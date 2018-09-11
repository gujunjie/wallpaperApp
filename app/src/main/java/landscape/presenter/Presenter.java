package landscape.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import comic.model.ComicModel;
import comic.model.IComicModel;
import comic.view.IComicView;
import landscape.model.ILandscapeModel;
import landscape.model.LandscapeModel;
import landscape.view.ILandscapeView;


public class Presenter extends BasePresenter<ILandscapeView> implements ILandscapeModel.onGetImageListListener{

    private ILandscapeView iLandscapeView;
    private ILandscapeModel iLandscapeModel;


    public Presenter(ILandscapeView iLandscapeView)
    {
        this.iLandscapeView=iLandscapeView;
        iLandscapeModel=new LandscapeModel();

    }

    public void getImageList(Context context)
    {
        iLandscapeModel.getContext(context);
        iLandscapeModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iLandscapeView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
