package com.lnyp.imgbrowse;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.polites.android.GestureImageView;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    List<String> imgs;

    List<View> views;

    Context mContext;

    DisplayImageOptions options;

    public MyViewPagerAdapter(Context context, List<String> imgs) {

        this.mContext = context;
        this.imgs = imgs;

        this.views = new ArrayList<>();

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

        ((ViewPager) container).removeView((GestureImageView) object);  //删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        GestureImageView full_image = new GestureImageView(mContext);
        //full_image
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        full_image.setLayoutParams(params);
        full_image.setScaleType(ImageView.ScaleType.FIT_XY);


        System.out.println("position:"+position+"------->>>>  url "+imgs.get(position));
        ImageLoaderUtil.getInstance().displayListItemImage(imgs.get(position), full_image);

        ((ViewPager) container).addView(full_image);

        return full_image;
    }
}