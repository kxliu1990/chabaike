package com.example.administrator.cbk.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.cbk.R;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView collect,watch,info,opinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        initView();

        collect.setOnClickListener(this);
        watch.setOnClickListener(this);
        info.setOnClickListener(this);
        opinion.setOnClickListener(this);
    }

    private void initView() {
        collect = (TextView) findViewById(R.id.more_tv_collect);
        watch = (TextView) findViewById(R.id.more_tv_watch);
        info = (TextView) findViewById(R.id.more_tv_info);
        opinion = (TextView) findViewById(R.id.more_tv_opinion);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_tv_collect:
                break;
            case R.id.more_tv_watch:
                break;
            case R.id.more_tv_info:
                break;
            case R.id.more_tv_opinion:
                break;
        }
    }
}
