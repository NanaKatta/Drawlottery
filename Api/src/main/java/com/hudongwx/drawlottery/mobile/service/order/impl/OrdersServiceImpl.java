package com.hudongwx.drawlottery.mobile.service.order.impl;

import com.hudongwx.drawlottery.mobile.entitys.*;
import com.hudongwx.drawlottery.mobile.exception.ServiceException;
import com.hudongwx.drawlottery.mobile.mappers.*;
import com.hudongwx.drawlottery.mobile.service.commodity.ICommodityService;
import com.hudongwx.drawlottery.mobile.service.commodity.IExchangeMethodService;
import com.hudongwx.drawlottery.mobile.service.order.IOrdersService;
import com.hudongwx.drawlottery.mobile.utils.LotteryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 开发公司：hudongwx.com<br/>
 * 版权：294786949@qq.com<br/>
 * <p>
 *
 * @author Kiter
 * @version 1.0, 2016/12/22 <br/>
 * @desc <p>
 * <p>
 * 创建　kiter　2016/12/22 19:50　<br/>
 * <p>
 * 订单service实现类
 * <p>
 * @email 346905702@qq.com
 */
@Service
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    OrdersMapper mapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedPacketsMapper redMapper;
    @Autowired
    OrdersCommoditysMapper orderMapper;
    @Autowired
    CommoditysMapper comMapper;
    @Autowired
    LuckCodesMapper codesMapper;
    @Autowired
    CommodityMapper commMapper;
    @Autowired
    LotteryInfoMapper lotteryInfoMapper;
    @Resource
    ICommodityService commodityService;
    @Autowired
    LuckCodeTemplateMapper templateMapper;
    @Autowired
    UserCodesHistoryMapper userHistoryMapper;
    @Autowired
    CommodityTemplateMapper templeMapper;
    @Autowired
    CommodityHistoryMapper historyMapper;
    @Autowired
    OrdersServiceImplAsync ordersServiceImplAsync;
    @Autowired
    NotificationPrizeMapper npMapper;
    @Resource
    private IExchangeMethodService exchangeMethodService;

    private final static Object lock = new Object();

    /**
     * 计算扣款
     * 有下一期情况下：如果购买数大于剩余数量，会购买下一期
     * 无下一期情况下：如果购买数量大于剩余数量，将返回闪币
     *
     * @param accountId
     * @param orders
     * @return
     * @apiNote 更改逻辑
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Long createOrder(Long accountId, Orders orders, List<CommodityAmount> commodityAmounts) throws ServiceException {
        //-----------创建订单-----------
        Long date = new Date().getTime();
        orders.setUserAccountId(accountId);
        orders.setSubmitDate(date);
        mapper.insertUseGenerated(orders);
        //-----------创建订单-----------

        //-----------处理订单细节-----------
        synchronized (lock){
            sysHandleOrderDetail(accountId, orders, commodityAmounts);
        }
        //-----------处理订单细节-----------

        //-----------异步支付-----------
        //ordersServiceImplAsync.payAsync(accountId, orders, commodityAmounts);
        //-----------异步支付-----------

        return orders.getId();
    }
    @Transactional(isolation = Isolation.READ_COMMITTED,rollbackForClassName = {"ServiceException","Exception"})
    public void sysHandleOrderDetail(long accountId, Orders orders, List<CommodityAmount> commodityAmountList) throws ServiceException {
        int price = orders.getPrice();
        final Long orderId = orders.getId();
        //总共需要花费的金额 （商品单价始终为1）
        int sumPrice = 0;
        for (CommodityAmount ca : commodityAmountList) {
            Commoditys currentCommodity = commodityService.selectOnSellCommodities(ca.getCommodityId());
            if (null == currentCommodity)
                throw new ServiceException("未获取商品信息");

            final Integer remainNum = currentCommodity.getBuyTotalNumber() - currentCommodity.getBuyCurrentNumber();
            Integer amount = ca.getAmount();
            //调整商品购买数量
            amount -= amount % currentCommodity.getMinimum();
            sumPrice += amount;
            //购买量与剩余量差值
            final int subNum = amount - remainNum;
            if (subNum >= 0) {
                if (subNum > 0 && currentCommodity.getAutoRound() == 1) {
                    Commodity nextCommodity = commodityService.getNextCommodity(currentCommodity.getId());
                    updateLuckCodes(accountId, nextCommodity.getId(), subNum, orderId);
                    //关联订单商品信息 多购买的数量关联到下一期
                    connectOrderAndCommodity(nextCommodity.getId(), orderId, subNum);
                    amount = remainNum;
                }
                if (currentCommodity.getAutoRound() == 1) {
                    final Commodity nextCommodity = commodityService.groundNext(currentCommodity.getId());
                    commMapper.updateBuyCurrentNum(nextCommodity.getId(), nextCommodity.getBuyCurrentNumber()+subNum);
                } else {
                    exchangeMethodService.exchangeVirtual(accountId, subNum);
                }
                //商品售罄开奖，更新商品信息
                Commodity temp = new Commodity();
                temp.setSellOutTime(System.currentTimeMillis());
                temp.setStateId(Commodity.SELL_OUT);
                temp.setId(currentCommodity.getId());
                commMapper.updateById(temp);
                currentCommodity.setSellOutTime(System.currentTimeMillis());
            }
            final boolean b = updateLuckCodes(accountId, currentCommodity.getId(), amount, orderId);
            //如果没有拿到幸运码
            if (!b){
                throw new ServiceException("未获取到幸运码");
            }
            if(subNum >= 0){
                //开奖
                LotteryUtils.raffle(npMapper, commMapper, comMapper, mapper, templateMapper, codesMapper, lotteryInfoMapper, userMapper, currentCommodity);
            }
            connectOrderAndCommodity(currentCommodity.getId(), orderId, amount);
            commMapper.updateBuyCurrentNum(currentCommodity.getId(), currentCommodity.getBuyCurrentNumber()+Math.min(amount, remainNum));
        }
        //余额更改量
        int changeNum;
        //红包面额
        int redNum;

        int tempNum = 0;
        //红包
        Long packetId = orders.getRedPacketId();
        if (packetId != null && packetId != 0) { // 如果红包ID不为空
            RedPackets red = new RedPackets();
            red.setId(orders.getRedPacketId());
            //查询红包面值
            RedPackets redPackets = redMapper.selectByPrimaryKey(orders.getRedPacketId());
            redNum = redPackets.getWorth();

            if (sumPrice != 0) {//有购买量则使用红包
                //更改红包使用状态
                red.setUseState(1);
                redMapper.updateByPrimaryKeySelective(red);
                sumPrice -= redNum;
                if (redNum >= sumPrice) {//红包数额大于购买量
                    sumPrice = 0;
                    tempNum = redNum;
                }
            }
        }

        if (orders.getPayModeId() == 1) {//使用余额付款方式
            changeNum = -sumPrice;
        } else {
            changeNum = price - sumPrice - tempNum;
        }
        User u = userMapper.selectById(accountId);
        if(u.getGoldNumber() + changeNum < 0)
            throw new ServiceException("余额不足");
        u.setGoldNumber(u.getGoldNumber() + changeNum);
        userMapper.updateByPrimaryKeySelective(u);

        //支付成功之后，更改订单支付状态
        orders.setPayState(1);
        mapper.updatePayState(orders.getId(), 1);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NESTED)
    private void handleOrderDetail(Long accountId, Long orderId, List<CommodityAmount> commodityAmountList) throws ServiceException {
        for (CommodityAmount ca : commodityAmountList) {
            //获取当前购买的商品商品信息
            Commoditys currentCommodity = commodityService.selectOnSellCommodities(ca.getCommodityId());
            if (null == currentCommodity)
                throw new ServiceException("未获取商品信息");

            int remainNum = currentCommodity.getBuyTotalNumber() - currentCommodity.getBuyCurrentNumber();
            int amount = ca.getAmount();
            //调整商品购买数量
            amount -= amount % currentCommodity.getMinimum();
            //购买量与剩余量差值
            final int subNum = amount - remainNum;
            if (subNum > 0 && currentCommodity.getAutoRound() == 1) {
                Commodity nextCommodity = commodityService.getNextCommodity(currentCommodity.getId());
                updateLuckCodes(accountId, nextCommodity.getId(), subNum, orderId);
                //关联订单商品信息 多购买的数量关联到下一期
                connectOrderAndCommodity(nextCommodity.getId(), orderId, subNum);
                amount = remainNum;
            }
            updateLuckCodes(accountId, currentCommodity.getId(), amount, orderId);
            connectOrderAndCommodity(currentCommodity.getId(), orderId, amount);
        }
    }

    private void connectOrderAndCommodity(Long currentCommodityId, Long orderId, Integer amount) {
        final OrdersCommoditys ordersCommoditys = new OrdersCommoditys(currentCommodityId, orderId);
        ordersCommoditys.setAmount(amount);
        orderMapper.insert(ordersCommoditys);//添加商品订单信息
    }

    /**
     * 生成幸运码
     *
     * @param commodityId
     * @param buyNum
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean updateLuckCodes(Long accountId, long commodityId, int buyNum, Long ordersId) {
        Date date = new Date();
        int i = codesMapper.updateCodesState(accountId, commodityId, ordersId,
                date.getTime(), buyNum);//自定义动态更新sql
        return i > 0;
    }


    /**
     * 查询当前用户的所有订单
     *
     * @param userAccount 用户accountID
     * @return 返回当前用户的所有订单信息
     */
    @Override
    public List<Orders> selectByUserAccount(Long userAccount) {
        Orders o = new Orders();
        o.setUserAccountId(userAccount);
        return mapper.select(o);
    }

    /**
     * 通过id删除订单
     *
     * @param id 订单id
     * @return
     */
    @Override
    public boolean deleteOder(Long id) {
        return mapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 通过主键修改订单信息
     *
     * @param oders 订单对象
     * @return 返回修改结果；
     */
    @Override
    public boolean update(Orders oders) {
        return mapper.updateByPrimaryKey(oders) > 0;
    }

    /**
     * 查看用户余额和红包
     *
     * @param accountId 用户ID
     * @param sum       商品总价
     * @return
     */
    @Override
    public Map<String, Object> selectUsableRedPackets(Long accountId, Integer sum) {
        Map<String, Object> m = new HashMap<>();
        List<Long> idList = new ArrayList<>();
        User user = userMapper.selectById(accountId);
        m.put("remainder", user.getGoldNumber());//获得用户账户余额
        List<RedPackets> list = redMapper.selectUserUsableRedPackets(accountId, System.currentTimeMillis());
        for (RedPackets r : list) {
            if (r.getUsePrice() <= sum) {
                idList.add(r.getId());//红包ID
            }
        }
        m.put("useRedPackets", idList);//添加可使用红包ID
        return m;
    }


    /**
     * 支付完成界面
     *
     * @param accountId 用户ID
     * @return
     */
    @Override
    public Map<String, Object> selectPaySuccess(Long accountId, Long orderId) {
        List<CommodityAmount> commodityAmounts = commodityService.selectAmounts(orderId);
        List<Map<String, Object>> mapList = new ArrayList<>();
        Integer number = 0;
        for (CommodityAmount ca : commodityAmounts) {
            Commoditys commoditys = comMapper.selectByKey(ca.getCommodityId());
            Map<String, Object> map = new HashMap<>();
            map.put("commId", ca.getCommodityId());//订单ID
            map.put("amount", ca.getAmount());//参与人次
            map.put("commodityName", commoditys.getName());//商品名
            map.put("roundTime", commoditys.getRoundTime());//期数
            map.put("luckCodes", luckCodes(accountId, ca.getCommodityId(), orderId));//用户参与商品的幸运码
            number += ca.getAmount();
            mapList.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("overallNumber", number);//添加总购买人次
        map.put("overallCommodity", commodityAmounts.size());//添加购买商品总数
        map.put("list", mapList);
        return map;

    }

    /*
    //查询用户当前订单参与商品的所有幸运号
    public List<String> luckCodesHistory(Long accountId, Long commodityId, Long ordersId) {
        List<String> list = new ArrayList<>();
        List<UserCodesHistory> list1 = userHistoryMapper.selectByOrders(accountId, commodityId, ordersId);
        for (UserCodesHistory luckCodes : list1) {
            UserCodesHistory codes1 = userHistoryMapper.selectById(luckCodes.getId());
            Long templateId = codes1.getLuckCodeTemplateId();
            LuckCodeTemplate template = templateMapper.selectById(templateId);
            list.add(template.getLuckCode());
        }
        return list;
    }*/

    //查询用户当前订单参与商品的所有幸运号
    public List<String> luckCodes(Long accountId, Long commodityId, Long ordersId) {
        List<String> list = new ArrayList<>();
        List<LuckCodes> list1 = codesMapper.selectByOrders(accountId, commodityId, ordersId);
        for (LuckCodes luckCodes : list1) {
            LuckCodes codes1 = codesMapper.selectById(luckCodes.getId());
            Long templateId = codes1.getLuckCodeTemplateId();
            LuckCodeTemplate template = templateMapper.selectById(templateId);
            list.add(template.getLuckCode());
        }
        return list;
    }

}
