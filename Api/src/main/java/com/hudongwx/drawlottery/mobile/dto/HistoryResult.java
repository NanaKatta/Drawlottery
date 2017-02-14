package com.hudongwx.drawlottery.mobile.dto;

/**
 * Drawlottery.
 * Date: 2017/2/7 0007
 * Time: 14:13
 *
 * @author <a href="http://userwu.github.io">wuhongxu</a>.
 * @version 1.0.0
 */
public class HistoryResult {
    //商品id
    private Long id;
    //购买人次
    private Integer userBuyNumber;
    //当前购买人次
    private Integer buyCurrentNumber;
    //购买总人次
    private Integer buyTotalNumber;
    //商品状态
    private Integer commState;
    //添加期数
    private String roundTime;
    //封面URL
    private String coverImgUrl;

    //添加商品名
    private String commName;
    //添加用户id
    private Long userAccountId;
    //结束时间
    private Long endTime;
    //用户昵称
    private String userNickname;
    //是否是高中奖率
    private Boolean isWinner;
    //对应高中奖率字段
    private Integer win;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserBuyNumber() {
        return userBuyNumber;
    }

    public void setUserBuyNumber(Integer userBuyNumber) {
        this.userBuyNumber = userBuyNumber;
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

    public Integer getCommState() {
        return commState;
    }

    public void setCommState(Integer commState) {
        this.commState = commState;
    }

    public String getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(String roundTime) {
        this.roundTime = roundTime;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        this.isWinner = winner;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
        this.setWinner(win == 1);
    }

    @Override
    public String toString() {
        return "HistoryResult{" +
                "id=" + id +
                ", userBuyNumber=" + userBuyNumber +
                ", buyCurrentNumber=" + buyCurrentNumber +
                ", buyTotalNumber=" + buyTotalNumber +
                ", commState=" + commState +
                ", roundTime='" + roundTime + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", commName='" + commName + '\'' +
                ", userAccountId=" + userAccountId +
                ", endTime=" + endTime +
                ", userNickname='" + userNickname + '\'' +
                ", isWinner=" + isWinner +
                ", win=" + win +
                '}';
    }
}
