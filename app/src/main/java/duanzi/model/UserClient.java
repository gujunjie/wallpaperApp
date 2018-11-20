package duanzi.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClient {

    @GET("getJoke?page=0&count=100&type=text")
    Observable<DuanZiBean> getDuanZiBean();
}
