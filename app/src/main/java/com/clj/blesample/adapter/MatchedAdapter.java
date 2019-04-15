package com.clj.blesample.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clj.blesample.R;
import com.clj.blesample.comm.Utils;

import java.util.ArrayList;
import java.util.List;

public class MatchedAdapter extends BaseAdapter {
    private Context context;
    private List<String> lists;

    public void addItem(String item) {
        lists.add(item);
    }

    public MatchedAdapter(Context context) {
        this.context = context;
        lists = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        if (i > lists.size())
            return null;
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = View.inflate(context, R.layout.adapter_matched, null);
            holder = new MatchedAdapter.ViewHolder();
            view.setTag(holder);
            holder.tvMatched = (TextView) view.findViewById(R.id.txt_matched);
        }
        final Object bleDevice = getItem(i);
        if (bleDevice != null) {
            holder.tvMatched.setText("hello world");
        }
        holder.tvMatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.Toast(context, "hello world");
            }
        });
        return view;
    }

    private static class ViewHolder {
        TextView tvMatched;
    }
}
