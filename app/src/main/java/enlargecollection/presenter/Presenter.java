package enlargecollection.presenter;

import android.content.Context;

import java.io.File;

import base.BasePresenter;
import enlargecollection.model.EnlargeCollectionModel;
import enlargecollection.model.IModel;
import enlargecollection.view.IView;


public class Presenter extends BasePresenter<IView> implements IModel.onCollectionDownloadListener,IModel.onDeleteCollectionListener{

    private IView iView;

    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new EnlargeCollectionModel();
    }

    public void collectionDownload(Context context, String downloadUrl)
    {
        iModel.collectionDownload(context,downloadUrl,this);
    }

    public void deleteCollection(Context context,String imageUrl)
    {
          iModel.deleteCollection(context,imageUrl,this);
    }


    @Override
    public void onDownloadSuccess(File file, String fileName) {
        iView.tipsCollectionDownloadSuccess(file,fileName);
    }

    @Override
    public void onDownloadFailure() {
        iView.tipsCollectionDownloadFailure();
    }

    @Override
    public void onDeleteCollectionSuccess() {
        iView.tipsDeleteCollectionSuccess();
    }
}
