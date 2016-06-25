package com.example.administrator.cbk.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.administrator.cbk.App.ConstantKey;
import com.example.administrator.cbk.R;
import com.example.administrator.cbk.Utils.Pref_Utils;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(LoadingActivity.this,WelcomeActivity.class);
                if(!getFirstOpenFlag()){
                    intent.setClass(LoadingActivity.this,HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },3000);
    }
    public boolean getFirstOpenFlag(){
        return Pref_Utils.getBoolean(this, ConstantKey.PER_KEY_FIRST_OPEN, true);
    }
}
