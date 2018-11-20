package newscar.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import tuijian.model.NewsBean;

public interface UserClient {

    @GET("journalismApi")
    Observable<NewsBean> getNewsBean();
}
