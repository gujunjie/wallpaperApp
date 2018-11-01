package biyi.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import register.model.Collection;
import register.model.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BiyiModel implements IModel {

    private Realm realm;

    private String filepath;

    private String filename;

    @Override
    public void savebiyiCollection(String imageurl, Context context, onsavebiyiCollectionListener listener) {
        SharedPreferences sp=context.getSharedPreferences("loginData",Context.MODE_PRIVATE);
        String name=sp.getString("loginAccount","");
        realm= Realm.getDefaultInstance();
        User user=realm.where(User.class).equalTo("userName",name).findFirst();
        realm.beginTransaction();
        Collection collection=new Collection();
        collection.setCollectionImageUrl(imageurl);
        collection.setImageWidth(1920);
        collection.setImageHeight(1080);
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
            listener.savebiyiCollectionSuccess();
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
                listener.savebiyiCollectionSuccess();
            }else
            {
                user.getList().add(collection);
                realm.copyToRealmOrUpdate(user);
                realm.commitTransaction();
                realm.close();
                listener.savebiyiCollectionSuccess();
            }

        }

    }

    @Override
    public void getBiyiImageUrl(final onGetBiyiImageUrlListener listener) {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://cn.bing.com/HPImageArchive.aspx/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        BiyiClient client=retrofit.create(BiyiClient.class);
        client.getImageJson().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BiyiImage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BiyiImage value) {
                        listener.onGetBiyiImageUrlSuccess(value.getImages().get(0).getUrl());
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onGetBiyiImageUrlFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void biyidownload(Context context, String downloadurl,final onbiyidownloadListener listener) {
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
                listener.onbiyidownloadSuccess(file,filename);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                listener.onbiyidownloadFailure();
            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        };
        filename=System.currentTimeMillis()+".jpg";
        filepath= Environment.getExternalStorageDirectory()+File.separator+"mydownload"+File.separator;
        FileDownloader.getImpl().create(downloadurl).setPath( filepath + filename)
                .setListener(fileDownloadListener).start();
    }
}
