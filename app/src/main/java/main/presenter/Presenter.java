package main.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import base.BasePresenter;
import io.realm.Realm;
import main.model.IModel;
import main.model.MainModel;
import main.view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.onGetUserDataListener,IModel.onGetImageUriListener,IModel.onSaveUserIconListener,IModel.onGetImagePathListener,IModel.onAutoLoginListener {

    private IView iView;
    private IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new MainModel();
    }

    public void autoLogin(Context context)
    {
        iModel.autoLogin(context,this);
    }

    public void getUserIcon(String userName)
    {
        iModel.getUserIcon(userName,this);
    }

    public void getImagePath(Context context,Intent data)
    {
        iModel.getImagePath(context,data,this);
    }


    public void getImageUri(Context context)
    {
        iModel.getImageUri(context,this);
    }

    public void saveUserIcon(Context context,Bitmap bitmap)
    {
        iModel.saveUserIcon(context,bitmap,this);
    }



    @Override
    public void onSuccess(Bitmap bitmap) {
        iView.showUserIcon(bitmap);
    }

    @Override
    public void onFailure(Bitmap bitmap) {

    }

    @Override
    public void onSuccess(Uri imageUri) {
        iView.getImageUri(imageUri);
    }
    @Override
    public void onFailure(Uri imageUri) {

    }

    @Override
    public void onSuccess() {
        iView.saveIconSuccess();
    }

    @Override
    public void onSuccess(String imagePath) {
        iView.showUserIcon(imagePath);
    }

    @Override
    public void onSuccess(String name, Bitmap bitmap) {
        iView.autoLoginShowData(name,bitmap);
    }

    @Override
    public void onFailure(String name) {
        iView.showUserName(name);
    }
}
