package com.hudongwx.drawlottery.service.order.impl;

import com.github.pagehelper.PageInfo;
import com.hudongwx.drawlottery.service.order.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单相关 service 实现类.
 * Date: 2017/1/18 0018
 * Time: 10:10
 *
 * @author <a href="http://userwu.github.io">wuhongxu</a>.
 * @version 1.0.0
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    /**
     * 得到历史集合.
     *
     * @param roundTime     期数
     * @param exchangeWay   兑奖方式
     * @param genre         属性
     * @param state         兑奖状态
     * @param cardNotEnough 是否卡充足
     * @return 分页
     */
    @Override
    public PageInfo getHistoryList(String roundTime, List<Integer> exchangeWay, List<Integer> genre, List<Integer> state, List<Integer> cardNotEnough) {
        return null;
    }
}
