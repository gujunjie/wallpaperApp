package beauty.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import adapter.PhotoAdapter;
import com.example.abc.kantu.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import beauty.model.BaiduImage;
import beauty.presenter.Presenter;

public class BeautyFragment extends BaseFragment<IBeautyView,Presenter> implements IBeautyView {



    private Presenter presenter;

    private List<BaiduImage.ImgsBean> list;

    private RecyclerView rvLandscape;

    private SwipeRefreshLayout rfLandscape;

    private PhotoAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         list=new ArrayList<>();
         if(list.size()==0)
         {
             presenter.getImageList(getActivity());
         }else
         {
             init(list);
         }
        View view = inflater.inflate(R.layout.landscape_layout, container, false);
        rvLandscape=(RecyclerView)view.findViewById(R.id.rv_landscape);
        rfLandscape=(SwipeRefreshLayout)view.findViewById(R.id.refresh_landscape);



        refresh();
        return view;
    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    @Override
    public void init(List<BaiduImage.ImgsBean> list) {

        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvLandscape.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        adapter=new PhotoAdapter(list,getActivity());

        rvLandscape.setAdapter(adapter);
    }

    public void refresh()
    {
        rfLandscape.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfLandscape.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getImageList(getActivity());
                rfLandscape.setRefreshing(false);
            }
        });
    }

    @Override
    public void netWorkError() {
        Toast.makeText(getActivity(),"网络错误,请检查您的网络设置",Toast.LENGTH_SHORT).show();
    }
}
