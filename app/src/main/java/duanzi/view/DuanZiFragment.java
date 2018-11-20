package duanzi.view;

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

import java.util.List;

import adapter.DuanZiAdapter;
import base.BaseFragment;
import duanzi.model.DuanZiBean;
import duanzi.presenter.Presenter;


public class DuanZiFragment extends BaseFragment<IView,Presenter> implements IView{



    private RecyclerView rv_video;

    private Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.videofragment_layout,container,false);

        presenter.getDuanZiData(getActivity());

        rv_video=(RecyclerView)view.findViewById(R.id.rv_video);
        return  view;
    }

    @Override
    public Presenter createPresenter() {
        presenter=new Presenter(this);
        return presenter;
    }

    @Override
    public void showDuanZiData(List<DuanZiBean.ResultBean> list) {
        DuanZiAdapter adapter=new DuanZiAdapter(getActivity(),list);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rv_video.setLayoutManager(manager);
        rv_video.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
