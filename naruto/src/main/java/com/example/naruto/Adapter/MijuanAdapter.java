package com.example.naruto.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.naruto.R;
import com.example.naruto.Table.Mijuan;

import java.util.List;

/**
 * Created by xjh on 2018/8/24.
 */

public class MijuanAdapter extends BaseAdapter {

    Context context;
    List<Mijuan> list;
    ViewHolder holedr;
    LayoutInflater inflater;

    Mijuan mijuan;

    public MijuanAdapter(Context context, List<Mijuan> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        holedr = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mijuan_list, parent, false);

            holedr = new ViewHolder();
            holedr.icon = (ImageView) convertView.findViewById(R.id.mijuan_icon);
            holedr.name = (TextView) convertView.findViewById(R.id.mijuan_name);
            holedr.fragment = (ProgressBar) convertView.findViewById(R.id.mijuan_fragment_progressBar);
            convertView.setTag(holedr);

        } else {
            holedr = (ViewHolder) convertView.getTag();
        }

        mijuan = list.get(position);

        Glide.with(context)
                .load(Uri.parse(mijuan.getIcon()))
                .into(holedr.icon);
        holedr.name.setText(mijuan.getName());


        holedr.fragment.setMax(mijuan.getMax_fragment());
        holedr.fragment.setProgress(mijuan.getFragment());


        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        ProgressBar fragment;
    }
}
