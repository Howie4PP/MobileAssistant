package com.example.shenhaichen.mobileassistant.common.rx;

import android.content.Context;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.common.exception.ApiException;
import com.example.shenhaichen.mobileassistant.common.exception.BaseException;
import com.example.shenhaichen.mobileassistant.common.exception.ErrorMessageFactory;
import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * 错误信息处理的类，封装后返回具体错误的信息
 * Created by shenhaichen on 06/01/2018.
 */

public class RxErrorHandler {

    private Context mContext;

    public RxErrorHandler(Context context){

        this.mContext = context;
    }

    public BaseException handleError(Throwable e){

        BaseException exception = new BaseException();

        if(e instanceof ApiException){

            exception.setCode(((ApiException)e).getCode());

        }
        else if (e instanceof JsonParseException){
            exception.setCode(BaseException.JSON_ERROR);
        }
        else  if(e instanceof HttpException){

            exception.setCode(((HttpException)e).code());
        }
        else  if(e instanceof SocketTimeoutException){

            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        }
        else if(e instanceof SocketException){


        }
        else {

            exception.setCode(BaseException.UNKNOWN_ERROR);

        }

        exception.setDisplayMessage(ErrorMessageFactory.create(mContext,exception.getCode()));

        return  exception;
    }

    public void  showErrorMessage(BaseException e){


        Toast.makeText(mContext,e.getDisplayMessage(),Toast.LENGTH_LONG).show();

    }
}
