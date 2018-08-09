package com.example.justforfun.dribbble.View.shot_list;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.justforfun.dribbble.Model.Shot;
import com.example.justforfun.dribbble.Model.User;
import com.example.justforfun.dribbble.R;
import com.example.justforfun.dribbble.View.base.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotListFragment extends Fragment{
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swip_refresh_container) SwipeRefreshLayout swipeRefreshLayout;

    ShotListAdapter adapter;
    private static final int COUNT_PER_PAGE = 6;
    private static final int COUNT_FINAL_PAGE = 3;

    public static ShotListFragment newInstance() {
        return new ShotListFragment();
        /*ShotListFragment fragment = new ShotListFragment();
        fragment.setArguments(args);
        return fragment;*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.spacing_medium)));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadTask(true).execute();
            }
        });

        adapter = new ShotListAdapter(fakeData(1), getContext(), new ShotListAdapter.OnloadMoreListener() {
            @Override
            public void loadMore() {
                new loadTask(false).execute();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    class loadTask extends AsyncTask<Void,Void,Void>{
        boolean refresh;

        public loadTask(boolean refresh){
            this.refresh = refresh;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(refresh){
                List<Shot> data = fakeData(1);
                adapter.setData(data);
                swipeRefreshLayout.setRefreshing(false);
                adapter.setShowloading(true);
            }
            else {
                List<Shot> moreData = fakeData(adapter.getDateCount()/COUNT_PER_PAGE + 1);
                adapter.append(moreData);

                if (moreData.size() < COUNT_PER_PAGE) {
                    adapter.setShowloading(false);
                }
            }
        }
    }

    // Because Dribbble API v1 is not available now, and most requests in API v2 need to
    // get approval of Dribbble. So I made some fake date to demonstrate this app.
    private List<Shot> fakeData(int page) {
        int count = page<3? COUNT_PER_PAGE:COUNT_FINAL_PAGE;

        List<Shot> shotList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            int index = (page-1)*COUNT_PER_PAGE+i;
            Shot shot = new Shot();
            shot.title = titles[index];
            shot.views_count = viewCount[index];
            shot.likes_count = likesCount[index];
            shot.buckets_count = bucketsCount[index];
            shot.description = "Jinghao Qiao is awesome";

            shot.images = new HashMap<>();
            shot.images.put(Shot.IMAGE_HIDPI, imageUrls[index]);

            shot.user = new User();
            shot.user.name = "Jinghao Qiao";

            shotList.add(shot);
        }
        return shotList;
    }


    private static final String[] titles = {
           "USAA | The Design Genome Project","Xavier","BGD Co.","Forbes Japen","Payment and charts interations","Night city 3d illustration","Monitoring Landing page","Nutrex Rebrand – Color Palette Exploration",
            "Death Valley National Park","Cape Paradise Resort \u0026 Spa","Tubik Website Home Page","Vibecast","Animated Digital Car Key Interface Design","Mr.McDee","Share Plans \u0026 Schedules"
    };

    private static int[] viewCount = {4811, 3518, 1531, 2021, 2939, 3609, 3534, 1573, 886, 879, 2422, 1693, 2712, 1234, 2699};

    private static int[] likesCount = {470,352,281,247,237,213,216,187,166,163,181,166,163,153,147};

    private static int[] bucketsCount = {12,23,7,4,11,3,17,7,2,9,23,4,8,8,3};

    private static final String[] imageUrls = {
            "https://cdn.dribbble.com/users/77628/screenshots/4902420/usaa-genome_final_dribbble_b_1x.jpg",
            "https://cdn.dribbble.com/users/581199/screenshots/4904405/xavier-designer-profiles-part-4.png",
            "https://cdn.dribbble.com/users/271258/screenshots/4905495/bgdco.jpg",
            "https://cdn.dribbble.com/users/59947/screenshots/4904982/muti_dribbble-800x600.jpg",
            "https://cdn.dribbble.com/users/149817/screenshots/4905058/shot4.gif",
            "https://cdn.dribbble.com/users/32512/screenshots/4893343/night_city_vehicle_ev_sfm.gif",
            "https://cdn.dribbble.com/users/702789/screenshots/4904129/soft.gif",
            "https://cdn.dribbble.com/users/172256/screenshots/4903730/nutrexpalette.png",
            "https://cdn.dribbble.com/users/33827/screenshots/4905544/deathvalley.jpg",
            "https://cdn.dribbble.com/users/2738/screenshots/4905888/capeparadise.png",
            "https://cdn.dribbble.com/users/418188/screenshots/4905037/website_interactions_ui_design_tubik.gif",
            "https://cdn.dribbble.com/users/283883/screenshots/4905171/vibecast-dribbble.jpg",
            "https://cdn.dribbble.com/users/1468665/screenshots/4904010/main_800x600_1.gif",
            "https://cdn.dribbble.com/users/73294/screenshots/4902508/scroogemcduck4.png",
            "https://cdn.dribbble.com/users/2546/screenshots/4901652/share-plan-xx.gif"
    };

}
