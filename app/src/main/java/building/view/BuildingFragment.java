package building.view;

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
import building.presenter.Presenter;

public class BuildingFragment extends BaseFragment<IBuildingView,Presenter> implements IBuildingView {

    private Presenter presenter;

    private RecyclerView rvBuilding;

    private List<BaiduImage.ImgsBean> list=new ArrayList<>();

    private SwipeRefreshLayout rfBuilding;

    private  PhotoAdapter adapter;



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
        View view = inflater.inflate(R.layout.building_layout, container, false);
        rvBuilding=(RecyclerView)view.findViewById(R.id.rv_building);
        rfBuilding=(SwipeRefreshLayout)view.findViewById(R.id.refresh_building);



        refresh();

        return view;
    }



    @Override
    public void init(List<BaiduImage.ImgsBean> list) {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvBuilding.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        adapter=new PhotoAdapter(list,getActivity());

        rvBuilding.setAdapter(adapter);
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }





    public void refresh()
    {

        rfBuilding.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfBuilding.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getImageList(getActivity());
                rfBuilding.setRefreshing(false);
            }
        });
    }


}
