package com.lnyp.imgbrowse;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * 配置全局的 Android-Universal-Image-Loader
 */
public class ImageLoaderUtil {
    private static ImageLoaderUtil instance = null;

    private ImageLoader mImageLoader;

    // 列表中默认的图片
    private DisplayImageOptions mListItemOptions;

    private ImageLoaderUtil(Context context) {

        mImageLoader = ImageLoader.getInstance();
        mListItemOptions = new DisplayImageOptions.Builder()
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.load_default_img)
                .showStubImage(R.mipmap.load_default_img)
                        // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.load_default_img)
                        // 加载图片时会在内存、磁盘中加载缓存
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(300)
                .build();

    }

    public static ImageLoaderUtil getInstance() {
        return instance;
    }

    public synchronized static ImageLoaderUtil init(Context context) {
        if (instance == null) {
            instance = new ImageLoaderUtil(context);
        }

        File cacheDir = context.getExternalFilesDir("news/pictures");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheExtraOptions(360, 360)
                .memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))
                .discCache(new UnlimitedDiscCache(cacheDir)).build();

        ImageLoader.getInstance().init(config);

        return instance;
    }

    /**
     * 列表图片
     *
     * @param uri
     * @param imageView
     */
    public void displayListItemImage(String uri, ImageView imageView) {
        String strUri = (isEmpty(uri) ? "" : uri);
        mImageLoader.displayImage(strUri, imageView, mListItemOptions);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private boolean isEmpty(String str) {
        if (str != null && str.trim().length() > 0 && !str.equalsIgnoreCase("null")) {
            return false;
        }
        return true;
    }
}
