package util;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

     public static Retrofit getRetrofit(Context context)
     {
         File cacheFile=new File(context.getExternalCacheDir().toString(),"cache");

         int cacheSize=10*1024*1024;

         Cache cache=new Cache(cacheFile,cacheSize);

         OkHttpClient client=new OkHttpClient.Builder()
                 .addInterceptor(new CacheInterceptor(context))
                 .addNetworkInterceptor(new CacheInterceptor(context))
                 .addNetworkInterceptor(new StethoInterceptor())
                 .cache(cache)
                 .build();


         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://image.baidu.com/data/")
                 .client(client)
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .build();

         return retrofit;
     }
}
