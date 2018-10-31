package enlargecollection.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.R;

import java.io.File;
import java.io.FileNotFoundException;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enlargecollection.presenter.Presenter;
import util.ShareUtil;

public class EnlargeCollectionActivity extends BaseActivity<IView, Presenter> implements IView {


    @BindView(R.id.iv_enlargeCollection)
    ImageView ivEnlargeCollection;
    @BindView(R.id.btn_deleteCollection)
    Button btnDeleteCollection;
    @BindView(R.id.btn_downloadCollection)
    Button btnDownloadCollection;
    @BindView(R.id.btn_asWallpaperCollection)
    Button btnAsWallpaperCollection;
    @BindView(R.id.tv_collection_showSize)
    TextView tvCollectionShowSize;
    private String imageUrl = "";

    private Presenter presenter;

    private boolean isDownload = false;//该图片是否下载

    public static final String CHANNEL_ID = "channel_id_1";
    public static final String CHANNEL_NAME = "channel_name_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_collection);
        ButterKnife.bind(this);

        immersionBar();
        initUI();
    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    public void initUI() {
        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("imageUrl");
        Glide.with(this)
                .load(imageUrl)
                .crossFade(500)
                .into(ivEnlargeCollection);
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

    @OnClick({R.id.iv_enlargeCollection, R.id.btn_deleteCollection, R.id.btn_downloadCollection, R.id.btn_asWallpaperCollection,R.id.btn_shareCollection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_enlargeCollection:
                finish();//离开页面
                break;
            case R.id.btn_deleteCollection:
                AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(this);
                normalDialog.setMessage("你确定要取消收藏吗?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteCollection(EnlargeCollectionActivity.this, imageUrl);
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


                break;
            case R.id.btn_downloadCollection:
                if (!isDownload) {
                    presenter.collectionDownload(this, imageUrl);
                    Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "该图片已经下载", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_asWallpaperCollection:
                if (isDownload) {
                    setWallpaper();
                    //图片已经下载就设置壁纸
                } else {
                    presenter.collectionDownload(this, imageUrl);
                    setWallpaper();//没有下载就先下载再设置壁纸
                }
                break;
            case R.id.btn_shareCollection:
                ShareUtil.showShare(EnlargeCollectionActivity.this,imageUrl);
                break;
        }
    }


    @Override
    public void tipsCollectionDownloadSuccess(File file, String fileName) {
        createNotification("下载完成");
        //将图片插入本地相册
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), fileName, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
        isDownload = true;//该图片已经下载
    }

    @Override
    public void tipsCollectionDownloadFailure() {
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
    public void tipsDeleteCollectionSuccess() {
        Toast.makeText(this, "已经取消收藏", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        setResult(1, intent);
        finish();
    }


}
