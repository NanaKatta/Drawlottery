package com.hudongwx.drawlottery.mobile.exception;

/**
 * Drawlottery.
 * Date: 2017/2/9 0009
 * Time: 14:23
 *
 * @author <a href="http://userwu.github.io">wuhongxu</a>.
 * @version 1.0.0
 */
public class ServiceException extends Exception {
    public ServiceException(){
        super("未知错误");
    }
    public ServiceException(String msg){
        super(msg);
    }
}
