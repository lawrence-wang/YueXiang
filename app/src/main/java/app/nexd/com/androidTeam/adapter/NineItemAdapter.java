package app.nexd.com.androidTeam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.nexd.com.androidTeam.R;
import app.nexd.com.androidTeam.mode.MallInfo;
import app.nexd.com.androidTeam.util.OkHttpClientManager;

/**
 * Created by lawrence on 2015/11/12.
 */
public class NineItemAdapter extends BaseAdapter {

    Context context;
    List<MallInfo> mallInfos;

    public NineItemAdapter(Context context) {
        this.context = context;
        mallInfos = new ArrayList<>();
    }

    public void setMallInfos(List<MallInfo> mallInfos) {
        if (mallInfos != null) {
            mallInfos.clear();
        }
        this.mallInfos = mallInfos;
    }

    @Override
    public int getCount() {
        return mallInfos.size();
    }

    @Override
    public MallInfo getItem(int position) {
        return mallInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.nine_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.ItemText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OkHttpClientManager.getDisplayImageDelegate().displayImage(viewHolder.imageView, getItem(position).getPicUrl());
        viewHolder.textView.setText(getItem(position).getName());

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
