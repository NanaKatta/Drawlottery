package com.hudongwx.drawlottery.mobile.mappers;

import com.hudongwx.drawlottery.mobile.commn.BaseMapper;
import com.hudongwx.drawlottery.mobile.entitys.RedPackets;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RedPacketsMapper extends BaseMapper<RedPackets> {

    List<RedPackets> selectByAccount(@Param("accountId")Long accountId);
}