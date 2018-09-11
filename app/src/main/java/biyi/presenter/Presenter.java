package biyi.presenter;


import android.content.Context;

import java.io.File;

import base.BasePresenter;
import biyi.model.BiyiModel;
import biyi.model.IModel;
import biyi.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetBiyiImageUrlListener,IModel.onbiyidownloadListener,IModel.onsavebiyiCollectionListener {

    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new BiyiModel();
    }


    public void getBiyiImageUrl()
    {
        iModel.getBiyiImageUrl(this);
    }

    public void biyidownload(Context context,String downloadurl)
    {
        iModel.biyidownload(context,downloadurl,this);
    }

    public void savebiyiCollection(String imageurl,Context context)
    {
        iModel.savebiyiCollection(imageurl,context,this);
    }

    @Override
    public void onGetBiyiImageUrlSuccess(String url) {
        iView.showBiyiImage(url);
    }

    @Override
    public void onGetBiyiImageUrlFailure() {
          iView.netWorkError();
    }

    @Override
    public void onbiyidownloadSuccess(File file, String filename) {
        iView.tipsbiyiDownloadSuccess(file,filename);
    }

    @Override
    public void onbiyidownloadFailure() {
           iView.tipsbiyiDownloadFailure();
    }


    @Override
    public void savebiyiCollectionSuccess() {
        iView.tipsbiyiCollectionSuccess();
    }

    @Override
    public void savebiyiCollectionFailure() {

    }
}
