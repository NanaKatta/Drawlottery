package com.hudongwx.drawlottery.mobile.service.commodity.impl;

import com.hudongwx.drawlottery.mobile.TestBaseMapper;
import com.hudongwx.drawlottery.mobile.service.commodity.ICommodityService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * 开发公司：hudongwx.com<br/>
 * 版权：294786949@qq.com<br/>
 * <p>
 *
 * @author wu
 * @version 1.0, 2016/12/29 <br/>
 * @desc <p>
 * <p>
 * 创建　wu　2016/12/29 <br/>
 * <p>
 * 用户收货地址
 * <p>
 * @email 294786949@qq.com
 */
public class CommodityServiceImplTest extends TestBaseMapper {

    @Autowired
    ICommodityService service;
    @Test
    public void testSelectPaging() throws Exception {
        List<Map<String, Object>> list = service.selectPaging(1, "布加迪", 1);
        for (Map<String,Object> map : list){
            System.out.println(map.get("commodityName"));
        }
    }

    @Test
    public void testType1() throws Exception {
        Map<String, Object> map = service.selectCommodity(23l);
        System.out.println(map.get("commodityName"));
    }

    @Test
    public void testByType() throws Exception {
        List<Map<String, Object>> list = service.selectHeight(40000);
        System.out.println(list.size());
    }

    @Test
    public void testType4() throws Exception {
        Map<String, Object> map = service.selectCommodity(54l);
        List<String> list = (List<String>) map.get("imgUrls");
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void testForPut() throws Exception {
        List<Map<String, Object>> list = service.selectOnLottery(1);
        System.out.println(list.size());
        for (Map<String, Object> map : list){
            System.out.println(map.get("roundTime"));
        }
        Assert.assertNotNull(list);
    }

}