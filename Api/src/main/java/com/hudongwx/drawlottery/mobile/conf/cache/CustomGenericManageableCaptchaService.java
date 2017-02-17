package com.hudongwx.drawlottery.mobile.conf.cache;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

/**
 * Drawlottery.
 * Date: 2017/2/16 0016
 * Time: 18:28
 *
 * @author <a href="http://userwu.github.io">wuhongxu</a>.
 * @version 1.0.0
 */
public class CustomGenericManageableCaptchaService extends GenericManageableCaptchaService {
    /**
     * The constructor method of class CustomManageableCaptchaService.java .
     *
     * @param captchaEngine
     * @param minGuarantedStorageDelayInSeconds
     * @param maxCaptchaStoreSize
     * @param captchaStoreLoadBeforeGarbageCollection
     */
    public CustomGenericManageableCaptchaService(CaptchaEngine captchaEngine,
                                                 int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize,
                                                 int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaEngine, minGuarantedStorageDelayInSeconds,
                maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    /**
     * The constructor method of class CustomManageableCaptchaService.java .
     *
     * @param captchaStore
     * @param captchaEngine
     * @param minGuarantedStorageDelayInSeconds
     * @param maxCaptchaStoreSize
     * @param captchaStoreLoadBeforeGarbageCollection
     */
    public CustomGenericManageableCaptchaService(CaptchaStore captchaStore,
                                                 CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds,
                                                 int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds,
                maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    /**
     * 修改验证码校验逻辑，默认的是执行了该方法后，就把sessionid从store当中移除<br/>
     * 然而在ajax校验的时候，如果第一次验证失败，第二次还得重新刷新验证码，这种逻辑不合理<br/>
     * 现在修改逻辑，只有校验通过以后，才移除sessionid。 Method Name：validateResponseForID .
     *
     * @param ID
     * @param response
     * @return
     * @throws CaptchaServiceException
     *             the return type：Boolean
     */
    @Override
    public Boolean validateResponseForID(String ID, Object response)
            throws CaptchaServiceException {
        System.out.printf("sessionId = %s,code = %s",ID,response);
        if (!this.store.hasCaptcha(ID)) {
            throw new CaptchaServiceException(
                    "验证码失效或没有该验证码");
        }
        Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
        //源码的这一句是没被注释的，这里我们注释掉，在下面暴露一个方法给我们自己来移除sessionId
        //this.store.removeCaptcha(ID);
        return valid;
    }

    /**
     * 移除session绑定的验证码信息.
     * Method Name：removeCaptcha .
     * @param sessionId
     * the return type：void
     */
    public void removeCaptcha(String sessionId){
        if(sessionId!=null && this.store.hasCaptcha(sessionId)){
            this.store.removeCaptcha(sessionId);
        }
    }
}
