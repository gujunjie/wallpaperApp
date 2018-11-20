package gengduo.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abc.kantu.R;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsAdapter;
import base.BaseFragment;
import gengduo.presenter.Presenter;
import tuijian.model.NewList;
import tuijian.model.NewsBean;

public class GengDuoFragment extends BaseFragment<IView,Presenter> implements IView {


    private Presenter presenter;

    private RecyclerView rv_newsTuiJian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.tuijianfragment_layout,container,false);
        presenter.getNewsBean(getActivity());


        rv_newsTuiJian=(RecyclerView)view.findViewById(R.id.rv_newsTuiJian);
        return view;
    }

    @Override
    public Presenter createPresenter() {

        presenter=new Presenter(this);
        return presenter;
    }

    @Override
    public void showNewsData(NewsBean.DataBean dataBean) {
        List<NewsBean.DataBean.DyBean> dyBeanList=dataBean.getDy();
        List<NewList> newList=new ArrayList<>();

        newList.addAll(dyBeanList);

        int size=newList.size();

        for(int i=0;i<size;i++)
        {
            if(newList.get(i).returnTitle()==null)
            {
                newList.remove(i);
                size--;
            }if(newList.get(i).returnPicInfoList()==null)
             {
                 newList.remove(i);
                 size--;
             }if(newList.get(i).returnPicInfoSize()==0)
           {
            newList.remove(i);
            size--;
           }
        }


        NewsAdapter adapter=new NewsAdapter(getActivity(),newList);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rv_newsTuiJian.setLayoutManager(manager);
        rv_newsTuiJian.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
