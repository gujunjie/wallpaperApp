package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.R;
import com.example.abc.kantu.VideoActivity;

import org.w3c.dom.Text;

import java.util.List;

import video.model.VideoBean;

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.ViewHolder> {

    private List<VideoBean.ResultBean> list;

    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder
    {   View VideoView;

        ImageView sharerIcon;
        TextView sharerName;
        TextView videoText;
        ImageView videoImage;
        public ViewHolder(View view)
        {
            super(view);
            VideoView=view;
            videoImage=(ImageView)view.findViewById(R.id.iv_videoImage);
            videoText=(TextView)view.findViewById(R.id.tv_videoText);

            sharerIcon=(ImageView)view.findViewById(R.id.iv_sharerIcon);
            sharerName=(TextView)view.findViewById(R.id.tv_sharerName);


        }
    }

    public MyVideoAdapter(Context context, List<VideoBean.ResultBean> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_video_item,parent,false);
        final ViewHolder viewholder=new ViewHolder(view);



        viewholder.videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  VideoBean.ResultBean resultBean=list.get(viewholder.getAdapterPosition());
                  Intent intent=new Intent(context, VideoActivity.class);
                  intent.putExtra("videoUrl",resultBean.getVideo());
                  intent.putExtra("thumbImageUrl", resultBean.getThumbnail());
                  context.startActivity(intent);

            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoBean.ResultBean resultBean=list.get(position);

        Glide.with(context).load(resultBean.getHeader()).into(holder.sharerIcon);
        holder.sharerName.setText(resultBean.getName());

        holder.videoText.setText(resultBean.getText());
        Glide.with(context).load(resultBean.getThumbnail()).into(holder.videoImage);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
