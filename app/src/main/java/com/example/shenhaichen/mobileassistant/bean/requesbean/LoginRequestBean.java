package com.example.shenhaichen.mobileassistant.bean.requesbean;

/**
 * Created by shenhaichen on 09/01/2018.
 */

public class LoginRequestBean {

    public LoginRequestBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
