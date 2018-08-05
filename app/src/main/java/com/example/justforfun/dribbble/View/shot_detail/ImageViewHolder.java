package com.example.justforfun.dribbble.View.shot_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    SimpleDraweeView image;

    public ImageViewHolder(View itemView) {
        super(itemView);
        image = (SimpleDraweeView) itemView;
    }
}
