package com.outstudio.weixin.back.exception;

/**
 * Created by 96428 on 2017/9/8.
 * This in weixin-edu, com.outstudio.weixin.back.exception
 */
public class WrongPasswordException extends CustomException {
    public WrongPasswordException(String mes) {
        super(mes);
    }
}
