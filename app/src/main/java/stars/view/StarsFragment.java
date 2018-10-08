package stars.view;

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
import stars.presenter.Presenter;


public class StarsFragment extends BaseFragment<IStarsView,Presenter> implements IStarsView {


    private Presenter presenter;

    private RecyclerView rvStars;

    private List<BaiduImage.ImgsBean> list=new ArrayList<>();

    private SwipeRefreshLayout rfStars;

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
        View view = inflater.inflate(R.layout.stars_layout, container, false);
        rvStars=(RecyclerView)view.findViewById(R.id.rv_stars);
        rfStars=(SwipeRefreshLayout)view.findViewById(R.id.refresh_stars);





        refresh();


        return view;
    }


    @Override
    public void init(List<BaiduImage.ImgsBean> list) {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvStars.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

       adapter=new PhotoAdapter(list,getActivity());

        rvStars.setAdapter(adapter);
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }






    public void refresh()
    {
        rfStars.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfStars.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               presenter.getImageList(getActivity());
                rfStars.setRefreshing(false);
            }
        });
    }


}
