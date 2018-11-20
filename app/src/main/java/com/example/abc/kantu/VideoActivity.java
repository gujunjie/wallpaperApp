package com.example.abc.kantu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ImmersionBar.with(this).init();

        Intent intent=getIntent();
        String videoUrl=intent.getStringExtra("videoUrl");
        String thumbImageUrl=intent.getStringExtra("thumbImageUrl");


        JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
        jzvdStd.setUp(videoUrl, "", Jzvd.SCREEN_WINDOW_FULLSCREEN);
        Glide.with(this).load(thumbImageUrl).into(jzvdStd.thumbImageView);
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
