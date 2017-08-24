package com.yinhao.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yinhao.googleplay.R;
import com.yinhao.googleplay.ui.fragment.BaseFragment;
import com.yinhao.googleplay.ui.fragment.FragmentFactory;
import com.yinhao.googleplay.ui.view.PagerTab;
import com.yinhao.googleplay.util.UIUtils;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private PagerTab mPagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        mPagerTab.setViewPager(viewPager);//将指针和viewpager绑定在一起
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                //开始加载数据
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        private String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);
        }

        //返回当前位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        //fragment数量
        @Override
        public int getCount() {
            return mTabNames.length;
        }

        //返回页签标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }
    }
}
