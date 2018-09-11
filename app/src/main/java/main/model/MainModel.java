package main.model;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import register.model.User;

public class MainModel implements IModel{

    Realm realm;

    private Uri imageUri;

    private AppCompatActivity activity;

    private String name;

    @Override
    public void autoLogin(Context context,onAutoLoginListener listener) {
        SharedPreferences sp=context.getSharedPreferences("loginData",Context.MODE_PRIVATE);
        if(sp.getBoolean("isLogin",false))
        {
            String name=sp.getString("loginAccount","");
            realm=Realm.getDefaultInstance();
            User user=realm.where(User.class).equalTo("userName",name).findFirst();


                if(user!=null)
                {
                    if(user.getIcon()!=null)
                    {
                        Bitmap bitmap=Bytes2Bimap(user.getIcon());
                        listener.onSuccess(name,bitmap);
                    }else
                    {
                        listener.onFailure(name);
                    }

                }else {
                    listener.onFailure(name);
                }

            realm.close();
        }
    }

    @Override
    public void getImagePath(Context context, Intent data, onGetImagePathListener listener) {
        Log.d("text", "getImagePath: ");
        handleImageOnKitKat(context,data,listener);
    }

    @Override
    public void getUserIcon(String userName, onGetUserDataListener listener) {
        realm=Realm.getDefaultInstance();
        RealmResults<User> results=realm.where(User.class).equalTo("userName",userName).findAll();

        if(results.size()==0)
        {
            Log.d("text", "getUserIcon: "+"null");
        }else
        {
            if(results.get(0).getIcon()!=null)
            {
                listener.onSuccess(Bytes2Bimap(results.get(0).getIcon()));
            }

        }
        realm.close();
    }

    @Override
    public void getImageUri(Context context,onGetImageUriListener listener) {
        activity=(AppCompatActivity)context;

        File outputImage=new File(context.getExternalCacheDir(),"output_image.jpg");
        try
        {
            if(outputImage.exists())
            {
                outputImage.delete();
            }else
                {
                outputImage.createNewFile();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24)
        {
             imageUri= FileProvider.getUriForFile(context,"com.example.kantu.fileprovider",outputImage);
        }else {
            imageUri=Uri.fromFile(outputImage);
        }
        listener.onSuccess(imageUri);
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        activity.startActivityForResult(intent,2);
    }

    @Override
    public void saveUserIcon(Context context, final Bitmap bitmap, final onSaveUserIconListener listener) {
        SharedPreferences sp=context.getSharedPreferences("loginData",Context.MODE_PRIVATE);
        name=sp.getString("loginAccount","");
        realm=Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> results = realm.where(User.class).equalTo("userName", name).findAll();
                if(results.size()==0)
                {
                     User user=new User();
                     user.setUserName(name);
                    user.setIcon(Bitmap2Bytes(bitmap));
                    realm.copyToRealmOrUpdate(user);
                }else {
                    User user = results.get(0);
                    user.setIcon(Bitmap2Bytes(bitmap));
                    realm.copyToRealmOrUpdate(user);
                }

            }


        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                listener.onSuccess();
            }
        });



    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Context context,Intent data,onGetImagePathListener listener)
    {

        Log.d("text", "handleImageOnKitKat: ");
        String imagePath=null;

        Uri uri=data.getData();

        if(DocumentsContract.isDocumentUri(context,uri))
        {
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority()))
            {
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context, contentUri, null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme()))
            {
                imagePath=getImagePath(context,uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme()))
            {
                imagePath=uri.getPath();
            }
            listener.onSuccess(imagePath);


    }

    public String getImagePath(Context context,Uri uri,String selection)
    {
        String path=null;
        Cursor cursor=context.getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
