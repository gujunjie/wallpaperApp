package car.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import car.model.CarModel;
import car.model.ICarModel;
import car.view.ICarView;
import comic.model.ComicModel;
import comic.model.IComicModel;
import comic.view.IComicView;


public class Presenter extends BasePresenter<ICarView> implements ICarModel.onGetImageListListener{

    private ICarView iCarView;
    private ICarModel iCarModel;


    public Presenter(ICarView iCarView)
    {
        this.iCarView=iCarView;
        iCarModel=new CarModel();

    }

    public void getImageList(Context context)
    {
        iCarModel.getContext(context);
        iCarModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iCarView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
