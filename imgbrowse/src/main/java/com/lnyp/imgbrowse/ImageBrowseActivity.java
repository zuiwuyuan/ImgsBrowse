package com.lnyp.imgbrowse;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

public class ImageBrowseActivity extends Activity {

    private ViewPager search_viewpager;

    private List<String> imgs;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_browse);

        this.position = getIntent().getIntExtra("position", 0);

        this.imgs = getIntent().getStringArrayListExtra("imgs");

        search_viewpager = (ViewPager) this.findViewById(R.id.imgs_viewpager);
        search_viewpager.setOffscreenPageLimit(2);

        System.out.println("position:" + position);
        System.out.println("imgs :" + imgs.size());

        PagerAdapter adapter = new MyViewPagerAdapter(this, imgs);
        search_viewpager.setAdapter(adapter);
        search_viewpager.setCurrentItem(position);
    }

    public void onBack(View view) {
        finish();
    }
}
