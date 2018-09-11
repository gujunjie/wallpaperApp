package car.model;

import beauty.model.BaiduImage;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClientCar {

    @GET("imgs/?col=汽车&tag=跑车&sort=0&pn=60&rn=60&p=channel&from=1")
    Observable<BaiduImage> getImage();

}