package sports.model;

import android.content.Context;

import java.util.List;

import beauty.model.BaiduImage;

public interface ISportsModel {
    void getContext(Context context);
    void getImageList(onGetImageListListener listener);

    interface onGetImageListListener
    {
        void onSuccess(List<BaiduImage.ImgsBean> list);
        void onFailure();
    }
}
