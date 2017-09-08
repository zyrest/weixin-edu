package com.outstudio.weixin.back.exception;

import org.springframework.validation.Errors;

/**
 * Created by 96428 on 2017/8/7.
 * This in hospital, io.github.cyingyo.hospital.common.exception
 */
public class UnvalidException extends CustomException {
    private Errors errors;

    public UnvalidException(String mes, Errors errors) {
        super(mes);
        this.errors = errors;
    }

    public UnvalidException(String mes) {
        super(mes);
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
