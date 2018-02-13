package com.netease.pineapple.module.video.detail;

import android.os.Bundle;

import com.guiying.module.common.base.BaseActionBarActivity;

/**
 * Created by zhaonan on 2018/2/13.
 */

public class VideoDetailActivity extends BaseActionBarActivity {

    @Override
    protected int setTitleId() {
        return R.string.module_video_detail_app_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_video_detail_activity_video_detail_layout);
    }
}
