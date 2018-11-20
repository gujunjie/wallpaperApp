package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MyHostFragmentPageAdapter extends FragmentPagerAdapter{

    private  String[] titleName={"风景","美女","汽车","动漫","影视","游戏","明星","美食","体育","创意","建筑"};

    private List<Fragment> list;



    public MyHostFragmentPageAdapter(FragmentManager fm, List<Fragment> list)
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
