package com.example.recipes.classify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recipes.R;

import java.util.List;

public class imageAdapter extends BaseAdapter {
    Context context;
    List<imageCL> imageID;

    public imageAdapter(Context context,List<imageCL> imageCLList){
        this.context=context;
        imageID=imageCLList;
    }

    @Override
    public int getCount() {
        return imageID.size();
    }

    @Override
    public Object getItem(int position) {
        return imageID.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.classify_item,null);
        imageCL ima=imageID.get(position);
        TextView imageView=(TextView)v.findViewById(R.id.classifyImage);
        imageView.setText(ima.getImage());
        return v;
    }
}
