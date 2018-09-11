package comic.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import beauty.model.BeautyModel;
import beauty.model.IBeautyModel;
import beauty.view.IBeautyView;
import comic.model.ComicModel;
import comic.model.IComicModel;
import comic.view.IComicView;


public class Presenter extends BasePresenter<IComicView> implements IComicModel.onGetImageListListener{

    private IComicView iComicView;
    private IComicModel iComicModel;


    public Presenter(IComicView comicView)
    {
        this.iComicView=comicView;
        iComicModel=new ComicModel();

    }

    public void getImageList(Context context)
    {
        iComicModel.getContext(context);
        iComicModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iComicView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
