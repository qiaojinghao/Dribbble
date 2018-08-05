package com.example.justforfun.dribbble.View.bucket_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.justforfun.dribbble.Model.Bucket;
import com.example.justforfun.dribbble.R;

import java.text.MessageFormat;
import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter{
    List<Bucket> data;
    LayoutInflater inflater;

    public BucketListAdapter(List<Bucket> data, Context context){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_bucket,parent,false);
        return new BucketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bucket bucket = data.get(position);

        // 0 -> 0 shot
        // 1 -> 1 shot
        // 2 -> 2 shots
        String buketShotCountString = MessageFormat.format(
                holder.itemView.getContext().getResources().getString(R.string.shot_count),
                bucket.shots_count);

        BucketViewHolder bucketViewHolder = (BucketViewHolder) holder;
        bucketViewHolder.bucketName.setText(bucket.name);
        bucketViewHolder.bucketCount.setText(buketShotCountString);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

