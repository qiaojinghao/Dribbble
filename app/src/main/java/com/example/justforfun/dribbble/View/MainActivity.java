package com.example.justforfun.dribbble.View;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.justforfun.dribbble.Dribbble.auth.Dribbble;
import com.example.justforfun.dribbble.R;
import com.example.justforfun.dribbble.View.bucket_list.BucketListFragment;
import com.example.justforfun.dribbble.View.shot_list.ShotListFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.nio.file.attribute.UserPrincipal;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.drawer) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) android.support.v7.widget.Toolbar toolbar;
    //@BindView(R.id.nav_header_user_pic) SimpleDraweeView userPic;
    //@BindView(R.id.nav_header_logout_btn) TextView log_out_btn;
    //@BindView(R.id.nav_header_user_name) TextView userName;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_layout, ShotListFragment.newInstance())
                    .commit();
        }
    }

    public void setupDrawer(){
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView userName = headerView.findViewById(R.id.nav_header_user_name);
        userName.setText(Dribbble.getCurrentUser().name);

        SimpleDraweeView userPic = headerView.findViewById(R.id.nav_header_user_pic);
        userPic.setImageURI(Dribbble.getCurrentUser().avatar_url);

        TextView logOut = headerView.findViewById(R.id.nav_header_logout_btn);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dribbble.logout(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()){
                    drawerLayout.closeDrawers();
                    return true;
                }
                Fragment fragment = null;
                //Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.drawer_item_home:
                        fragment = ShotListFragment.newInstance();
                        //Toast.makeText(MainActivity.this, "home clicked", Toast.LENGTH_LONG).show();
                        setTitle(R.string.title_home);
                        break;
                    case R.id.drawer_item_likes:
                        fragment = ShotListFragment.newInstance();
                        //Toast.makeText(MainActivity.this, "like clicked", Toast.LENGTH_LONG).show();
                        setTitle(R.string.title_likes);
                        break;
                    case R.id.drawer_item_buckets:
                        fragment = BucketListFragment.newInstance();
                        //Toast.makeText(MainActivity.this, "buckets clicked", Toast.LENGTH_LONG).show();
                        setTitle(R.string.title_buckets);
                        break;
                }

                drawerLayout.closeDrawers();

                if(fragment != null){
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_layout, fragment)
                            .commit();
                    return true;
                }

                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
