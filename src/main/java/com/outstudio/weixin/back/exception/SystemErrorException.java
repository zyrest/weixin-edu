package com.outstudio.weixin.back.exception;

/**
 * Created by 96428 on 2017/8/7.
 * This in hospital, io.github.cyingyo.hospital.common.exception
 */
public class SystemErrorException extends CustomException {
    public SystemErrorException() {
        super("系统错误, 请稍后再试");
    }
}
