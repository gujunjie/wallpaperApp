package com.example.abc.kantu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import beauty.model.BaiduImage;
import enlarge.view.EnlargeActivity;
import util.ScreenUtil;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private  List<BaiduImage.ImgsBean> list;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder
    {   View photoView;
        ImageView photoImage;
        TextView photoText;

        public ViewHolder(View view)
        {
            super(view);
            photoView=view;
            photoImage=(ImageView)view.findViewById(R.id.iv_image);
            photoText=(TextView)view.findViewById(R.id.tv_text);

        }
    }

    public PhotoAdapter(List<BaiduImage.ImgsBean> list, Context context)
    {
        list.remove(list.size()-1);
        this.list=list;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaiduImage.ImgsBean photo=list.get(holder.getAdapterPosition());
                String imageUrl=photo.getImageUrl();
                String DowmloadUrl=photo.getDownloadUrl();
                Intent intent=new Intent(context, EnlargeActivity.class);
                intent.putExtra("imageSize",photo.getImageWidth()+" x "+photo.getImageHeight());
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("downloadUrl",DowmloadUrl);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiduImage.ImgsBean photo=list.get(position);
        holder.photoText.setText(photo.getTitle());


        LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)holder.photoImage.getLayoutParams();
        float itemWidth=(ScreenUtil.getScreenWidth(holder.photoView.getContext())-3*4)/2;
        layoutParams.width=(int)itemWidth;
        float scale=(itemWidth+0f)/photo.getImageWidth();
        layoutParams.height=(int)(photo.getImageHeight()*scale);
        holder.photoImage.setLayoutParams(layoutParams);
        Glide.with(context).load(photo.getImageUrl()).override(layoutParams.width,layoutParams.height).into(holder.photoImage);




    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
