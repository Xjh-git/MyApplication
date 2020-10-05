package com.example.recipes.Shouye;

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
import com.example.recipes.Recipes.Recipes;
import com.example.recipes.User.User;
import com.example.recipes.User.UserDAO;

import java.util.List;

/**
 * Created by xjh on 2018/7/16.
 */

public class RecipesAdapter extends BaseAdapter {
    List<Recipes> list;
    private LayoutInflater mInflater;
    ViewHolder holder;
    Context context;

    public RecipesAdapter(Context context, List<Recipes> list) {
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
            convertView = mInflater.inflate(R.layout.recipes_list, parent, false); //加载布局
            holder = new ViewHolder();


            holder.recipe_writer = (TextView) convertView.findViewById(R.id.recipe_writer);
            holder.recipe_name = (TextView) convertView.findViewById(R.id.recipe_name);
            holder.recipe_score = (TextView) convertView.findViewById(R.id.recipe_score);
            holder.rectangleCircleImageView = (ImageView) convertView.findViewById(R.id.rectangleCircleImageView);
            convertView.setTag(holder);

        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }

        Recipes recipes = list.get(position);

        UserDAO userDAO=new UserDAO(context);
        User writer=userDAO.find(recipes.getWriter());

        holder.recipe_name.setText(recipes.getName());
        holder.recipe_writer.setText(writer.getName());
        holder.recipe_score.setText(String.valueOf(recipes.getScore()));
        Glide.with(context)
                .load(Uri.parse(recipes.getIcon_addr()))
                .into(holder.rectangleCircleImageView);
        //        holder.rectangleCircleImageView.setImageURI(Uri.parse(recipes.getIcon_addr()));

        return convertView;
    }


    private class ViewHolder {
        TextView recipe_writer, recipe_name, recipe_score;
        ImageView rectangleCircleImageView;
    }
}

