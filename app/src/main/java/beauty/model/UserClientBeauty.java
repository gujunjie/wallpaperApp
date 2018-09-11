package beauty.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClientBeauty {

    @GET("imgs/?col=壁纸&tag=美女&sort=0&pn=60&rn=60&p=channel&from=1")
    Observable<BaiduImage> getImage();

}