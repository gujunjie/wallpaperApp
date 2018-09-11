package movie.view;

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
import movie.presenter.Presenter;

public class MovieFragment extends BaseFragment<IMovieView,Presenter> implements IMovieView {


    private Presenter presenter;

    private RecyclerView rvMovie;

    private List<BaiduImage.ImgsBean> list=new ArrayList<>();

    private SwipeRefreshLayout rfMovie;

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
        View view = inflater.inflate(R.layout.movie_layout, container, false);
        rvMovie=(RecyclerView)view.findViewById(R.id.rv_movie);
        rfMovie=(SwipeRefreshLayout)view.findViewById(R.id.refresh_movie);





        refresh();


        return view;
    }


    @Override
    public void init(List<BaiduImage.ImgsBean> list) {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvMovie.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

       adapter=new PhotoAdapter(list,getActivity());

        rvMovie.setAdapter(adapter);
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }






    public void refresh()
    {
        rfMovie.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        rfMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               presenter.getImageList(getActivity());
                rfMovie.setRefreshing(false);
            }
        });
    }


}
