package game.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import car.model.CarModel;
import car.model.ICarModel;
import car.view.ICarView;
import game.model.GameModel;
import game.model.IGameModel;
import game.view.IGameView;


public class Presenter extends BasePresenter<IGameView> implements IGameModel.onGetImageListListener{

    private IGameView iGameView;
    private IGameModel iGameModel;


    public Presenter(IGameView iGameView)
    {
        this.iGameView=iGameView;
        iGameModel=new GameModel();

    }

    public void getImageList(Context context)
    {
        iGameModel.getContext(context);
        iGameModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iGameView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
