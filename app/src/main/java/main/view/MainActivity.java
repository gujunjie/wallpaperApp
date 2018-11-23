package main.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.HostFragment;
import com.example.abc.kantu.MyViewPager;
import com.example.abc.kantu.NewsFragment;
import com.example.abc.kantu.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.barlibrary.ImmersionBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.MyFragmentPageAdapter;
import base.BaseActivity;
import biyi.view.BiyiActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import collection.view.CollectionActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import duanzi.view.DuanZiFragment;
import io.reactivex.disposables.CompositeDisposable;
import login.view.LoginActivity;
import main.presenter.Presenter;
import video.view.VideoFragment;

public class MainActivity extends BaseActivity<IView, Presenter>
        implements NavigationView.OnNavigationItemSelectedListener, IView {


    @BindView(R.id.vp_viewPager)
    MyViewPager vpViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.tl_tabLayout)
    CommonTabLayout tlTabLayout;


    private TabLayout.Tab tab;

    private List<Fragment> list;

    private CircleImageView circleImageView;
    private TextView tv_showUserName;

    private Presenter presenter;

    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.colorAccent).init();

        initUI();


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        } else {
            initFragment();

        }
        handleIcon();
        autoLogin();
    }

    public void autoLogin() {
        presenter.autoLogin(this);
    }


    public void handleIcon() {
        View view = navView.getHeaderView(0);
        tv_showUserName = (TextView) view.findViewById(R.id.tv_showUserName);
        circleImageView = (CircleImageView) view.findViewById(R.id.civ_icon);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
                if (sp.getBoolean("isLogin", false)) {
                    final String[] items = {"相册", "拍照"};
                    AlertDialog.Builder listDialog =
                            new AlertDialog.Builder(MainActivity.this);
                    listDialog.setTitle("更换头像");
                    listDialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                                    intent.setType("image/*");
                                    startActivityForResult(intent, 3);
                                    break;
                                case 1:
                                    presenter.getImageUri(MainActivity.this);
                                    break;
                            }
                        }
                    });
                    listDialog.show();
                } else {
                    Toast.makeText(MainActivity.this, "请先登录账号", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 4);
                }
            }

        });
    }

    public void initUI() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navView.setNavigationItemSelectedListener(this);
    }


    public void initFragment() {
        list = new ArrayList<>();
        list.add(new HostFragment());
        list.add(new VideoFragment());
        list.add(new NewsFragment());
        list.add(new DuanZiFragment());

        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager(), list);
        vpViewPager.setAdapter(adapter);

        ArrayList<CustomTabEntity> customTabEntityArrayList=new ArrayList<>();

        final String title[] ={"主页","视屏","新闻","段子"};

        final int iconSelect[] ={R.drawable.host_pressed,R.drawable.video_pressed,R.drawable.news_pressed,R.drawable.duanzi_pressed};

        final int iconUnSelect[]={R.drawable.host,R.drawable.video,R.drawable.news,R.drawable.duanzi};

        for(int i=0;i<4;i++)
        {
            final int j=i;
            customTabEntityArrayList.add(new CustomTabEntity() {


                @Override
                public String getTabTitle() {
                    return title[j];
                }

                @Override
                public int getTabSelectedIcon() {
                    return iconSelect[j];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return iconUnSelect[j];
                }
            });
        }




        tlTabLayout.setTabData(customTabEntityArrayList);

        tlTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.biyin_wallpaper) {
            Intent intent = new Intent(MainActivity.this, BiyiActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.user) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        } else if (id == R.id.cancel) {
            SharedPreferences sp = getSharedPreferences("loginData", MODE_PRIVATE);
            if (sp.getBoolean("isLogin", false)) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", false);
                editor.putString("loginAccount", "");
                editor.apply();
                Glide.with(MainActivity.this).load(R.drawable.logo).into(circleImageView);
                tv_showUserName.setText("每日一图");
                Toast.makeText(MainActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "您尚未登陆", Toast.LENGTH_SHORT).show();
            }


        } else if (id == R.id.collection) {
            Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
            startActivity(intent);

        } else if (id == R.id.quit) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }

        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.dispose();
    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 && requestCode == 1) {
            String name = data.getStringExtra("username");
            tv_showUserName.setText(name);
            presenter.getUserIcon(name);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                circleImageView.setImageBitmap(bitmap);
                presenter.saveUserIcon(MainActivity.this, bitmap);//把拍照的头像存入数据库
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            presenter.getImagePath(MainActivity.this, data);
        }
        if (requestCode == 4 && resultCode == 1) {
            String name = data.getStringExtra("username");
            tv_showUserName.setText(name);
            presenter.getUserIcon(name);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initFragment();

                } else {

                    Toast.makeText(MainActivity.this, "你没有授予权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Override
    public void showUserIcon(Bitmap bitmap) {
        circleImageView.setImageBitmap(bitmap);
    }

    @Override
    public void showUserIcon(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            circleImageView.setImageBitmap(bitmap);
            presenter.saveUserIcon(MainActivity.this, bitmap);

        } else {
            Toast.makeText(MainActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public void saveIconSuccess() {
        Toast.makeText(MainActivity.this, "保存头像成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void autoLoginShowData(String name, Bitmap bitmap) {
        circleImageView.setImageBitmap(bitmap);
        tv_showUserName.setText(name);
    }

    @Override
    public void showUserName(String name) {
        tv_showUserName.setText(name);
    }
}
