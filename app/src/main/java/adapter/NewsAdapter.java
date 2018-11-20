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
import com.example.abc.kantu.NewsActivity;
import com.example.abc.kantu.R;

import java.util.List;

import tuijian.model.NewList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewList> list;

    private Context context;


    public NewsAdapter(Context context, List<NewList> list)
    {
        this.list=list;
        this.context=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        View newsView;
        ImageView newsImage;
        TextView newsSource;
        TextView newsTitle;

        public ViewHolder(View view)
        {
            super(view);
            newsView=view;
            newsImage=(ImageView)view.findViewById(R.id.iv_newsImage);
            newsTitle=(TextView)view.findViewById(R.id.tv_newsTitle);
            newsSource=(TextView)view.findViewById(R.id.yv_newsSource);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_news_item,parent,false);

        final ViewHolder viewHolder=new ViewHolder(view);

        viewHolder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewList newList=list.get(viewHolder.getAdapterPosition());
                String link=newList.returnLink();

                Intent intent=new Intent(context,NewsActivity.class);
                intent.putExtra("link",link);
                context.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewList newList=list.get(position);
        holder.newsTitle.setText(newList.returnTitle());
        holder.newsSource.setText(newList.returnSource());

        Glide.with(context).load(newList.returnimageUrl()).into(holder.newsImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
