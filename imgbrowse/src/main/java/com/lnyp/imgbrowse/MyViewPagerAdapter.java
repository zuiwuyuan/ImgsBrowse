package com.lnyp.imgbrowse;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lidroid.xutils.util.LogUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MyViewPagerAdapter extends PagerAdapter {

    List<String> imgs;

    Activity mContext;

    public MyViewPagerAdapter(Activity context, List<String> imgs) {

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

        // 点击图片
        img.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                LogUtils.e("---------------setOnPhotoTapListener--------------------");
            }
        });

        //点击图片外
        img.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                LogUtils.e("---------------setOnViewTapListener--------------------");

                mContext.finish();
            }
        });

        return view;

    }
}