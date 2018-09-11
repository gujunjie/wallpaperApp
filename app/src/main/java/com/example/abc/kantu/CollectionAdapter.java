package com.example.abc.kantu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import beauty.model.BaiduImage;
import enlargecollection.view.EnlargeCollectionActivity;
import io.realm.RealmList;
import register.model.Collection;
import util.ScreenUtil;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private RealmList<Collection> list;
    private Context context;
    private Activity activity;

    public CollectionAdapter(Context context,RealmList<Collection> list,Activity activity)
    {
        this.list=list;
        this.context=context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_collection_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection collection=list.get(holder.getAdapterPosition());
                Intent intent=new Intent(context, EnlargeCollectionActivity.class);
                intent.putExtra("imageUrl",collection.getCollectionImageUrl());
                activity.startActivityForResult(intent,1);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection=list.get(position);

        Glide.with(context).load(collection.getCollectionImageUrl()).into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {


        ImageView photoImage;
        public ViewHolder(View view)
        {
             super(view);
             photoImage=(ImageView)view.findViewById(R.id.iv_collection);

        }
    }
}
