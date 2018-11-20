package video.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import video.model.IModel;
import video.model.VideoBean;
import video.model.VideoModel;
import video.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetVideoDataListener{


    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new VideoModel();
    }

    public void getVideoData(Context context)
    {
         iModel.getVideoData(context,this);
    }

    @Override
    public void onGetVideoDataSuccess(List<VideoBean.ResultBean> list) {
        iView.showVideoData(list);
    }

    @Override
    public void onGetVideoDataFailure(String message) {
        iView.showError(message);
    }
}
