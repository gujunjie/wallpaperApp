package landscape.model;

import beauty.model.BaiduImage;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClientLandscape {

    @GET("imgs/?col=壁纸&tag=风景&sort=0&pn=60&rn=60&p=channel&from=1")
    Observable<BaiduImage> getImage();

}