package com.example.justforfun.dribbble.View.shot_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justforfun.dribbble.R;
import com.example.justforfun.dribbble.View.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.shot_clickable_cover) public View cover;
    @BindView(R.id.shot_image) public SimpleDraweeView image;
    @BindView(R.id.shot_view_count) public TextView viewCount;
    @BindView(R.id.shot_like_count) public TextView likeCount;
    @BindView(R.id.shot_bucket_count) public TextView bucketCount;

    public ShotViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
