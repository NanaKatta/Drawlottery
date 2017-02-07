package com.hudongwx.drawlottery.mobile.service.captcha.impl;

import com.hudongwx.drawlottery.mobile.service.captcha.ICaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 开发公司：hudongwx.com<br/>
 * 版权：294786949@qq.com<br/>
 * <p>
 *
 * @author origin
 * @version 1.0, 2017/1/14 0014 <br/>
 * @desc <p>
 * <p>
 * 创建　origin　2017/1/14 0014　<br/>
 * <p>
 * *******
 * <p>
 * @email 294786949@qq.com
 */
@Service
@CacheConfig(cacheNames="phone_captcha_cache")
public class CaptchaServiceImpl implements ICaptchaService {

    // super(new FastHashMapCaptchaStore(), new DefaultGimpyEngine(), 180,
    //            100000, 75000);
    //验证码失效时间,最大存储数量,少于多时候生成
    @Autowired
    ImageCaptchaService captchaService;

    /*
    获取验证图片信息
     */
    public BufferedImage getBufferedCaptchImage(String id){
        BufferedImage image = captchaService.getImageChallengeForID(id);
        return image;
    }

    /**
     * 发送手机验证码
     * @param id
     * @return
     */
    @CachePut(key = "#id")
    @Override
    public String sendPhoneCaptchaCode(String id, String phoneNumber) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int i = current.nextInt(1000, 9999);
        //TODO 调用阿里云 短信验证服务
        return String.valueOf(i);
    }


    @Override
    public boolean validatorPhoneCaptchCode(String id) {

        return false;
    }


    /**
     * 图形验证码校验
     * @param id
     * @param code
     * @return
     */
    public boolean validatorImageCode(String id,String code){
        return captchaService.validateResponseForID(id,code);
    }

}
