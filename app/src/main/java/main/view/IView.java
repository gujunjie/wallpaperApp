package main.view;

import android.graphics.Bitmap;
import android.net.Uri;

import io.realm.Realm;

public interface IView {

    void autoLoginShowData(String name,Bitmap bitmap);

    void showUserIcon(Bitmap bitmap);

    void showUserIcon(String imagePath);

    void getImageUri(Uri imageUri);

    void saveIconSuccess();

    void showUserName(String name);
}
