package game.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import game.presenter.Presenter;

public class GameFragment extends BaseFragment<IGameView, Presenter> implements IGameView {
    private Presenter presenter;

    private RecyclerView rvGame;

    private List<BaiduImage.ImgsBean> list=new ArrayList<>();

    private SwipeRefreshLayout rfGame;

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

        View view = inflater.inflate(R.layout.game_layout, container, false);
        rvGame=(RecyclerView)view.findViewById(R.id.rv_game);
        rfGame=(SwipeRefreshLayout)view.findViewById(R.id.refresh_game);


        refresh();


        return view;
    }


    @Override
    public void init(List<BaiduImage.ImgsBean> list) {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvGame.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        adapter=new PhotoAdapter(list,getActivity());

        rvGame.setAdapter(adapter);
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }

    public void refresh()
    {
        rfGame.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfGame.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getImageList(getActivity());
                rfGame.setRefreshing(false);
            }
        });
    }


}
