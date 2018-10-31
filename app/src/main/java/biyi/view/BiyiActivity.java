package biyi.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.R;

import java.io.File;
import java.io.FileNotFoundException;

import base.BaseActivity;
import biyi.presenter.Presenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.ShareUtil;

public class BiyiActivity extends BaseActivity<IView, Presenter> implements IView {

    @BindView(R.id.iv_biyiImage)
    ImageView ivBiyiImage;
    @BindView(R.id.tv_showbiyiSize)
    TextView tvShowbiyiSize;
    private Presenter presenter;

    private String biyiImageUrl;

    private boolean isDownload = false;//该图片是否下载

    public static final String CHANNEL_ID = "channel_id_1";
    public static final String CHANNEL_NAME = "channel_name_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biyi);
        ButterKnife.bind(this);
        immersionBar();

        presenter.getBiyiImageUrl();

    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    @OnClick({R.id.iv_biyiImage, R.id.btn_biyidownload, R.id.btn_asbiyiWallpaper, R.id.btn_biyicollect,R.id.btn_asbiyiShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_biyiImage:
                finish();
                break;
            case R.id.btn_biyicollect:
                SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
                if (sp.getBoolean("isLogin", false)) {
                    presenter.savebiyiCollection(biyiImageUrl, this);
                } else {
                    Toast.makeText(this, "请先登录您的账号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_biyidownload:
                if (!isDownload) {
                    presenter.biyidownload(BiyiActivity.this, biyiImageUrl);
                    Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BiyiActivity.this, "该图片已经下载", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_asbiyiWallpaper:

                if (isDownload) {
                    setWallpaper();
                    //图片已经下载就设置壁纸
                } else {
                    presenter.biyidownload(BiyiActivity.this, biyiImageUrl);
                    setWallpaper();//没有下载就先下载再设置壁纸

                }
                break;
            case R.id.btn_asbiyiShare:
                ShareUtil.showShare(BiyiActivity.this,biyiImageUrl);
                break;
        }
    }

    public void immersionBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void showBiyiImage(String url) {
        Glide.with(this).load("https://cn.bing.com" + url).into(ivBiyiImage);
        biyiImageUrl = "https://cn.bing.com" + url;
    }

    @Override
    public void netWorkError() {
        Toast.makeText(BiyiActivity.this, "网络异常,请检查您的网络设置", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tipsbiyiDownloadSuccess(File file, String filename) {
        createNotification("下载完成");
        //将图片插入本地相册
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), filename, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
        isDownload = true;//该图片已经下载
    }

    @Override
    public void tipsbiyiDownloadFailure() {
        createNotification("下载失败");
    }

    public void createNotification(String message) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString()));
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            manager.createNotificationChannel(notificationChannel);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(message)
                    .setContentText("已保存至本地相册")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo)
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .build();
            manager.notify(1, notification);

        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString()));
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(message)
                    .setContentText("已保存至本地相册")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .build();
            manager.notify(1, notification);
        }
    }

    public void setWallpaper() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setMessage("你确定要设置该图片为壁纸吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    public void tipsbiyiCollectionSuccess() {
        Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
    }


}



