package com.example.shenhaichen.mobileassistant.bean;

import java.io.Serializable;

/**
 * Created by shenhaichen on 05/01/2018.
 */

public class BaseBean<T> implements Serializable{

    public static final int SUCCESS = 1;
    private int status;
    private String message;
    private T data;

    /**
     * 等于1,则表示成功
     * @return true
     */
    public boolean success(){
        return (status == SUCCESS);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
