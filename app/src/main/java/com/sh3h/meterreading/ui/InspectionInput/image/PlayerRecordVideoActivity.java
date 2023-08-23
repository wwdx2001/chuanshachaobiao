package com.sh3h.meterreading.ui.InspectionInput.image;

import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.sh3h.meterreading.R;

public class PlayerRecordVideoActivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_player_record_video;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setTitle("视频预览");
        videoView = (VideoView) findViewById(R.id.video_view);

        String url = getIntent().getStringExtra("path");
        Uri uri = Uri.parse(url);
        MediaController mediaController = new MediaController(this);
        mediaController.setVisibility(View.VISIBLE);        //隐藏进度条
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();        //自动播放视频
    }
}
