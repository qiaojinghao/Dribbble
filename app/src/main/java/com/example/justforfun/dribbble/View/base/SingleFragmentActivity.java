package com.example.justforfun.dribbble.View.base;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.justforfun.dribbble.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if(isBackEnabled()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getActivityTitle());

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, newFragment())
                    .commit();
        }
    }

    protected boolean isBackEnabled() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isBackEnabled() && item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected String getActivityTitle() {
        return "";
    }

    protected abstract Fragment newFragment();
}
