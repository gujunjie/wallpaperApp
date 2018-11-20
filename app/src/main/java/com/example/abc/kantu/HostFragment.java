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

import adapter.MyHostFragmentPageAdapter;
import beauty.view.BeautyFragment;
import building.view.BuildingFragment;
import car.view.CarFragment;
import comic.view.ComicFragment;
import creative.view.CreativeFragment;
import food.view.FoodFragment;
import game.view.GameFragment;
import landscape.view.LandscapeFragment;
import movie.view.MovieFragment;
import sports.view.SportsFragment;
import stars.view.StarsFragment;

public class HostFragment extends Fragment {


    private ViewPager vp_host;
    private TabLayout tl_host;

    private List<Fragment> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.hostfragment_layout,container,false);

        vp_host=(ViewPager)view.findViewById(R.id.vp_host);
        tl_host=(TabLayout)view.findViewById(R.id.tl_host);

        initUI();

       return view;
    }

    public void initUI()
    {
        list = new ArrayList<>();
        list.add(new LandscapeFragment());
        list.add(new BeautyFragment());
        list.add(new CarFragment());
        list.add(new ComicFragment());
        list.add(new MovieFragment());
        list.add(new GameFragment());
        list.add(new StarsFragment());
        list.add(new FoodFragment());
        list.add(new SportsFragment());
        list.add(new CreativeFragment());
        list.add(new BuildingFragment());

        MyHostFragmentPageAdapter adapter = new MyHostFragmentPageAdapter(getChildFragmentManager(), list);
        vp_host.setAdapter(adapter);
        tl_host.setupWithViewPager(vp_host);
    }
}
