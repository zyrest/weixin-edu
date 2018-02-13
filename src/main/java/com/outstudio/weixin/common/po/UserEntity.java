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

    private Date math_end_date;//数学会员到期时间

    private Date physics_end_date;//物理会员到期时间

    private Date chemistry_end_date;//化学会员到期时间

    public String getOpenid() {
        return openid;
    }

    public Date getMath_end_date() {
        return math_end_date;
    }

    public void setMath_end_date(Date math_end_date) {
        this.math_end_date = math_end_date;
    }

    public Date getPhysics_end_date() {
        return physics_end_date;
    }

    public void setPhysics_end_date(Date physics_end_date) {
        this.physics_end_date = physics_end_date;
    }

    public Date getChemistry_end_date() {
        return chemistry_end_date;
    }

    public void setChemistry_end_date(Date chemistry_end_date) {
        this.chemistry_end_date = chemistry_end_date;
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