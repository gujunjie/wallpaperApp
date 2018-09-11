package enlargecollection.model;

import android.content.Context;

import java.io.File;

public interface IModel {

    void collectionDownload(Context context, String downloadUrl,onCollectionDownloadListener listener);

    interface onCollectionDownloadListener
    {
        void onDownloadSuccess(File file, String fileName);
        void onDownloadFailure();
    }

    void deleteCollection(Context context,String imageUrl,onDeleteCollectionListener listener);

    interface onDeleteCollectionListener
    {
        void onDeleteCollectionSuccess();
    }

}
