package video.model;

import android.content.Context;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import util.CacheInterceptor;

public class VideoModel implements IModel{




    @Override
    public void getVideoData(Context context, final onGetVideoDataListener listener) {
        File cacheFile=new File(context.getExternalCacheDir().toString(),"cache");

        int cacheSize=10*1024*1024;

        Cache cache=new Cache(cacheFile,cacheSize);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new CacheInterceptor(context))
                .addNetworkInterceptor(new CacheInterceptor(context))
                .cache(cache)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.apiopen.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        UserClient userClient=retrofit.create(UserClient.class);

        userClient.getVideoBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoBean value) {
                         listener.onGetVideoDataSuccess(value.getResult());
                    }

                    @Override
                    public void onError(Throwable e) {
                         listener.onGetVideoDataFailure("获取数据失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
