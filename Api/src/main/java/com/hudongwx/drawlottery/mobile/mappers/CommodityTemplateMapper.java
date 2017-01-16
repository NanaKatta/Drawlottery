package com.hudongwx.drawlottery.mobile.mappers;


import com.hudongwx.drawlottery.mobile.commn.BaseMapper;
import com.hudongwx.drawlottery.mobile.entitys.CommodityTemplate;
import org.apache.ibatis.annotations.Param;

public interface CommodityTemplateMapper extends BaseMapper<CommodityTemplate> {

    CommodityTemplate selectById(@Param("tempId")Long tempId);
}