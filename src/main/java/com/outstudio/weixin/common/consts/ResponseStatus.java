package com.outstudio.weixin.common.consts;

/**
 * Created by 96428 on 2017/7/25.
 * This in ssmjavaconfig, samson.common.statics
 */
public class ResponseStatus {

    public static final Integer SUCCESS = 200;//查
    public static final Integer CREATED = 201;//增改
    public static final Integer RECIEVED = 202;
    public static final Integer NULL_RESPONSE = 203;
    public static final Integer NO_CONTENT = 204;//删除
    public static final Integer INVALID_REQUEST=400;// 增改

    public static final Integer RESOURCE_NOT_FOUND = 40001;
    public static final Integer WRONG_TOKEN = 40002;
    public static final Integer TOKEN_EXPIRE = 40003;
    public static final Integer NO_PERMISSION = 40004;
    public static final Integer NO_SUCH_USER = 40005;
    public static final Integer WRONG_URL = 40006;
    public static final Integer WRONG_FILE_TYPE = 40007;
    public static final Integer WRONG_FILE_SIZE = 40008;
    public static final Integer UNVALID_PARAM = 40009;
    public static final Integer UNVALID_HEADER = 40010;
    public static final Integer KICK_OUT = 40011;
    public static final Integer EXCESSIVE_ATTEMPT = 40012;
    public static final Integer USER_EXIST = 40013;
    public static final Integer DATABASE_ERROR = 40014;
    public static final Integer LOGICAL_ERROR = 40015;
    public static final Integer SYSTEM_ERROE = 500;

}
