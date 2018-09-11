package car.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import car.presenter.Presenter;

public class CarFragment extends BaseFragment<ICarView,Presenter> implements ICarView{

    private Presenter presenter;

    private RecyclerView rvCar;

    private List<BaiduImage.ImgsBean> list=new ArrayList<>();

    private SwipeRefreshLayout rfCar;

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
        View view = inflater.inflate(R.layout.car_layout, container, false);
        rvCar=(RecyclerView)view.findViewById(R.id.rv_car);
        rfCar=(SwipeRefreshLayout)view.findViewById(R.id.refresh_car);



        refresh();

        return view;
    }



    @Override
    public void init(List<BaiduImage.ImgsBean> list) {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvCar.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        adapter=new PhotoAdapter(list,getActivity());

        rvCar.setAdapter(adapter);
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }





    public void refresh()
    {

        rfCar.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfCar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getImageList(getActivity());
                rfCar.setRefreshing(false);
            }
        });
    }


}
