package com.example.administrator.httplib;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class RequestQueue  {
    //阻塞的请求队列
    private BlockingDeque<Request> requestQueue = new LinkedBlockingDeque<>();
    //后台访问网络的线程个数
    private final int MAX_THREADS = 3;
    //后台的线程引用
    private NetWorkDispatcher[] workers = new NetWorkDispatcher[MAX_THREADS];

    public RequestQueue(){
        initDispatcher();
    }

    private void initDispatcher() {
        for(int i =0; i < workers.length;i++){
            //创建一个访问网络的线程类
            workers[i] = new NetWorkDispatcher(requestQueue);
            workers[i].start();
        }

    }
    //将请求添加到队列中的方法
    public void addRequest(Request request){
        requestQueue.add(request);
    }
    //终止线程
    public void stop(){
            for(int i = 0; i<workers.length; i++){
                workers[i].flag = false;
                workers[i].interrupt();
            }
    }

}
