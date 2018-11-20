package enlarge.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.MyEnlargeViewPager;
import com.example.abc.kantu.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.gyf.barlibrary.ImmersionBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import adapter.MyEnlargePageAdapter;
import base.BaseActivity;
import beauty.model.BaiduImage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enlarge.presenter.Presenter;
import util.ShareUtil;

public class EnlargeActivity extends BaseActivity<IView, Presenter> implements IView {


    @BindView(R.id.tv_showSize)
    TextView tvShowSize;
    @BindView(R.id.btn_collect)
    Button btnCollect;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.btn_asWallpaper)
    Button btnAsWallpaper;
    @BindView(R.id.vp_enlarge)
    MyEnlargeViewPager vpEnlarge;
    @BindView(R.id.tv_position)
    TextView tvPosition;


    private String imageUrl;//图片链接

    private int imageWidth;//图片宽度

    private int imageHeight;//图片高度

    private Presenter presenter;

    private boolean isDownload = false;//该图片是否下载

    private List<BaiduImage.ImgsBean> list;//获取的图片集合

    private List<View> viewList;//viewpager所需要的view集合

    public int position;//当前图片下标

    public static final String CHANNEL_ID = "channel_id_1";
    public static final String CHANNEL_NAME = "channel_name_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);
        ButterKnife.bind(this);

        ImmersionBar.with(this).init();

        initUI();

    }

    public void initUI() {
        Intent intent = getIntent();

        list = intent.getParcelableArrayListExtra("imageList");
        position = intent.getIntExtra("position", 0);
        imageWidth = list.get(position).getImageWidth();
        imageHeight = list.get(position).getImageHeight();
        tvShowSize.setText(imageWidth + " X " + imageHeight);
        imageUrl = list.get(position).getImageUrl();

        tvPosition.setText("(" +(position+1)+" / "+"60)");

        int size = list.size();

        viewList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            View view = getLayoutInflater().from(this).inflate(R.layout.view_layout, null);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);
            Glide.with(EnlargeActivity.this)
                    .load(list.get(i).getImageUrl())
                    .crossFade(500)
                    .into(photoView);
            viewList.add(view);
        }


        MyEnlargePageAdapter adapter = new MyEnlargePageAdapter(viewList);

        vpEnlarge.setAdapter(adapter);
        vpEnlarge.setCurrentItem(position);
        vpEnlarge.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imageUrl = list.get(position).getImageUrl();
                imageWidth = list.get(position).getImageWidth();
                imageHeight = list.get(position).getImageHeight();
                tvShowSize.setText(imageWidth + " X " + imageHeight);
                tvPosition.setText("(" +(position+1)+" / "+"60)");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    @OnClick({R.id.btn_collect, R.id.btn_download, R.id.btn_asWallpaper, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_collect:
                SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
                if (sp.getBoolean("isLogin", false)) {
                    presenter.saveCollection(imageUrl, this, imageWidth, imageHeight);
                } else {
                    Toast.makeText(this, "请先登录您的账号", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_download:
                if (!isDownload) {
                    presenter.download(EnlargeActivity.this, imageUrl);
                    Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnlargeActivity.this, "该图片已经下载", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_asWallpaper:


                if (isDownload) {
                    setWallpaper();
                    //图片已经下载就设置壁纸
                } else {
                    presenter.download(EnlargeActivity.this, imageUrl);
                    setWallpaper();//没有下载就先下载再设置壁纸

                }

                break;
            case R.id.btn_share:
                ShareUtil.showShare(EnlargeActivity.this, imageUrl);
                break;
        }
    }

    @Override
    public void tipsCollectionSuccess() {
        Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void tipsDownloadSuccess(File file, String fileName) {
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
    public void tipsDownloadFailure() {

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


}
