package com.example.justforfun.dribbble.View.shot_detail;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justforfun.dribbble.Model.Shot;
import com.example.justforfun.dribbble.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;

public class ShotAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_SHOT_IMAGE = 0;
    private static final int VIEW_TYPE_SHOT_INFO = 1;

    private final Shot shot;
    private LayoutInflater inflater;

    public ShotAdapter(Context context, Shot shot){
        this.shot = shot;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case VIEW_TYPE_SHOT_IMAGE:
                view = inflater.inflate(R.layout.shot_item_image,parent,false);
                return new ImageViewHolder(view);
            case VIEW_TYPE_SHOT_INFO:
                view = inflater.inflate(R.layout.shot_item_info,parent,false);
                return new InfoViewHolder(view);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType){
            case VIEW_TYPE_SHOT_IMAGE:
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(shot.getImageUrl()))
                        .setAutoPlayAnimations(true)
                        .build();
                ((ImageViewHolder) holder).image.setController(controller);
                break;
            case VIEW_TYPE_SHOT_INFO:
                InfoViewHolder shotDetailViewHolder = (InfoViewHolder) holder;
                shotDetailViewHolder.title.setText(shot.title);
                shotDetailViewHolder.authorName.setText(shot.user.name);
                shotDetailViewHolder.description.setText(shot.description);
                //shotDetailViewHolder.authorPicture.setImageURI(Uri.parse(shot.user.avatar_url));

                shotDetailViewHolder.likeCount.setText(String.valueOf(shot.likes_count));
                shotDetailViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));
                shotDetailViewHolder.viewCount.setText(String.valueOf(shot.views_count));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_SHOT_IMAGE;
        } else {
            return VIEW_TYPE_SHOT_INFO;
        }
    }
}
