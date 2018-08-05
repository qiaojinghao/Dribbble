package com.example.justforfun.dribbble.View.shot_detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justforfun.dribbble.Model.Shot;
import com.example.justforfun.dribbble.R;
import com.example.justforfun.dribbble.Utils.ModelUtils;
import com.google.gson.reflect.TypeToken;

import java.security.Key;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class ShotFragment extends Fragment {

    public static final String KEY_SHOT = "shot";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static ShotFragment newInstance(Bundle args){
        ShotFragment shotFragment = new ShotFragment();
        shotFragment.setArguments(args);
        return shotFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Shot shot = ModelUtils.toObject(getArguments().getString(KEY_SHOT), new TypeToken<Shot>(){});

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ShotAdapter(getContext(), shot));
    }
}
