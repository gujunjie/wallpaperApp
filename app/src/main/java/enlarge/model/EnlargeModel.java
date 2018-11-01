package enlarge.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import register.model.Collection;
import register.model.User;


public class EnlargeModel implements IModel{

    private Realm realm;

    private String filepath;

    private String filename;

    @Override
    public void download(Context context, final String downloadUrl, final onDownloadListener listener) {


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
         filepath=Environment.getExternalStorageDirectory()+File.separator+"mydownload"+File.separator;
        FileDownloader.getImpl().create(downloadUrl).setPath( filepath + filename)
        .setListener(fileDownloadListener).start();

    }
    @Override
    public void saveImageUrl(String url, Context context,int imageWidth,int imageHeight,onSaveImageUrlListener listener) {
        SharedPreferences sp=context.getSharedPreferences("loginData",Context.MODE_PRIVATE);
        String name=sp.getString("loginAccount","");
        realm=Realm.getDefaultInstance();
        User user=realm.where(User.class).equalTo("userName",name).findFirst();
        realm.beginTransaction();
        Collection collection=new Collection();
        collection.setCollectionImageUrl(url);
        collection.setImageWidth(imageWidth);
        collection.setImageHeight(imageHeight);
        if(user==null)
        {

            user=new User();
            user.setUserName(name);
            RealmList<Collection> list=new RealmList<>();
            list.add(collection);
            user.setList(list);
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
            realm.close();
            listener.onsaveImageUrlSuccess();
        }else
        {

            if(user.getList()==null)
            {
                RealmList<Collection> list=new RealmList<>();
                list.add(collection);
                user.setList(list);
                realm.copyToRealmOrUpdate(user);
                realm.commitTransaction();
                realm.close();
                listener.onsaveImageUrlSuccess();
            }else
            {
                user.getList().add(collection);
                realm.copyToRealmOrUpdate(user);
                realm.commitTransaction();
                realm.close();
                listener.onsaveImageUrlSuccess();
            }

        }


    }
}
