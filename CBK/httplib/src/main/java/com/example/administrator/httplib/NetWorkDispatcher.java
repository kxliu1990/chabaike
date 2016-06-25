package com.example.administrator.httplib;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class NetWorkDispatcher extends Thread {
    private static final String TAG = NetWorkDispatcher.class.getSimpleName();
    private BlockingDeque<Request> mQueue;
    public boolean flag = true;
    public NetWorkDispatcher(BlockingDeque<Request> queue){
        mQueue = queue;
    }
    @Override
    public void run() {
        //如果线程标记是可运行状态并且没有被打断，则从请求队列中取出请求进行网络访问
        while(true && !isInterrupted()){
            try {
            //从请求队列中取出一个请求
               final Request req = mQueue.take();
                byte[] result = null;
                try {
                //获取网络请求的内容 .req参数为从请求队列中取出来的
                    result = getNetWorkResponse(req);
                    if(result != null){
                        //当内容不为空的时候回调正常的返回结果
                        req.dispatchContent(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    req.onError(e);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                flag = false;
            }
        }
    }

    //获得网络响应的方法
     public byte[] getNetWorkResponse(Request request) throws Exception {
         if(TextUtils.isEmpty(request.getUrl())){
             throw new Exception("URL is null");
         }
         if(request.getMethod() == Request.Method.GET){
             return getResponseByGET(request);
         }
         if(request.getMethod() == Request.Method.POST){
             return getResponseByPost(request);
         }
         return null;
     }
    //获得get请求的方法
    private byte[] getResponseByGET(Request request) throws Exception {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        url = new URL(request.getUrl());
        connection = (HttpURLConnection) url.openConnection();//打开链接
        connection.setRequestMethod("GET");//设置请求方式
        connection.setConnectTimeout(5000);//设置响应的时间
        int code = connection.getResponseCode();//获得响应码
        if(code != 200){
            Log.d(TAG,"getNetWorkResponse() returned:response code error code = "+ code);
            throw new Exception("code error");
        }
            is = connection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] bytes = new byte[2048];
        while ((len = is.read(bytes))!= -1){
            baos.write(bytes,0,len);
        }
        is.close();
        return baos.toByteArray();
    }
    //获得post请求  的方式
    public byte[] getResponseByPost(Request request) throws Exception {
        URL url = null;
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStream is = null;
        url = new URL(request.getUrl());
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置请求方式
        connection.setConnectTimeout(5000);//设置连接的时间
        connection.setDoOutput(true);//允许获得输出流，默认是false

        //设置上传参数的字节长度
        String str = getPostEncodeString(request);
        byte[] content = null;
        if(str != null){
            content = str.getBytes();
            connection.setRequestProperty("content-length",content.length+"");
        }
        os = connection.getOutputStream();//获得输出流
        if(content != null){
            os.write(content);
            os.flush();
        }

       int code =  connection.getResponseCode();
        if(code != 200){
            Log.d(TAG,"getNetWorkResponse() returned:response code error code = " + code);
            throw new Exception("code error");
        }
        is = connection.getInputStream();//获得输入流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] bytes = new byte[2048];
        while ((is.read(bytes))!=-1){
            baos.write(bytes,0,len);
        }
        is.close();
        os.close();

        return baos.toByteArray();
    }
    //获得设置post请求后所上传的参数
    public String getPostEncodeString(Request request){
        HashMap<String,String> params =  request.getPostParams();
        StringBuilder sb = new StringBuilder();//非线程安全
        if(params != null){
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            int i = 0;
            while (iterator.hasNext()){
                if(i > 0){
                    sb.append("&");
                }
                Map.Entry<String,String> value = iterator.next();
                String str = value.getKey() + "=" + value.getValue();
                sb.append(str);
                i++;
            }
            return sb.toString();
        }

        return null;
    }
}
