package com.example.shenhaichen.mobileassistant.bean;

/**
 * Created by shenhaichen on 26/03/2018.
 */

public class LoginBean extends BaseEntity {

    private String token;

    private  User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
