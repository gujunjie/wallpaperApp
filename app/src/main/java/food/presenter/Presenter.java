package food.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import food.model.FoodModel;
import food.model.IFoodModel;
import food.view.IFoodView;
import game.model.GameModel;
import game.model.IGameModel;
import game.view.IGameView;


public class Presenter extends BasePresenter<IFoodView> implements IFoodModel.onGetImageListListener{

    private IFoodView iFoodView;
    private IFoodModel iFoodModel;


    public Presenter(IFoodView iFoodView)
    {
        this.iFoodView=iFoodView;
        iFoodModel=new FoodModel();

    }

    public void getImageList(Context context)
    {
        iFoodModel.getContext(context);
        iFoodModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iFoodView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
