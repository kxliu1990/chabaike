package com.example.administrator.cbk.Adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cbk.R;
import com.example.administrator.cbk.beans.Info;
import com.example.administrator.httplib.BitmapRequest;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class InforListAdapter extends BaseAdapter {
    private List<Info> list;

    public InforListAdapter(List<Info> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            //创建对象
           convertView = View.inflate(parent.getContext(), R.layout.content_lv_item, null);
            vh = new ViewHolder();
            vh.iv_icon = (ImageView) convertView.findViewById(R.id.content_iv);
            vh.tv_desc = (TextView) convertView.findViewById(R.id.content_tv_desc);
            vh.tv_rcount = (TextView) convertView.findViewById(R.id.content_tv_read );
            vh.tv_time = (TextView) convertView.findViewById(R.id.content_tv_time);

            convertView.setTag(vh);//设置标签
        }
        //获得item中的对象
        Info info = (Info) getItem(position);
        vh = (ViewHolder) convertView.getTag();//取出viewholder的对象

        //填充控件
        vh.tv_time.setText(info.getTime());
        vh.tv_rcount.setText(info.getRcount()+"");
        vh.tv_desc.setText(info.getDescription());
        vh.iv_icon.setImageResource(R.mipmap.ic_launcher);
        loadBitmap(vh.iv_icon,"http://tnfs.tngou.net/image"+info.getImg()+"_100x100");

        return convertView;
    }

    //创建ViewHolder的类
    public class ViewHolder {
        public TextView tv_desc;
        public TextView tv_rcount;
        public TextView tv_time;
        public ImageView iv_icon;
    }
    //下载图片
    public void loadBitmap(final ImageView iv,final String url){
        iv.setTag(url);
        BitmapRequest br = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResonse(final Bitmap response) {
                if(iv != null &&  response != null && ((String)iv.getTag()).equals(url)){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(response);
                        }
                    });
                }
            }
        });
        HttpHelper.addRequest(br);
    }



































   /* private  static final String TAG = InforListAdapter.class.getSimpleName();
    private List<Info> infoList;

    public InforListAdapter(List<Info> infoList) {
        this.infoList = infoList;
    }

    //返回item的数量
    @Override
    public int getCount() {
        System.out.println(infoList.size());
        return infoList == null? 0 : infoList.size();
    }

    //根据下标返回item中的对象
    @Override
    public Object getItem(int position) {
        System.out.println(infoList.get(position));
        return infoList.get(position);
    }
    //返回item的下标
    @Override
    public long getItemId(int position) {
        System.out.println(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.content_lv_item,null);
            holder = new ViewHolder();
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.content_iv);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.content_tv_desc);
            holder.tv_rcount = (TextView) convertView.findViewById(R.id.content_tv_read);
            holder.tv_time = (TextView) convertView.findViewById(R.id.content_tv_time);
            convertView.setTag(holder);
        }

        Info info = (Info) getItem(position);

        holder = (ViewHolder) convertView.getTag();
        holder.tv_time.setText(info.getTime());
        holder.tv_rcount.setText(info.getRcount()+"");
        holder.tv_desc.setText(info.getDescription());
        holder.iv_icon.setImageResource(R.mipmap.ic_launcher);
        ImageLoad(holder.iv_icon,"http://tnfs.tngou.net/image"+ info.getImg()+"_100x100");

        return convertView;
    }

    //创建ViewHolder类
    public class ViewHolder{
        public ImageView iv_icon;
        public TextView tv_desc;
        public TextView tv_rcount;
        public TextView tv_time;
    }
    //下载图片
    public void ImageLoad(final ImageView iv, final String url){
        Log.d(TAG, "ImageLoad() returned: url=" + url);


        iv.setTag(url);

        BitmapRequest br = new BitmapRequest(url, Request.Method.GET, new Request.Callback<Bitmap>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResonse(final Bitmap response) {
                if(iv != null && response != null && ((String)iv.getTag()).equals(url)){
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(response);
                        }
                    });
                }
            }

        });

        ///////////????????
        HttpHelper.addRequest(br);
    }*/
}
