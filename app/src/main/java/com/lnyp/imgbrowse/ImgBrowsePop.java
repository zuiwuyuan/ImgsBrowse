package com.lnyp.imgbrowse;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.polites.android.GestureImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片浏览弹出框
 *
 */
public class ImgBrowsePop extends PopupWindow {

    private ViewPager search_viewpager;

    private ImageView img_browse_back;

    private List<String> imgs = new ArrayList<String>();

    private List<View> views = new ArrayList<View>();

    private int position;

    private View window;

    public ImgBrowsePop(Activity context, List<KeCheng> datas,int position) {
        super(context);

        this.position = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        window = inflater.inflate(R.layout.img_browse_pop, null);

        img_browse_back = (ImageView) window.findViewById(R.id.img_browse_back);
        search_viewpager = (ViewPager) window.findViewById(R.id.imgs_viewpager);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        for (KeCheng data : datas) {

            GestureImageView iv = new GestureImageView(context);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            ImageLoaderUtil.getInstance().displayListItemImage(data.picBig, iv);

            views.add(iv);
        }

        PagerAdapter adapter = new MyViewPagerAdapter(context, views);
        search_viewpager.setAdapter(adapter);
        search_viewpager.setCurrentItem(position);

        // 设置SelectPicPopupWindow的View
        this.setContentView(window);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        img_browse_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

class MyViewPagerAdapter extends PagerAdapter {

    List<View> views;

    Context mContext;

    public MyViewPagerAdapter(Context context, List<View> views) {

        this.mContext = context;
        this.views = views;
    }

    @Override
    public int getCount() { // 获得size
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(views.get(position));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position), 0);//添加页卡
        return views.get(position);
    }
}