package com.example.justforfun.dribbble.View.shot_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justforfun.dribbble.Model.Shot;
import com.example.justforfun.dribbble.R;
import com.example.justforfun.dribbble.Utils.ModelUtils;
import com.example.justforfun.dribbble.View.shot_detail.ShotActivity;
import com.example.justforfun.dribbble.View.shot_detail.ShotFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;

public class ShotListAdapter extends RecyclerView.Adapter{
    private static final int VIEW_TYPE_SHOT = 0;
    private static final int VIEW_TYPE_LODAING = 1;

    private List<Shot> data;
    private LayoutInflater inflater;
    private OnloadMoreListener onloadMoreListener;
    private boolean showloading;

    public ShotListAdapter(List<Shot> data, Context context, OnloadMoreListener onloadMoreListener){
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.onloadMoreListener = onloadMoreListener;
        showloading = true;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SHOT) {
            View view = inflater.inflate(R.layout.list_item_shot, parent, false);
            return new ShotViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.list_item_loading, parent, false);
            return new RecyclerView.ViewHolder(view) {};
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == VIEW_TYPE_SHOT) {
            final Shot shot = data.get(position);

            ShotViewHolder shotViewHolder = (ShotViewHolder) holder;
            shotViewHolder.likeCount.setText(String.valueOf(shot.likes_count));
            shotViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));
            shotViewHolder.viewCount.setText(Integer.toString(shot.views_count));

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(shot.getImageUrl())).setAutoPlayAnimations(true).build();
            shotViewHolder.image.setController(controller);

            shotViewHolder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, ShotActivity.class);

                    intent.putExtra(ShotFragment.KEY_SHOT, ModelUtils.toString(shot, new TypeToken<Shot>(){}));
                    intent.putExtra(ShotActivity.KEY_SHOT_TITLE, shot.title);
                    context.startActivity(intent);
                }
            });
        }else{
            onloadMoreListener.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return showloading? data.size()+1:data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position<data.size()? VIEW_TYPE_SHOT:VIEW_TYPE_LODAING;
    }

    public void setShowloading(boolean showloading){
        this.showloading = showloading;
    }

    public void append(@NonNull List<Shot> moreData){
        data.addAll(moreData);
        notifyDataSetChanged();
    }

    public void setData(List<Shot> data){
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    public int getDateCount(){
        return data.size();
    }

    interface OnloadMoreListener{
        void loadMore();
    }
}
