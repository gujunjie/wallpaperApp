package startup.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClient {

    @GET("gqapi.php?return=json")
    Observable<XinlangImage> getImageUrl();
}
