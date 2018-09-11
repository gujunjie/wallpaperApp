package login.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import register.model.User;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class LoginModel implements IModel{

    Realm realm;

    @Override
    public void upDateLoginState(Context context,String userName) {

        SharedPreferences sp=context.getSharedPreferences("loginData",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",true);
        editor.putString("loginAccount",userName);
        editor.apply();



    }

    @Override
    public void getLoginMessage(String userName, String password, final onGetLoginMessageListener listener) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/user/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserClient client=retrofit.create(UserClient.class);

        client.login("27bfd0a1855c8",userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean value) {
                          listener.onSuccess(value.getRetCode(),value.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                           listener.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
