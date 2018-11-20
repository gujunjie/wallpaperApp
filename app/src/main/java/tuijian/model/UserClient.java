package tuijian.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClient {

    @GET("journalismApi")
    Observable<NewsBean> getNewsBean();
}
