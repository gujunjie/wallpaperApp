package util;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;

public class CacheInterceptor implements Interceptor {

    private Context context;

    public CacheInterceptor(Context context)
    {
        this.context=context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtil.isNetworkConnected(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        okhttp3.Response originalResponse=chain.proceed(request);
        if (NetworkUtil.isNetworkConnected(context)) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置(注掉部分)
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    //.header("Cache-Control", "max-age=3600")
                    .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            int maxAge= 60 * 60;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
