package com.netease.pineapple.module.share;

import android.os.Bundle;

import com.guiying.module.common.base.BaseActionBarActivity;

/**
 * Created by zhaonan on 2018/2/13.
 */

public class ShareActivity extends BaseActionBarActivity {

    @Override
    protected int setTitleId() {
        return com.netease.pineapple.module.video.detail.R.string.module_video_detail_app_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.netease.pineapple.module.video.detail.R.layout.module_video_detail_activity_video_detail_layout);
    }
}
