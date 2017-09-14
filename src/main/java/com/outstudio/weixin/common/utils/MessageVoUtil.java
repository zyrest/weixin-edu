package com.outstudio.weixin.common.utils;

import com.outstudio.weixin.common.consts.ResponseStatus;
import com.outstudio.weixin.common.vo.MessageVo;

/**
 * Created by 96428 on 2017/9/12.
 * This in weixin-edu, com.outstudio.weixin.common.utils
 */
public class MessageVoUtil {
    public static MessageVo success() {
        return new MessageVo().setStatus(ResponseStatus.SUCCESS).setMessage("success");
    }

    public static MessageVo success(Object data) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.SUCCESS).setMessage("success").setData(data);
        return messageVo;
    }

    public static MessageVo success(String redirectUrl,Object data) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.SUCCESS).setMessage("success").setData(data).setRedirectUrl(redirectUrl);
        return messageVo;
    }

    public static MessageVo created(String redirectUrl) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.CREATED).setMessage("created").setRedirectUrl(redirectUrl);
        return messageVo;
    }

    public static MessageVo created(String redirectUrl,Object data) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.CREATED).setMessage("created").setRedirectUrl(redirectUrl).setData(data);
        return messageVo;
    }

    public static MessageVo databaseError(String redirectUrl) {
        MessageVo me = new MessageVo();
        me.setStatus(ResponseStatus.DATABASE_ERROR).setMessage("database error").setRedirectUrl(redirectUrl);
        return me;
    }

    public static MessageVo noContent(String redirectUrl) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.NO_CONTENT).setMessage("no content").setRedirectUrl(redirectUrl);
        return messageVo;
    }

    public static MessageVo resourceNotFound(String redirectUrl) {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.RESOURCE_NOT_FOUND).setMessage("resource not found").setRedirectUrl(redirectUrl);
        return messageVo;
    }

    public static MessageVo resourceIsUsing() {
        MessageVo messageVo = new MessageVo();
        messageVo.setStatus(ResponseStatus.LOGICAL_ERROR).setMessage("资源正在使用中，不能删除！");
        return messageVo;
    }
}
