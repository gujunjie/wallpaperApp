package enlarge.model;

import android.content.Context;

import java.io.File;

public interface IModel {

    void saveImageUrl(String url, Context context,onSaveImageUrlListener listener);

    void download(Context context,String downloadUrl,onDownloadListener listener);

    interface onDownloadListener
    {
        void onDownloadSuccess(File file,String fileName);
        void onDownloadFailure();
    }

    interface onSaveImageUrlListener
    {
        void onsaveImageUrlSuccess();
        void onsaveImageUrlFailure();
    }

}
