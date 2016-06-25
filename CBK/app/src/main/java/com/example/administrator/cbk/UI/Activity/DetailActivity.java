package com.example.administrator.cbk.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.cbk.R;
import com.example.administrator.cbk.beans.Info;

import static com.example.administrator.cbk.R.id.detail_tv_content;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private TextView tv_title,tv_content,tv_keyword,tv_time;
    private int id = 0;
    private Info info;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_share:
                Intent intent = new Intent();

                break;
            case R.id.menu_more:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        Intent intent = getIntent();
        info = intent.getParcelableExtra("id");

        tv_keyword.setText(info.getKeywords());
        tv_title.setText(info.getTitle());
        tv_time.setText(info.getTime());
        tv_content.setText(info.getDescription());
    }

    private void initView() {
        tv_content = (TextView) findViewById(detail_tv_content);
        tv_keyword = (TextView) findViewById(R.id.detail_tv_keyword);
        tv_time = (TextView) findViewById(R.id.detail_tv_time);
        tv_title = (TextView) findViewById(R.id.detail_tv_title);
    }

}
