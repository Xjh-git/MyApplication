package com.example.recipes.Rank;

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

public class proAdapter extends RecyclerView.Adapter<proAdapter.ViewHolder> {
    private List<Recipes> proList;
    private OnItemClickListener mListener;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productionImage;
        ImageView workerImage;
        TextView workerName;
        TextView productionGrade;
        TextView productionName;
        int position;

        public ViewHolder(View view) {
            super(view);
            productionImage = (ImageView) view.findViewById(R.id.ProductionImage);
            workerImage = (ImageView) view.findViewById(R.id.Worker_Photo);
            workerName = (TextView) view.findViewById(R.id.Worker_Name);
            productionGrade = (TextView) view.findViewById(R.id.ProductionGrade);
            productionName = (TextView) view.findViewById(R.id.ProductionName);

            EventInit(view);
        }

        private void EventInit(View view) {
            /**
             * 点击响应事件
             */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnCLick(position, v);
                }
            });
        }


        public void setDate(int date) {
            this.position = date;
        }
    }

    public proAdapter(Context context, List<Recipes> productionList) {
        proList = productionList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_lay, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipes pro = proList.get(position);

        UserDAO userDAO=new UserDAO(context);
        User user=userDAO.find(pro.getWriter());

        Glide.with(context)
                .load(Uri.parse(pro.getIcon_addr()))
                .into(holder.productionImage);
        Glide.with(context)
                .load(Uri.parse(user.getPhoto_uri()))
                .into(holder.workerImage);


        holder.productionGrade.setText(pro.getScore()+"");
        holder.productionName.setText(pro.getName());
        holder.workerName.setText(user.getName());
//        holder.productionImage.setImageResource(pro.getProductionImage());
//        holder.workerImage.setImageResource(pro.getWorkerImage());
        /**
         * 传递位置position参数于ViewHolder
         */
        holder.setDate(position);
    }

    @Override
    public int getItemCount() {
        return proList.size();
    }

    /**
     * 设置接口暴露方法
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    /**
     * 创建一个事件相应接口
     */
    public interface OnItemClickListener {
        void OnCLick(int postion, View v);
    }

}