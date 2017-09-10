package com.outstudio.weixin.back.exception;

/**
 * Created by lmy on 2017/9/9.
 */
public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException(String mes) {
        super(mes);
    }
}
