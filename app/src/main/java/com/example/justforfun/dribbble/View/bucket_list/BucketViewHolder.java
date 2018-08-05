package com.example.justforfun.dribbble.View.bucket_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.justforfun.dribbble.R;
import com.example.justforfun.dribbble.View.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BucketViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.bucket_layout) public View bucketLayout;
    @BindView(R.id.bucket_name) public TextView bucketName;
    @BindView(R.id.bucket_count) public TextView bucketCount;
    @BindView(R.id.bucket_chosen) public CheckBox bucketChosen;

    public BucketViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
