package com.example.recipes.works;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipes.R;

import java.util.List;

/**
 * Created by xjh on 2018/7/16.
 */

public class WorkAdapter extends BaseAdapter {
    List<work> list;
    private LayoutInflater mInflater;
    ViewHolder holder;
    Context context;

    public WorkAdapter(Context context, List<work> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.myworks_list, parent, false); //加载布局
            holder = new ViewHolder();

            holder.works_pic_iv=(ImageView)convertView.findViewById(R.id.works_pic_iv);
            holder.tagname_tv=(TextView)convertView.findViewById(R.id.tagname_tv);

            //            holder.recipe_writer = (TextView) convertView.findViewById(R.id.recipe_writer);
            //            holder.recipe_name = (TextView) convertView.findViewById(R.id.recipe_name);
            //            holder.recipe_score = (TextView) convertView.findViewById(R.id.recipe_score);
            //            holder.rectangleCircleImageView = (ImageView) convertView.findViewById(R.id.rectangleCircleImageView);
            convertView.setTag(holder);

        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        work work = list.get(position);

        holder.tagname_tv.setText(work.getTags());
        Glide.with(context)
                .load(Uri.parse(work.getImg()))
                .into(holder.works_pic_iv);

        //        holder.recipe_name.setText(recipes.getName());
        //        holder.recipe_writer.setText(recipes.getWriter());
        //        holder.recipe_score.setText(String.valueOf(recipes.getScore()));
        //        Glide.with(context)
        //                .load(Uri.parse(recipes.getIcon_addr()))
        //                .into(holder.rectangleCircleImageView);
        //        holder.rectangleCircleImageView.setImageURI(Uri.parse(recipes.getIcon_addr()));

        return convertView;
    }

    private class ViewHolder {
        ImageView works_pic_iv;
        TextView tagname_tv;
    }
}

