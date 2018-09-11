package login.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserClient {

    @GET("login")
    Observable<LoginBean> login(@Query("key") String key,
                                @Query("username") String username,
                                @Query("password") String password);
}
