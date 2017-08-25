package com.yinhao.googleplay.ui.fragment;

import java.util.HashMap;

/**
 * Created by yinhao on 2017/7/22.
 * 生产fragment工厂
 */

public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int pos) {
        //先从集合中取, 如果没有,才创建对象, 提高性能
        BaseFragment fragment = mFragmentMap.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();//主页
                    break;
                case 1:
                    fragment = new AppFragment();//应用
                    break;
                case 2:
                    fragment = new GameFragment();//游戏
                    break;
                case 3:
                    fragment = new SubjectFragment();//主题
                    break;
                case 4:
                    fragment = new RecommendFragment();//推荐
                    break;
                case 5:
                    fragment = new CategoryFragment();//分类
                    break;
                case 6:
                    fragment = new HotFragment();//热门
                    break;
            }
            mFragmentMap.put(pos, fragment);//将fragment保存在集合中
        }
        return fragment;
    }
}
