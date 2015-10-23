package com.lnyp.imgbrowse;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class MyViewPagerAdapter extends PagerAdapter {

    List<String> imgs;

    Context mContext;

    public MyViewPagerAdapter(Context context, List<String> imgs) {

        this.mContext = context;
        this.imgs = imgs;

    }

    @Override
    public int getCount() { // 获得size
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        String imgUrl = imgs.get(position);
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.img_browse, null);
        PhotoView img = (PhotoView) view.findViewById(R.id.img_plan);
        img.setTag(imgUrl);
        ImageLoaderUtil.getInstance().displayListItemImage(imgs.get(position), img);

        ((ViewPager) container).addView(view);

        return view;

    }
}