package sports.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import sports.model.ISportsModel;
import sports.model.SportsModel;
import sports.view.ISportsView;
import stars.model.IStarsModel;
import stars.model.StarsModel;
import stars.view.IStarsView;


public class Presenter extends BasePresenter<ISportsView> implements ISportsModel.onGetImageListListener{

    private ISportsView iSportsView;
    private ISportsModel iSportsModel;


    public Presenter(ISportsView iSportsView)
    {
        this.iSportsView=iSportsView;
        iSportsModel=new SportsModel();

    }

    public void getImageList(Context context)
    {
        iSportsModel.getContext(context);
        iSportsModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iSportsView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
