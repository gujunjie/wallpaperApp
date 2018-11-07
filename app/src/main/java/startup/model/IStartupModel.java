package startup.model;

import android.content.Context;

public interface IStartupModel {

    void getImageUrl(Context context,onGetImageUrlListener listener);

    interface onGetImageUrlListener
    {
        void onGetImageUrlSuccess(String imageUrl);
        void onGetImageUrlFailure();
    }
}
