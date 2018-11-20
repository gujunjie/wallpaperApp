package video.model;

import android.content.Context;

import java.util.List;

public interface IModel {

    void getVideoData(Context context,onGetVideoDataListener listener);

    interface onGetVideoDataListener
    {
        void onGetVideoDataSuccess(List<VideoBean.ResultBean> list);
        void onGetVideoDataFailure(String message);
    }
}
