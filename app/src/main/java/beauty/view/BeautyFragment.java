package beauty.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abc.kantu.PhotoAdapter;
import com.example.abc.kantu.R;

import java.util.ArrayList;
import java.util.List;

import base.BaseFragment;
import beauty.model.BaiduImage;
import beauty.presenter.Presenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BeautyFragment extends BaseFragment<IBeautyView,Presenter> implements IBeautyView {



    private Presenter presenter;

    private List<BaiduImage.ImgsBean> list;

    private RecyclerView rvBeauty;

    private SwipeRefreshLayout rfBeauty;

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
        View view = inflater.inflate(R.layout.beauty_layout, container, false);
        rvBeauty=(RecyclerView)view.findViewById(R.id.rv_beauty);
        rfBeauty=(SwipeRefreshLayout)view.findViewById(R.id.refresh_beauty);



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
        rvBeauty.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        adapter=new PhotoAdapter(list,getActivity());

        rvBeauty.setAdapter(adapter);
    }

    public void refresh()
    {
        rfBeauty.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfBeauty.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getImageList(getActivity());
                rfBeauty.setRefreshing(false);
            }
        });
    }

    @Override
    public void netWorkError() {
        Toast.makeText(getActivity(),"网络错误,请检查您的网络设置",Toast.LENGTH_SHORT).show();
    }
}
