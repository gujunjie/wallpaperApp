package movie.presenter;

import android.content.Context;

import java.util.List;

import base.BasePresenter;
import beauty.model.BaiduImage;
import comic.model.ComicModel;
import comic.model.IComicModel;
import comic.view.IComicView;
import movie.model.IMovieModel;
import movie.model.MovieModel;
import movie.view.IMovieView;


public class Presenter extends BasePresenter<IMovieView> implements IMovieModel.onGetImageListListener{

    private IMovieView iMovieView;
    private IMovieModel iMovieModel;


    public Presenter(IMovieView iMovieView)
    {
        this.iMovieView=iMovieView;
        iMovieModel=new MovieModel();

    }

    public void getImageList(Context context)
    {
        iMovieModel.getContext(context);
        iMovieModel.getImageList(this);

    }

    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
        iMovieView.init(list);
    }

    @Override
    public void onFailure() {

    }
}
