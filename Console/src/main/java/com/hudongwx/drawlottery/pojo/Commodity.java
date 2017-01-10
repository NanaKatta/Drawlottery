package com.hudongwx.drawlottery.pojo;

import com.hudongwx.drawlottery.common.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品实体类（同时兼有dto功能）.
 * Date: 2017/1/10 0010
 * Time: 17:48
 *
 * @author <a href="http://userwu.github.io">wuhongxu</a>.
 * @version 1.0.0
 */
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上一次购买人次
     */
    @Column(name = "buy_last_number")
    private Integer buyLastNumber;

    /**
     * 当前购买人次
     */
    @Column(name = "buy_current_number")
    private Integer buyCurrentNumber;
    /**
     * 中奖幸运码id
     */
    @Column(name = "luck_code_id")
    private Long luckCodeId;

    /**
     * 浏览量（商品点击量）
     */
    @Column(name = "view_num")
    private Long viewNum;

    @Column(name = "temp_id")
    private Long tempId;

    /**
     * 当前期数
     */
    @Column(name = "round_time")
    private String roundTime;
    /**
     * 售罄时间
     */
    @Column(name = "sell_out_time")
    private Date sellOutTime;


//----------------------------------------------------------//
//----------------------------------------------------------//
//--------------------------自动填充项------------------------//
//----------------------------------------------------------//
//----------------------------------------------------------//

    /**
     * 商品名
     */
    private String name;
    /**
     * 商品描述
     */
    private String commodityDesc;
    /**
     * 商品类型id
     */
    private Long commodityTypeId;
    /**
     * 类型名
     */
    @Transient
    private String typeName;
    /**
     * 商品类别（1：实体，0：虚拟,2：实体不能快递）
     */
    private Integer genre;

    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 发卡类型
     */
    private Integer cardType;
    /**
     * 发卡数量
     */
    private Integer cardNum;
    /**
     * 发卡金额
     */
    private Integer cardMoney;
    /**
     * 上架时间（已格式化，用于显示）
     */
    private String groundTimeLabel;
    /**
     * 下架时间（已格式化，用于显示）
     */
    private String undercarriageTimeLabel;
    /**
     * 上架时间
     */
    private Date groundTime;
    /**
     * 下架时间
     */
    private Date undercarriageTime;
    /**
     * 剩余购买人次
     */
    private Integer buyNowNumber;
    /**
     * 总购买人次
     */
    private Integer buyTotalNumber;
    /**
     * 开抢时间
     */
    private Date startTime;
    /**
     * 商品状态id
     */
    private Integer stateId;
    /**
     * 状态名
     */
    private String stateName;
    /**
     * 封面图片id
     */
    private String coverImgUrl;
    /**
     * 是否自动生成下一期（1：是，0：否）
     */
    private Integer autoRound;
    /**
     * 商品详情URL
     */
    private String commodityDescUrl;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getGroundTime() {
        return groundTime;
    }

    public void setGroundTime(Date groundTime) {
        this.groundTime = groundTime;
        if (groundTime == null) this.setGroundTimeLabel("");
        else this.setGroundTimeLabel(DateUtils.format(groundTime));
    }

    public Date getUndercarriageTime() {
        return undercarriageTime;
    }

    public void setUndercarriageTime(Date undercarriageTime) {
        this.undercarriageTime = undercarriageTime;
        if (undercarriageTime == null) this.setUndercarriageTimeLabel("");
        else this.setGroundTimeLabel(DateUtils.format(undercarriageTime));
    }

    public String getGroundTimeLabel() {
        return groundTimeLabel;
    }

    public void setGroundTimeLabel(String groundTimeLabel) {
        this.groundTimeLabel = groundTimeLabel;
    }

    public String getUndercarriageTimeLabel() {
        return undercarriageTimeLabel;
    }

    public void setUndercarriageTimeLabel(String undercarriageTimeLabel) {
        this.undercarriageTimeLabel = undercarriageTimeLabel;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBuyNowNumber() {
        return buyNowNumber;
    }

    public void setBuyNowNumber(Integer buyNowNumber) {
        this.buyNowNumber = buyNowNumber;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getCommodityDesc() {
        return commodityDesc;
    }


    public void setCommodityDesc(String commodityDesc) {
        this.commodityDesc = commodityDesc == null ? null : commodityDesc.trim();
    }


    public Long getCommodityTypeId() {
        return commodityTypeId;
    }


    public void setCommodityTypeId(Long commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public Integer getGenre() {
        return genre;
    }


    public void setGenre(Integer genre) {
        this.genre = genre;
    }


    public Integer getBuyLastNumber() {
        return buyLastNumber;
    }


    public void setBuyLastNumber(Integer buyLastNumber) {
        this.buyLastNumber = buyLastNumber;
    }


    public Integer getBuyCurrentNumber() {
        return buyCurrentNumber;
    }


    public void setBuyCurrentNumber(Integer buyCurrentNumber) {
        this.buyCurrentNumber = buyCurrentNumber;
    }


    public Integer getBuyTotalNumber() {
        return buyTotalNumber;
    }


    public void setBuyTotalNumber(Integer buyTotalNumber) {
        this.buyTotalNumber = buyTotalNumber;
    }


    public Date getStartTime() {
        return startTime;
    }


    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Long getLuckCodeId() {
        return luckCodeId;
    }


    public void setLuckCodeId(Long luckCodeId) {
        this.luckCodeId = luckCodeId;
    }


    public String getRoundTime() {
        return roundTime;
    }


    public void setRoundTime(String roundTime) {
        this.roundTime = roundTime == null ? null : roundTime.trim();
    }


    public String getCoverImgUrl() {
        return coverImgUrl;
    }


    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl == null ? null : coverImgUrl.trim();
    }


    public Integer getAutoRound() {
        return autoRound;
    }


    public void setAutoRound(Integer autoRound) {
        this.autoRound = autoRound;
    }


    public String getCommodityDescUrl() {
        return commodityDescUrl;
    }


    public void setCommodityDescUrl(String commodityDescUrl) {
        this.commodityDescUrl = commodityDescUrl == null ? null : commodityDescUrl.trim();
    }


    public Date getSellOutTime() {
        return sellOutTime;
    }


    public void setSellOutTime(Date sellOutTime) {
        this.sellOutTime = sellOutTime;
    }

    public Long getViewNum() {
        return viewNum;
    }

    public void setViewNum(Long viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(Integer cardMoney) {
        this.cardMoney = cardMoney;
    }
}
