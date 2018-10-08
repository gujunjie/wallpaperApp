package main.view;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.MyFragmentPageAdapter;
import com.example.abc.kantu.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import beauty.view.BeautyFragment;
import biyi.view.BiyiActivity;
import building.view.BuildingFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import car.view.CarFragment;
import collection.view.CollectionActivity;
import comic.view.ComicFragment;
import creative.view.CreativeFragment;
import de.hdodenhof.circleimageview.CircleImageView;
import food.view.FoodFragment;
import game.view.GameFragment;
import io.reactivex.disposables.CompositeDisposable;
import landscape.view.LandscapeFragment;
import login.view.LoginActivity;
import main.presenter.Presenter;
import movie.view.MovieFragment;
import sports.view.SportsFragment;
import stars.view.StarsFragment;

public class MainActivity extends BaseActivity<IView, Presenter>
        implements NavigationView.OnNavigationItemSelectedListener, IView {

    @BindView(R.id.tl_tabLayout)
    TabLayout tlTabLayout;
    @BindView(R.id.vp_viewPager)
    ViewPager vpViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<Fragment> list;

    private CircleImageView circleImageView;
    private TextView tv_showUserName;

    private Presenter presenter;

    private Uri imageUri;

    private int position=0;//tab的位置

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // immersionBar();

        //setWindowStatusBarColor(this,R.color.colorAccent);


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


    public void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position)
                {
                    case 0:
                        rv=(RecyclerView)findViewById(R.id.rv_landscape);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 1:
                        rv=(RecyclerView)findViewById(R.id.rv_beauty);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 2:
                        rv=(RecyclerView)findViewById(R.id.rv_car);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 3:
                        rv=(RecyclerView)findViewById(R.id.rv_comic);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 4:
                        rv=(RecyclerView)findViewById(R.id.rv_movie);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 5:
                        rv=(RecyclerView)findViewById(R.id.rv_game);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 6:
                        rv=(RecyclerView)findViewById(R.id.rv_stars);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 7:
                        rv=(RecyclerView)findViewById(R.id.rv_food);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 8:
                        rv=(RecyclerView)findViewById(R.id.rv_sports);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 9:
                        rv=(RecyclerView)findViewById(R.id.rv_creative);
                        rv.smoothScrollToPosition(0);
                        break;
                    case 10:
                        rv=(RecyclerView)findViewById(R.id.rv_building);
                        rv.smoothScrollToPosition(0);
                        break;
                }
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navView.setNavigationItemSelectedListener(this);
    }


    public void initFragment() {
        list = new ArrayList<>();
        list.add(new LandscapeFragment());
        list.add(new BeautyFragment());
        list.add(new CarFragment());
        list.add(new ComicFragment());
        list.add(new MovieFragment());
        list.add(new GameFragment());
        list.add(new StarsFragment());
        list.add(new FoodFragment());
        list.add(new SportsFragment());
        list.add(new CreativeFragment());
        list.add(new BuildingFragment());

        MyFragmentPageAdapter adapter = new MyFragmentPageAdapter(getSupportFragmentManager(), list);
        vpViewPager.setAdapter(adapter);
        tlTabLayout.setupWithViewPager(vpViewPager);

        tlTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
            Intent intent=new Intent(MainActivity.this, BiyiActivity.class);
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
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        return true;
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
