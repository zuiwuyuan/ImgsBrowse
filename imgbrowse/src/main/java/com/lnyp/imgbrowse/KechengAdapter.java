package com.lnyp.imgbrowse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KechengAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private List<KeCheng> mDatas;

    private OnImgClickListener onImgClickListener;

    public void setOnImgClickListener(OnImgClickListener onImgClickListener){
        this.onImgClickListener = onImgClickListener;
    }

    public KechengAdapter(Context context, List<KeCheng> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return (mDatas != null ? mDatas.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return (mDatas != null ? mDatas.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item_kecheng, null);

            holder = new ViewHolder();
            holder.picBig = (ImageView) convertView.findViewById(R.id.picBig);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.description = (TextView) convertView.findViewById(R.id.description);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final KeCheng keCheng = mDatas.get(position);

        if (keCheng != null) {

            ImageLoaderUtil.getInstance().displayListItemImage(keCheng.picBig, holder.picBig);

            holder.name.setText(keCheng.name);
            holder.description.setText(keCheng.description);

            holder.picBig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImgClickListener.handleBrowse(position);
                }
            });

        }
        return convertView;
    }

    public interface OnImgClickListener{

        void handleBrowse(int position);
    }

    static class ViewHolder {

        ImageView picBig;

        TextView name;

        TextView description;
    }
}