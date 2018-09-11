package movie.model;

import android.content.Context;

import beauty.model.BaiduImage;
import comic.model.IComicModel;
import comic.model.UserClientComic;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import movie.view.IMovieView;
import util.RetrofitUtil;

public class MovieModel implements IMovieModel {

    private Context context;

    @Override
    public void getContext(Context context) {
         this.context=context;
    }

    @Override
    public void getImageList(final onGetImageListListener listener) {


        UserClientMovie client= RetrofitUtil.getRetrofit(context).create(UserClientMovie.class);
        client.getImage().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaiduImage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaiduImage value) {
                        listener.onSuccess(value.getImgs());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
