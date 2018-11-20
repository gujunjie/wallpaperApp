package com.example.abc.kantu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.MyNewsFragmentPageAdapter;
import gengduo.view.GengDuoFragment;
import keji.view.KeJiFragment;
import newscar.view.NewsCarFragment;
import newsports.view.NewSportFragment;
import war.view.WarFragment;
import tuijian.view.TuiJianFragment;
import caijing.view.CaiJingFragment;

public class NewsFragment extends Fragment {


    private ViewPager vp_news;
    private TabLayout tl_news;

    private List<Fragment> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.newsfragment_layout,container,false);

        vp_news=(ViewPager)view.findViewById(R.id.vp_news);
        tl_news=(TabLayout)view.findViewById(R.id.tl_news);

        initUI();
        return  view;
    }

    public void initUI()
    {
        list = new ArrayList<>();
        list.add(new TuiJianFragment());
        list.add(new KeJiFragment());
        list.add(new CaiJingFragment());
        list.add(new WarFragment());
        list.add(new NewsCarFragment());
        list.add(new NewSportFragment());
        list.add(new GengDuoFragment());

        MyNewsFragmentPageAdapter adapter = new MyNewsFragmentPageAdapter(getChildFragmentManager(), list);
        vp_news.setAdapter(adapter);
        tl_news.setupWithViewPager(vp_news);
    }
}
