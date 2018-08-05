package com.example.justforfun.dribbble.View.shot_detail;

import android.app.Fragment;

import com.example.justforfun.dribbble.View.base.SingleFragmentActivity;

public class ShotActivity extends SingleFragmentActivity {

    public static final String KEY_SHOT_TITLE = "shot_title";

    @Override
    protected Fragment newFragment() {
        return ShotFragment.newInstance(getIntent().getExtras());
    }

    @Override
    protected String getActivityTitle() {
        return getIntent().getStringExtra(KEY_SHOT_TITLE);
    }
}
