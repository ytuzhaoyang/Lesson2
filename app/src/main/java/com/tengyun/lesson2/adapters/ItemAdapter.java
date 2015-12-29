package com.tengyun.lesson2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tengyun.lesson2.CircleTransFormation;
import com.tengyun.lesson2.Item;
import com.tengyun.lesson2.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Item>  items;

    public ItemAdapter(Context context) {
        this.context = context;
        items= new ArrayList<Item>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(Collection<? extends Item> collection){
        items.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        Item item = items.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.content.setText(item.getContent());
        if (item.getUserName()!= null){
            viewHolder.userName.setText(item.getUserName());
            Picasso.with(context).load(getIconURL(item.getUserId(), item.getUserIcon()))
                    .transform(new CircleTransFormation())
                    .into(viewHolder.userIcon);
        }else {
            viewHolder.userName.setText("无名");
            viewHolder.userIcon.setImageResource(R.mipmap.ic_launcher);
        }

        if (item.getImage() == null){
            viewHolder.image.setVisibility(View.GONE);
        }else {
            viewHolder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(getImageUrl(item.getImage()))
                    .resize(parent.getWidth(), 0)             //设置尺寸，不可以小于0，不可以同时为0；若一个不为0，另一个为0，则0不生效，按宽高比
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(viewHolder.image);
        }
        return convertView;
    }

    public static String getIconURL(long id, String icon){
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url, id / 10000, id, icon);
    }


    public static String getImageUrl(String image){
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        return String.format(url, matcher.group(1), matcher.group(), "small", image);
        //TODO:检测网络是WIFI还是移动网络
    }


    private static class ViewHolder {
        ImageView userIcon;
        ImageView image;
        TextView userName;
        TextView content;

        public ViewHolder(View itemView) {
            userIcon = (ImageView) itemView.findViewById(R.id.user_icon);
            image = (ImageView) itemView.findViewById(R.id.image);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
