package video.model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClient {

    @GET("getJoke?page=0&count=60&type=video")
    Observable<VideoBean> getVideoBean();
}
