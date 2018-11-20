package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.abc.kantu.R;

import java.util.List;

import duanzi.model.DuanZiBean;

public class DuanZiAdapter extends RecyclerView.Adapter<DuanZiAdapter.ViewHolder>{


    private Context context;

    private List<DuanZiBean.ResultBean> list;


    public DuanZiAdapter(Context context,List<DuanZiBean.ResultBean> list)
    {
        this.context=context;
        this.list=list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View duanziView;
        TextView duanziText;
        TextView comment;
        ImageView icon;
        TextView name;


        public ViewHolder(View view)
        {
            super(view);
            duanziView=view;
            duanziText=(TextView)view.findViewById(R.id.tv_duanziText);
            comment=(TextView)view.findViewById(R.id.tv_comments);
            icon=(ImageView)view.findViewById(R.id.iv_icon);
            name=(TextView)view.findViewById(R.id.tv_name);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_duanzi_item,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DuanZiBean.ResultBean resultBean=list.get(position);

        holder.duanziText.setText(resultBean.getText());
        holder.comment.setText(resultBean.getTop_comments_content());
        holder.name.setText(resultBean.getTop_comments_name());

        Glide.with(context).load(resultBean.getTop_comments_header()).into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}
