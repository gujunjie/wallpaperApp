package com.example.abc.kantu;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MyFragmentPageAdapter extends FragmentPagerAdapter{

    private  String[] titleName={"风景","美女","汽车","动漫","影视","游戏"};

    private List<Fragment> list;

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> list)
    {
        super(fm);
        this.list=list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleName[position];
    }
}
