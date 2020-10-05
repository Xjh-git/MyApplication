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
import com.example.naruto.Table.Ninjia;

import java.util.List;

/**
 * Created by xjh on 2018/8/24.
 */

public class NinjiaAdapter extends BaseAdapter {

    Context context;
    List<Ninjia> list;
    ViewHolder holedr;
    LayoutInflater inflater;

    public NinjiaAdapter(Context context, List<Ninjia> list) {
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
            convertView = inflater.inflate(R.layout.ninjia_list, parent, false);

            holedr = new ViewHolder();
            holedr.icon = (ImageView) convertView.findViewById(R.id.ninjia_icon);
            holedr.name = (TextView) convertView.findViewById(R.id.ninjia_name);
            holedr.fragment = (ProgressBar) convertView.findViewById(R.id.ninjia_fragment_progressBar);
            convertView.setTag(holedr);

        } else {
            holedr = (ViewHolder) convertView.getTag();
        }

        final Ninjia ninjia = list.get(position);

        Glide.with(context)
                .load(Uri.parse(ninjia.getIcon()))
                .into(holedr.icon);
        holedr.name.setText(ninjia.getName());


        holedr.fragment.setMax(ninjia.getMax_fragments());
        holedr.fragment.setProgress(ninjia.getFragments());


        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        ProgressBar fragment;
    }
}
