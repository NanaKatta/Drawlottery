package com.hudongwx.drawlottery.mobile.service.order.impl;

import com.hudongwx.drawlottery.mobile.TestBaseMapper;
import com.hudongwx.drawlottery.mobile.dto.HistoryResult;
import com.hudongwx.drawlottery.mobile.entitys.CommodityAmount;
import com.hudongwx.drawlottery.mobile.entitys.Orders;
import com.hudongwx.drawlottery.mobile.service.order.IOrdersService;
import com.hudongwx.drawlottery.mobile.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 11 on 2017/1/14.
 */
public class OrdersServiceImplTest extends TestBaseMapper {
    @Autowired
    IOrdersService service;
    @Autowired
    IUserService userService;
    @Test
    public void testPay() throws Exception {
        Long[] account = new Long[8];
        account[0] = 10000L;
        account[1] = 10001L;
        account[2] = 100000L;
        for(int i = 0; i < 5; i++){
            account[3+i] = account[2]+i+1;
        }
        for(int i = 0; i < 7; i++){
            int finalI = i;
            new Thread(() -> {
                Long accountId = account[finalI];
                Orders orders = new Orders();
                orders.setUserAccountId(accountId);
                orders.setPayModeId(1);
                orders.setRedPacketId(0L);
                orders.setPrice(20);
                orders.setSubmitDate(new Date().getTime());
                List<CommodityAmount> list = new ArrayList<>();
                CommodityAmount commodityAmount = new CommodityAmount();
                commodityAmount.setCommodityId(2L);
                commodityAmount.setAmount(20);
                list.add(commodityAmount);
                try {
                    service.createOrder(accountId, orders, list);
                } catch (Exception e){
                    System.out.printf("accountId:%s->出错，原因:%s\n",accountId,e.getMessage());
                    e.printStackTrace();
                    System.out.println("-------------------------------------------------");

                }
            }).start();
        }
        Thread.sleep(10000);
        for (Long id : account) {
            final List<HistoryResult> historyResults = userService.selectPurchaseRecords(0, id, 0L);
            for (HistoryResult result : historyResults) {
                System.out.printf("\n----------------------\n%s\n\n",result.toString());
            }

        }
    }
}