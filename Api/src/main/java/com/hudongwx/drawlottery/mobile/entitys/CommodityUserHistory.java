package com.hudongwx.drawlottery.mobile.entitys;

import javax.persistence.*;
import javax.websocket.OnClose;
import java.util.Date;

/**
 * 开发公司：hudongwx.com<br/>
 * 版权：294786949@qq.com<br/>
 * <p>
 *
 * @author Kiter
 * @version 1.0, 2017/1/5 <br/>
 * @desc <p>
 * <p>
 * 创建　kiter　2017/1/5 11:01　<br/>
 * <p>
 *          商品用户对应历史表实体类
 * <p>
 * @email 346905702@qq.com
 */

@Table(name = "t_commodity_user_history")
public class CommodityUserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_account_id")
    private Long accountId;

    /**
     * 商品历史ID
     */
    @Column(name = "commodity_history_id")
    private Long commodityHistoryId;

    /**
     * 购买商品时间
     */
    @Column(name = "partake_date")
    private Date partakeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCommodityHistoryId() {
        return commodityHistoryId;
    }

    public void setCommodityHistoryId(Long commodityHistoryId) {
        this.commodityHistoryId = commodityHistoryId;
    }

    public Date getPartakeDate() {
        return partakeDate;
    }

    public void setPartakeDate(Date partakeDate) {
        this.partakeDate = partakeDate;
    }
}
