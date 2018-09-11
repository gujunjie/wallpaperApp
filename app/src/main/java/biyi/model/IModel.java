package biyi.model;

import android.content.Context;

import java.io.File;

public interface IModel {

   void getBiyiImageUrl(onGetBiyiImageUrlListener listener);

   interface onGetBiyiImageUrlListener
   {
       void onGetBiyiImageUrlSuccess(String url);
       void onGetBiyiImageUrlFailure();
   }

   void biyidownload(Context context,String downloadurl,onbiyidownloadListener listener);

   interface onbiyidownloadListener
   {
       void onbiyidownloadSuccess(File file,String filename);
       void onbiyidownloadFailure();
   }

   void savebiyiCollection(String imageurl,Context context,onsavebiyiCollectionListener listener);

   interface onsavebiyiCollectionListener
   {
       void savebiyiCollectionSuccess();
       void savebiyiCollectionFailure();
   }

}
