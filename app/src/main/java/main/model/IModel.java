package main.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import io.realm.Realm;

public interface IModel {

    void autoLogin(Context context,onAutoLoginListener listener);

    void getUserIcon(String userName,onGetUserDataListener listener);

    void getImageUri(Context context,onGetImageUriListener listener);

    void saveUserIcon(Context context,Bitmap bitmap,onSaveUserIconListener listener);

    void getImagePath(Context context,Intent data,onGetImagePathListener listener);

    interface onAutoLoginListener
    {
        void onSuccess(String name,Bitmap bitmap);
        void onFailure(String name);
    }

    interface onGetImagePathListener
    {
        void onSuccess(String imagePath);
    }

    interface onSaveUserIconListener
    {
        void onSuccess();
    }

    interface onGetImageUriListener
    {
        void onSuccess(Uri imageUri);
        void onFailure(Uri imageUri);
    }

    interface onGetUserDataListener
    {
        void onSuccess(Bitmap bitmap);
        void onFailure(Bitmap bitmap);
    }
}
