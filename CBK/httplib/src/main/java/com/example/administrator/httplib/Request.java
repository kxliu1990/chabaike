package com.example.administrator.httplib;


import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/21 0021.
 *
 * 创建请求网络方式的类
 */
abstract public class Request<T> {
    private String url;
    private Method method;
    private Callback callback;

    public Request(String url, Method method, Callback callback) {
        this.url = url;
        this.method = method;
        this.callback = callback;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    //枚举
    public enum Method{
        GET,POST
    }


    //重写接口的方法
    public void onError(Exception e){
        callback.onError(e);
    }
    public void onResponse(T res){
        callback.onResonse(res);
    }


    //定义一个接口
    public interface Callback<T>{
        void onError(Exception e);
        void onResonse(T response);
    }
    //post请求获得参数的方法
    public HashMap<String,String> getPostParams(){
        return null;
    }

    abstract public void dispatchContent(byte[] content);
}
