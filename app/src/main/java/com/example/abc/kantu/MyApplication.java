package com.example.abc.kantu;

import android.app.Application;

import com.mob.MobSDK;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        Realm.init(this);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name("kantu.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
     }
}
