package com.example.administrator.httplib;

/**
 * Created by Administrator on 2016/6/22 0022.
 * 请求文字的类
 */
public class StringRequest extends Request<String> {
    public StringRequest(String url, Method method, Callback callback) {
        super(url, method, callback);
    }

    @Override
    public void dispatchContent(byte[] content) {
        onResponse(new String(content));
    }
}
