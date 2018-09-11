package beauty.model;

import android.content.Context;

import java.util.List;

public interface IBeautyModel {
    void getContext(Context context);
    void getImageList(onGetImageListListener listener);

    interface onGetImageListListener
    {
        void onSuccess(List<BaiduImage.ImgsBean> list);
        void onFailure();
    }
}
