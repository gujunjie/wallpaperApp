package register.model;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import movie.model.RegisterBean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterModel  implements IModel{

    Realm realm;

    @Override
    public void getRegisterMessage(String userName, String password, final onGetRegisterListener listener) {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UserClient client=retrofit.create(UserClient.class);

        client.register("27bfd0a1855c8",userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterBean value) {
                           listener.onSuccess(value.getRetCode(),value.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                           listener.onFailure("网络错误,请检查网络设置");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void saveInDataBase(String userName, onSaveInDataBaseListener listener) {
          realm=Realm.getDefaultInstance();
          realm.beginTransaction();
          User user=new User();
          user.setUserName(userName);
          realm.copyToRealmOrUpdate(user);
          realm.commitTransaction();
          realm.close();
          listener.onSuccess();
    }


}
