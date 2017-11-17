package com.outstudio.weixin.back.exception;

/**
 * Created by lmy on 2017/11/17.
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String msg){
        super(msg);}
}
