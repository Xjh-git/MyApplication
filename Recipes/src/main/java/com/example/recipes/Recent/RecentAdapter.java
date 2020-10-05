package com.example.recipes.Recent;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipes.R;
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.User.User;
import com.example.recipes.User.UserDAO;

import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private List<Recipes> recentProList;
    private OnItemClickListener listener;
    private Context context;

    class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView productionImage;
        ImageView workerImage;
        TextView productionName;
        TextView workerName;
        int position;

        public ViewHolder(View view){
            super(view);
            productionImage=(ImageView)view.findViewById(R.id.RecentProductionImage);
            workerImage=(ImageView)view.findViewById(R.id.RecentWorkerPhoto);
            productionName=(TextView)view.findViewById(R.id.RecentProductionName);
            workerName=(TextView)view.findViewById(R.id.RecentWorkerName);
            EventInit(view);
        }

        private void EventInit(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(position,v);
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    public RecentAdapter(Context context, List<Recipes> recentProList){
        this.recentProList=recentProList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_lay,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipes recentPro=recentProList.get(position);
        UserDAO userDAO=new UserDAO(context);
        User user=userDAO.find(recentPro.getWriter());

        Glide.with(context)
                .load(Uri.parse(recentPro.getIcon_addr()))
                .into(holder.productionImage);
        Glide.with(context)
                .load(Uri.parse(user.getPhoto_uri()))
                .into(holder.workerImage);

//        holder.productionImage.setImageResource(recentPro.getProductionImage());
//        holder.workerImage.setImageResource(recentPro.getWorkerImage());
        holder.productionName.setText(recentPro.getName());
        holder.workerName.setText(user.getName());

        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return recentProList.size();
    }

    public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        listener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(int position, View v);
    }
}
