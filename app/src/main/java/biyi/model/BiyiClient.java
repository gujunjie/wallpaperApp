package biyi.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BiyiClient {

    @GET("?format=js&idx=0&n=1&mkt=zh-CN")
    Observable<BiyiImage> getImageJson();
}
