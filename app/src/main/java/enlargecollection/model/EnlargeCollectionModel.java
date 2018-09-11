package enlargecollection.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;
import register.model.User;

public class EnlargeCollectionModel implements IModel {
    private Realm realm;

    private String filepath;

    private String filename;

    @Override
    public void deleteCollection(Context context, String imageUrl, onDeleteCollectionListener listener) {

        SharedPreferences sp=context.getSharedPreferences("loginData",Context.MODE_PRIVATE);
        String name=sp.getString("loginAccount","");
        realm=Realm.getDefaultInstance();

        User user=realm.where(User.class).equalTo("userName",name).findFirst();

        RealmList<Collection> list=user.getList();

        realm.beginTransaction();

        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getCollectionImageUrl().equals(imageUrl))
            {
                list.get(i).deleteFromRealm();
            }
        }

        realm.commitTransaction();
        realm.close();

        listener.onDeleteCollectionSuccess();


    }

    @Override
    public void collectionDownload(Context context, String downloadUrl,final onCollectionDownloadListener listener) {

        FileDownloader.setup(context);
        FileDownloadListener fileDownloadListener=new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {

                File file=new File(filepath,filename);
                listener.onDownloadSuccess(file,filename);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                listener.onDownloadFailure();
            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        };
        filename=System.currentTimeMillis()+".jpg";
        filepath= Environment.getExternalStorageDirectory()+File.separator+"mydownload"+File.separator;
        FileDownloader.getImpl().create(downloadUrl).setPath( filepath + filename)
                .setListener(fileDownloadListener).start();
    }
}
