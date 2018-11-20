package video.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abc.kantu.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyVideoAdapter;
import base.BaseFragment;
import base.BasePresenter;
import video.model.VideoBean;
import video.presenter.Presenter;

public class VideoFragment extends BaseFragment<IView,Presenter> implements IView{

    private Presenter presenter;

    private List<VideoBean.ResultBean> list=new ArrayList<>();

    private RecyclerView rv_video;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.videofragment_layout,container,false);

        rv_video=(RecyclerView)view.findViewById(R.id.rv_video);
        presenter.getVideoData(getActivity());

        return view;
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }

    @Override
    public void showVideoData(List<VideoBean.ResultBean> list) {
        MyVideoAdapter adapter=new MyVideoAdapter(getActivity(),list);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rv_video.setLayoutManager(manager);
        rv_video.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
