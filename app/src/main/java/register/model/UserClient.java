package register.model;

import io.reactivex.Observable;
import movie.model.RegisterBean;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserClient {

    @GET("rigister")
    Observable<RegisterBean> register(@Query("key") String key,
                                      @Query("username") String username,
                                      @Query("password") String password);
}
