package com.outstudio.weixin.common.vo;

/**
 * Created by lmy on 2018/2/18.
 */
public enum ExhibitionType {
    AUDIO("audio"), VIDEO("video"), PICTURE("picture");
    private String type;

    ExhibitionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
