package com.hudongwx.drawlottery.dao;

import com.hudongwx.drawlottery.common.base.BaseMapper;
import com.hudongwx.drawlottery.pojo.LuckCodes;
import org.apache.ibatis.annotations.Param;

public interface LuckCodesMapper extends BaseMapper<LuckCodes> {
    int insertAuto(LuckCodes code);

    void insertCodes(@Param("commodityId") long commodityId, @Param("maxCode") long maxCode);
}