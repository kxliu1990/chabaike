package com.example.administrator.cbk.UI.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.cbk.R;
import com.example.administrator.cbk.UI.Fragment.ContentFragment;
import com.example.administrator.cbk.beans.TabInfo;

public class HomeActivity extends AppCompatActivity{
    private TabLayout tab;
    private ViewPager viewpager;
    private TabInfo[] tabs = new TabInfo[]{
            new TabInfo("企业要闻",1),
            new TabInfo("医疗新闻",2),
            new TabInfo("生活贴士",3),
            new TabInfo("药品新闻",4),
            new TabInfo("食品新闻",5),
            new TabInfo("社会热点",6),
            new TabInfo("疾病快讯",7),

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();




    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.home_tablelayout);
        viewpager = (ViewPager) findViewById(R.id.home_viewpager);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

        tab.setTabMode(TabLayout.MODE_SCROLLABLE);//可滑动的tab
        tab.setupWithViewPager(viewpager);//将tab和viewpager联动起来

    }


    //自定义适配器继承FragmentStatePagerAdapter.该适配器用于item比较多的情况下
    class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ContentFragment cf = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",tabs[position].id);
            cf.setArguments(bundle);
            return cf;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position].name;
        }
    }


}
