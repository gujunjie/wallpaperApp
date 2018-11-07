package startup.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.abc.kantu.R;

import java.util.Random;

import base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.view.MainActivity;
import startup.presenter.Presenter;

public class StartupActivity extends BaseActivity<IStartupView, Presenter> implements IStartupView {

    @BindView(R.id.iv_startup)
    ImageView ivStartup;
    @BindView(R.id.btn_jump)
    Button btnJump;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_ziti)
    ImageView ivZiti;
    private Presenter presenter;

    private boolean isPressed = false;

    private Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        ButterKnife.bind(this);

        immersionBar();

        presenter.getImageUrl(this);

        MyCountDownTimer timer = new MyCountDownTimer(6000, 1000);
        timer.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!isPressed) {
                    turnOnMainActivity();
                }
            }
        }).start();//开启线程3秒自动跳转

    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
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
    public void showStartupImage(String imageUrl) {
        Glide.with(this).load(imageUrl).into(new GlideDrawableImageViewTarget(ivStartup) {

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                btnJump.setVisibility(View.VISIBLE);
                Glide.with(StartupActivity.this).load(R.drawable.logo).crossFade(1000).into(ivLogo);
                Glide.with(StartupActivity.this).load(R.drawable.startuplogo).crossFade(1000).into(ivZiti);
            }
        });


    }

    @Override
    public void showGetImageUrlFailure() {
        Toast.makeText(this, "网络不给力啊", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_jump)
    public void onViewClicked() {

        turnOnMainActivity();
        isPressed = true;
    }

    public void turnOnMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }//跳转主界面

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btnJump.setText("快速跳过" + l / 1000);
        }//显示倒计时

        @Override
        public void onFinish() {

        }
    }//倒计时类
}


