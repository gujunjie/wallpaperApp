package sports.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abc.kantu.PhotoAdapter;
import com.example.abc.kantu.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import beauty.model.BaiduImage;
import sports.presenter.Presenter;


public class SportsFragment extends BaseFragment<ISportsView,Presenter> implements ISportsView {


    private Presenter presenter;

    private RecyclerView rvSports;

    private List<BaiduImage.ImgsBean> list=new ArrayList<>();

    private SwipeRefreshLayout rfSports;

    private PhotoAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(list.size()==0)
        {
            presenter.getImageList(getActivity());
        }else
        {
            init(list);
        }
        View view = inflater.inflate(R.layout.sports_layout, container, false);
        rvSports=(RecyclerView)view.findViewById(R.id.rv_sports);
        rfSports=(SwipeRefreshLayout)view.findViewById(R.id.refresh_sports);





        refresh();


        return view;
    }


    @Override
    public void init(List<BaiduImage.ImgsBean> list) {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvSports.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

       adapter=new PhotoAdapter(list,getActivity());

        rvSports.setAdapter(adapter);
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }






    public void refresh()
    {
        rfSports.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfSports.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               presenter.getImageList(getActivity());
                rfSports.setRefreshing(false);
            }
        });
    }


}
