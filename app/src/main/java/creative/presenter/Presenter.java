package creative.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import creative.model.CreativeModel;
import creative.model.ICreativeModel;
import creative.view.ICreativeView;
import food.model.FoodModel;
import food.model.IFoodModel;
import food.view.IFoodView;


public class Presenter extends BasePresenter<ICreativeView> implements ICreativeModel.onGetImageListListener{

    private ICreativeView iCreativeView;
    private ICreativeModel iCreativeModel;


    public Presenter(ICreativeView iCreativeView)
    {
        this.iCreativeView=iCreativeView;
        iCreativeModel=new CreativeModel();

    }

    public void getImageList(Context context)
    {
        iCreativeModel.getContext(context);
        iCreativeModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iCreativeView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
