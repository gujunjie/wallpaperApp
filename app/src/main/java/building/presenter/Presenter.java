package building.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import building.model.BuildingModel;
import building.model.IBuildingModel;
import building.view.IBuildingView;
import car.model.CarModel;
import car.model.ICarModel;
import car.view.ICarView;


public class Presenter extends BasePresenter<IBuildingView> implements IBuildingModel.onGetImageListListener{

    private IBuildingView iBuildingView;
    private IBuildingModel iBuildingModel;


    public Presenter(IBuildingView iBuildingView)
    {
        this.iBuildingView=iBuildingView;
        iBuildingModel=new BuildingModel();

    }

    public void getImageList(Context context)
    {
        iBuildingModel.getContext(context);
        iBuildingModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iBuildingView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
