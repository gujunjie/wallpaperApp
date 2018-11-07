package startup.model;

import android.content.Context;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Random;

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

public class StartupModel implements IStartupModel {


    @Override
    public void getImageUrl(Context context,final onGetImageUrlListener listener) {

//        File cacheFile=new File(context.getExternalCacheDir().toString(),"cache");
//
//        int cacheSize=10*1024*1024;
//
//        Cache cache=new Cache(cacheFile,cacheSize);
//
//        OkHttpClient client=new OkHttpClient.Builder()
//                .addInterceptor(new CacheInterceptor(context))
//                .addNetworkInterceptor(new CacheInterceptor(context))
//                .cache(cache)
//                .build();
//
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("https://api.ixiaowai.cn/gqapi/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(client)
//                .build();
//
//        UserClient cilent=retrofit.create(UserClient.class);
//
//        cilent.getImageUrl().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<XinlangImage>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(XinlangImage value) {
//                       listener.onGetImageUrlSuccess(value.getImgurl());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                       listener.onGetImageUrlFailure();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        Random random=new Random();
        int index=random.nextInt(50)+1;
        String imageUrl="https://gitee.com/gujunjie/imageServers/raw/master/startupWallpaper/"+index+".jpg";
        listener.onGetImageUrlSuccess(imageUrl);
    }
}
