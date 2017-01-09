package com.hudongwx.drawlottery.mobile.web.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hudongwx.drawlottery.mobile.entitys.Orders;
import com.hudongwx.drawlottery.mobile.service.luckcodes.IUserLuckCodesService;
import com.hudongwx.drawlottery.mobile.service.order.IOrdersService;
import com.hudongwx.drawlottery.mobile.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 开发公司：hudongwx.com<br/>
 * 版权：294786949@qq.com<br/>
 * <p>
 *
 * @author wu
 * @version 1.0, 2016/12/24 <br/>
 * @desc 用户订单管理<p>
 * <p>
 * 创建　wu　2016/12/24 <br/>
 * <p>
 * *********
 * <p>
 * @email 294786949@qq.com
 */
@RestController
@Api(value = "OrdersController", description = "用户订单管理")
public class OrdersController extends BaseController {

    @Autowired
    IOrdersService ordersService;
    @Autowired
    IUserLuckCodesService ulcService;

    /**
     * 用户添加订单信息
     *
     * @param jsonObject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/user/orders/sub", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject addOrders(@RequestBody JSONObject jsonObject) {
        System.out.println("jsonObject----------->" + jsonObject.toString());
        return response(ordersService.addOder(10000L, jsonObject));
    }

    /**
     * 用户查看指定订单信息
     *
     * @param orderid 订单id
     * @return JSONObject
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/user/orders/info", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject queryOrder(@RequestParam("orderid") Long orderid) {
        Orders order = new Orders();// TODO: 2016/12/24 查看单条订单？？？
        return success(order);
    }

    /**
     * 用户查看指定订单信息
     *
     * @return JSONObject
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/user/orders/suc", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject queryOrderSuccess(@RequestBody JSONObject jsonObject) {
        System.out.println("suc------>" + jsonObject.toString());
        Map<String, Object> mapInfo = ordersService.selectPaySuccess(10000L, jsonObject);
        System.out.println("mapList---------------->" + JSON.toJSONString(mapInfo));
        return success(mapInfo);
    }

    /**
     * 用户查看所有个人订单信息
     *
     * @param accountid 用户id
     * @return JSONObject
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/user/orders/show", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject queryAllUserOrders(@RequestParam("acc") Long accountid) {
        List<Orders> olist = ordersService.selectByUserAccount(accountid);
        return success(olist);
    }

    /**
     * 用户查看所有个人订单信息
     *
     * @param orderId 用户id
     * @return JSONObject
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/user/orders/show/code", method = {RequestMethod.POST, RequestMethod.GET})
    public JSONObject queryOrderCodes(@RequestParam("orderId") Long orderId) {
        List<Orders> olist = ordersService.selectByUserAccount(orderId);
        // TODO: 2017/1/9 查询code
        return success(olist);
    }
}
