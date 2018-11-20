package beauty.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


import base.BasePresenter;
import beauty.model.BaiduImage;
import beauty.model.BeautyModel;
import beauty.model.IBeautyModel;
import beauty.view.IBeautyView;


public class Presenter extends BasePresenter<IBeautyView> implements IBeautyModel.onGetImageListListener{

    private IBeautyView iBeautyView;
    private IBeautyModel iBeautyModel;


    public Presenter(IBeautyView iBeautyView)
    {
        this.iBeautyView=iBeautyView;
        iBeautyModel=new BeautyModel();

    }

    public void getImageList(Context context)
    {
        iBeautyModel.getContext(context);
        iBeautyModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iBeautyView.init(list);
    }

    @Override
    public void onFailure() {
        iBeautyView.netWorkError();
    }
}
