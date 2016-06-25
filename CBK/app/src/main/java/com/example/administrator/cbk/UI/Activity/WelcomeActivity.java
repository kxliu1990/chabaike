package com.example.administrator.cbk.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.cbk.App.ConstantKey;
import com.example.administrator.cbk.R;
import com.example.administrator.cbk.Utils.Pref_Utils;

public class WelcomeActivity extends AppCompatActivity{
    private LinearLayout line_container;
    private ViewPager viewPager;
    private int[] image_id = {R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3};
    private ImageView[] imageViews = new ImageView[image_id.length];//存放imageview的数组

    private int pre_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter);

        initView();
        Pref_Utils.putBoolean(this, ConstantKey.PER_KEY_FIRST_OPEN,false);
    }

    private void initView() {

        line_container = (LinearLayout) findViewById(R.id.welcome_ll);

        //底部圆点的布局
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20,20);
        //背景图片的布局
        ViewGroup.LayoutParams lvp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT);
        //设置圆点的间隔
        lp.rightMargin = 5;
        for(int i = 0; i < image_id.length; i++){
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//把图片不按比例扩大、缩小到view的尺寸
            iv.setLayoutParams(lvp);
            imageViews[i] = iv;
            imageViews[i].setImageResource(image_id[i]);

            //当图片为第三张时设置按钮监听并跳转
            if(i == image_id.length-1){
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(WelcomeActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

                View view = new View(this);
            //设置第一张为默认
            if(i == 0){
                view.setBackgroundResource(R.mipmap.page_now);
            }else {
                view.setBackgroundResource(R.mipmap.page);
            }

            view.setLayoutParams(lp);
            line_container.addView(view);
        }

    }

    class MyViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

        @Override
        public int getCount() {
            return image_id.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position]);
            return imageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews[position]);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            line_container.getChildAt(position).setBackgroundResource(R.mipmap.page_now);
            line_container.getChildAt(pre_index).setBackgroundResource(R.mipmap.page);

            pre_index = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
