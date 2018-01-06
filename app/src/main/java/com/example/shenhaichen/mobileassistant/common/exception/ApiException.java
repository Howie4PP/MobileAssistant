package com.example.shenhaichen.mobileassistant.common.exception;

/**
 * Created by shenhaichen on 05/01/2018.
 */

public class ApiException extends BaseException {

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}
