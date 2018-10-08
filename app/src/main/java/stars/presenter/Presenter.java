package stars.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import movie.model.IMovieModel;
import movie.model.MovieModel;
import movie.view.IMovieView;
import stars.model.IStarsModel;
import stars.model.StarsModel;
import stars.view.IStarsView;


public class Presenter extends BasePresenter<IStarsView> implements IStarsModel.onGetImageListListener{

    private IStarsView iStarsView;
    private IStarsModel iStarsModel;


    public Presenter(IStarsView iStarsView)
    {
        this.iStarsView=iStarsView;
        iStarsModel=new StarsModel();

    }

    public void getImageList(Context context)
    {
        iStarsModel.getContext(context);
        iStarsModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iStarsView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
