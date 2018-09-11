package enlarge.presenter;

import android.content.Context;
import android.util.Log;

import java.io.File;

import base.BasePresenter;
import enlarge.model.EnlargeModel;
import enlarge.model.IModel;
import enlarge.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onSaveImageUrlListener,IModel.onDownloadListener {

    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new EnlargeModel();
    }

    public void saveCollection(String url, Context context)
    {
        iModel.saveImageUrl(url,context,this);
    }

    public void download(Context context,String downloadUrl)
    {
        iModel.download(context,downloadUrl,this);
    }

    @Override
    public void onDownloadSuccess(File file, String fileName) {
        iView.tipsDownloadSuccess(file,fileName);

}
    @Override
    public void onDownloadFailure() {
        iView.tipsDownloadFailure();
    }

    @Override
    public void onsaveImageUrlSuccess() {
        iView.tipsCollectionSuccess();
    }

    @Override
    public void onsaveImageUrlFailure() {

    }
}
