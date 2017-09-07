package com.outstudio.weixin.common.po;

public class ManagerEntity {
    private Integer id;

    private String m_account;

    private String m_password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getM_account() {
        return m_account;
    }

    public void setM_account(String m_account) {
        this.m_account = m_account == null ? null : m_account.trim();
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password == null ? null : m_password.trim();
    }
}