package com.hudongwx.drawlottery.mobile.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendSmsRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Drawlottery.
 * Date: 2017/2/17 0017
 * Time: 11:54
 *
 * @author <a href="http://userwu.github.io">wuhongxu</a>.
 * @version 1.0.0
 */
public class SMSUtils {
    /**
     * @param signName 签名
     * @param tmpCode 模板id
     * @param phoneNum 电话号码
     * @param date 面试时间
     * @param contact 联系方式
     */
    private static  final IClientProfile profile;
    private static  final IAcsClient acsClient;

    static{
        profile = DefaultProfile.getProfile("cn-hangzhou","LTAIhhoPE0fC0dDB","3jbg562s0dpoZxliHfOcGmcybkYx7f");
        acsClient = new DefaultAcsClient(profile);
    }
    public static void sendSms(String signName,String tmpCode,String phoneNum,String code) throws Exception {
        SingleSendSmsRequest smsRequest = new SingleSendSmsRequest();
        smsRequest.setSignName(signName);
        smsRequest.setTemplateCode(tmpCode);
        smsRequest.setRecNum(phoneNum);
        smsRequest.setAcceptFormat(FormatType.JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("time",30);
        jsonObject.put("title","[公司]");
        smsRequest.setParamString(jsonObject.toString());
        acsClient.getAcsResponse(smsRequest);
    }

}
