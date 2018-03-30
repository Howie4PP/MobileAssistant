package com.example.shenhaichen.mobileassistant.common.http;

import android.content.Context;
import android.text.TextUtils;

import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.common.util.DensityUtil;
import com.example.shenhaichen.mobileassistant.common.util.DeviceUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 这个类就是取得原来的参数，然后加入公共参数，在封装为一个request进行网络请求
 * Created by shenhaichen on 09/01/2018.
 */

public class CommonParamsInterceptor implements Interceptor {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //因为有使用dagger，在MyAppModule中已经完成实例化了
    private Gson mGson;
    private Context mContext;

    public CommonParamsInterceptor(Context context, Gson mGson) {
        this.mGson = mGson;
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        //这是拦截器拦截到的请求数据
        Request request = chain.request();
        try {
            //判断请求方式，get or post request
            String method = request.method();
            // 一些常用的请求方式
            HashMap<String, Object> commomParamsMap = new HashMap<>();

            commomParamsMap.put(Constant.IMEI, DeviceUtils.getIMEI(mContext));
            commomParamsMap.put(Constant.MODEL, DeviceUtils.getModel());
            commomParamsMap.put(Constant.LANGUAGE, DeviceUtils.getLanguage());
            commomParamsMap.put(Constant.OS, DeviceUtils.getBuildVersionIncremental());
            commomParamsMap.put(Constant.RESOLUTION, DensityUtil.getScreenW(mContext) + "*" + DensityUtil.getScreenH(mContext));
            commomParamsMap.put(Constant.SDK, DeviceUtils.getBuildVersionSDK() + "");
            commomParamsMap.put(Constant.DENSITY_SCALE_FACTOR, mContext.getResources().getDisplayMetrics().density + "");
            //由于服务端的token是直接放在参数中，所以在这里也直接加入到参数当中去
            String token = ACache.get(mContext).getAsString(Constant.TOKEN);
            commomParamsMap.put(Constant.TOKEN, token == null ? "" : token);

            //封装get请求
            if (method.equals("GET")) {

                HttpUrl httpUrl = request.url();
                HashMap<String, Object> rootMap = new HashMap<>();

                Set<String> paramNames = httpUrl.queryParameterNames();

                //根据不同的请求去匹配
                for (String key : paramNames) {
                    if (Constant.PARAM.equals(key)) {
                        String oldParamJson = httpUrl.queryParameter(Constant.PARAM);
                        //考虑是否有参数进行请求后，进行返回
                        if (oldParamJson != null) {
                            HashMap<String, Object> p = mGson.fromJson(oldParamJson, HashMap.class);
                            if (p != null) {
                                for (Map.Entry<String, Object> entry : p.entrySet()) {
                                    rootMap.put(entry.getKey(), entry.getValue());
                                }
                            }
                        }
                    } else {
                        rootMap.put(key, httpUrl.queryParameter(key));
                    }
                }

                rootMap.put("publicParams", commomParamsMap);
                // {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}
                String newJsonParams = mGson.toJson(rootMap);
                //toString 直接返回string的url
                String url = httpUrl.toString();
                //考虑是否有参数
                int index = url.indexOf("?");
                if (index > 0) {
                    url = url.substring(0, index);
                }

                //重新拼接需要的API地址
                url = url + "?" + Constant.PARAM + "=" + newJsonParams;
                //重新构建请求
                request = request.newBuilder().url(url).build();

            } else if (method.equals("POST")) {

                RequestBody body = request.body();

                HashMap<String, Object> rootMap = new HashMap<>();
                if (body instanceof FormBody) { //form 表单
                    for (int i = 0; i < ((FormBody) body).size(); i++) {
                        rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                    }
                } else {
                    Buffer buffer = new Buffer();
                    //读取成字符串
                    body.writeTo(buffer);
                    String oldJsonParams = buffer.readUtf8();
                    //当得到的Json数据不为空的时候，才进行操作
                    if (!TextUtils.isEmpty(oldJsonParams)){
                        //转化为需要的数据格式
                        rootMap = mGson.fromJson(oldJsonParams, HashMap.class);
                        if (rootMap != null){
                            //重新组装，加入公共参数
                            rootMap.put("publicParams", commomParamsMap);
                            String newJsonParams = mGson.toJson(rootMap);
                            request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();
                        }
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return chain.proceed(request);
    }
}
