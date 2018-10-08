package building.model;

import android.content.Context;

import beauty.model.BaiduImage;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.RetrofitUtil;

public class BuildingModel implements IBuildingModel {

    private Context context;

    @Override
    public void getContext(Context context) {
         this.context=context;
    }

    @Override
    public void getImageList(final onGetImageListListener listener) {


        UserClientBuilding client= RetrofitUtil.getRetrofit(context).create(UserClientBuilding.class);
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
