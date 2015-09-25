package com.lnyp.imgbrowse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements KechengAdapter.OnImgClickListener {

    private String BASE_URL = "http://www.imooc.com/api/teacher?type=4&num=10";

    @ViewInject(R.id.main_layout)
    private RelativeLayout main_layout;

    @ViewInject(R.id.list_kecheng)
    private ListView listKecheng;

    private List<KeCheng> cks;

    private KechengAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);

        getDataFromServer();
    }

    /**
     * 获取网络数据
     */
    private void getDataFromServer() {

        HttpUtils http = new HttpUtils();

        http.configCurrentHttpCacheExpiry(1000 * 5);
        // 设置超时时间
        http.configTimeout(5 * 1000);
        http.configSoTimeout(5 * 1000);

        http.send(HttpRequest.HttpMethod.GET, BASE_URL, null, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Gson gson = new Gson();
                RspData rspData = gson.fromJson(responseInfo.result, RspData.class);
                cks = rspData.data;

                if (mAdapter == null) {

                    mAdapter = new KechengAdapter(MainActivity.this, cks);
                    listKecheng.setAdapter(mAdapter);

                    mAdapter.setOnImgClickListener(MainActivity.this);

                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    @Override
    public void handleBrowse(int position) {

//        PopupWindow popupWindow = new ImgBrowsePop(this, cks,position);
//        popupWindow.showAtLocation(main_layout, Gravity.CENTER, 0, 0);

        ArrayList<String> imgs = new ArrayList<>();

        for (KeCheng data : cks) {

            imgs.add(data.picBig);
        }

        Intent intent = new Intent(this, ImageBrowseActivity.class);
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("imgs", imgs);
        startActivity(intent);

    }
}
