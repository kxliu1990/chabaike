package com.example.administrator.cbk.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.cbk.Adapter.InforListAdapter;
import com.example.administrator.cbk.R;
import com.example.administrator.cbk.UI.Activity.DetailActivity;
import com.example.administrator.cbk.beans.Info;
import com.example.administrator.httplib.HttpHelper;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class ContentFragment extends Fragment implements ListView.OnItemClickListener {
    private static final String TAG = "ContentFragment";
    private ListView listview;
    private int class_id;
    private InforListAdapter adapter;
    private PtrClassicFrameLayout mRefreshView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(getContext(), R.layout.fragment_content, null);
        initView(view);
        class_id = getArguments().getInt("id");
        if (bundle != null) {
            Parcelable[] par = bundle.getParcelableArray("cache");
            Info[] ins = (Info[]) bundle.getParcelableArray("cache");
            if (ins != null && ins.length != 0) {
                listinfos = Arrays.asList(ins);
                adapter = new InforListAdapter(listinfos);
                listview.setAdapter(adapter);
            } else {
                getDataFromNetWork();
            }
        } else {
            getDataFromNetWork();
        }


        return view;
    }

    private void initView(View view) {
        listview = (ListView) view.findViewById(R.id.frag_content_lv);

        listview.setOnItemClickListener(this);

        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);

        mRefreshView.setResistance(1.7f);//阻尼系数，默认1.7f，越大下拉越吃力
        mRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);//
        mRefreshView.setDurationToClose(200);
        mRefreshView.setDurationToCloseHeader(1000);
        // default is false
        mRefreshView.setPullToRefresh(true);
        // default is true
        mRefreshView.setKeepHeaderWhenRefresh(true);

        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDataFromNetWork();
            }
        });
    }

    //储存list中的内容的集合
    List<Info> listinfos = new ArrayList<Info>();

    private void getDataFromNetWork() {
        String url = "http://www.tngou.net/api/info/list?id=" + class_id;
        StringRequest sq = new StringRequest(url, Request.Method.GET, new Request.Callback<String>() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResonse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    List<Info> infos = parseJsonList(obj);
                   /* for(Info i : infos){

                    System.out.println("==========="+i.getTime());
                    }*/
                    if (infos != null) {
                        listinfos.clear();
                        listinfos.addAll(infos);
                    }
                    if (adapter == null) {
                        adapter = new InforListAdapter(listinfos);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listview.setAdapter(adapter);
                            }
                        });

                       /* new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                listview.setAdapter(adapter);
                            }
                        });*/



                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });
            }
        });

        HttpHelper.addRequest(sq);
    }

    private List<Info> parseJsonList(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return null;
        }
        JSONArray array = jsonObject.getJSONArray("tngou");
        if (array == null || array.length() == 0) {
            return null;
        }
        List<Info> list = new ArrayList<Info>();//储存每个item数据的数组
        Info info = null;
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            info = new Info();
            info.setDescription(obj.optString("description"));
            info.setRcount(obj.optInt("rcount"));
            long time = obj.getLong("time");
            String str = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss").format(time);
            info.setTime(str);
            info.setImg(obj.optString("img"));
            info.setId(obj.optInt("id"));
            info.setKeywords(obj.optString("keywords"));
            info.setTitle(obj.optString("title"));
            info.setDescription(obj.optString("description"));


            list.add(info);
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), DetailActivity.class);
        intent.putExtra("id",listinfos.get(position));
        startActivity(intent);
    }
}
