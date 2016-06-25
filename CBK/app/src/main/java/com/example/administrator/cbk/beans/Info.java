package com.example.administrator.cbk.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class Info implements Parcelable {
    private String title;//资讯标题
    private int infoclass;//分类
    private String img;//图片
    private String description;//描述
    private String keywords;//关键字
    private int count ;//访问次数
    private int fcount;//收藏数
    private int rcount;//评论读数
    private String time;
    private String message;
    private int id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Info(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInfoclass() {
        return infoclass;
    }

    public void setInfoclass(int infoclass) {
        this.infoclass = infoclass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected Info(Parcel in) {
        img = in.readString();
        description = in.readString();
        rcount = in.readInt();
        time = in.readString();
        keywords = in.readString();
        message = in.readString();
        title = in.readString();

    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(description);
        dest.writeInt(rcount);
        dest.writeString(time);
        dest.writeString(keywords);
        dest.writeString(message);
        dest.writeString(title);

    }
}