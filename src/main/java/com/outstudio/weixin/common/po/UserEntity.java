package com.outstudio.weixin.common.po;

import java.io.Serializable;
import java.util.Date;

public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer pid;
    private double balance;
    private Integer level;

    private String openid;

    private String nickname;

    private Integer sex;

    private String province;

    private String city;

    private String country;

    private Date vip_end_date;


    public String getOpenid() {
        return openid;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", pid=" + pid +
                ", balance=" + balance +
                ", level=" + level +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", vip_end_date=" + vip_end_date +
                '}';
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public double getBalance() {
        return balance;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Date getVip_end_date() {
        return vip_end_date;
    }

    public void setVip_end_date(Date vip_end_date) {
        this.vip_end_date = vip_end_date;
    }

}