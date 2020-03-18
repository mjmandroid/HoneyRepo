package com.streaming.better.honey.wedget.rollviewpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beautystudiocn.allsale.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by suxi on 2017/6/16.
 */

public class AdvsPagerAdapter extends LoopPagerAdapter {

    private String[] imgs;
    private LruCache<String, Bitmap> mLruCaches;
    private int[] ids;
    private Context mContext;
    private String[] shopIds;
    private int[] goodIds;
    private int[] type;
    private String[] activityIds;


    public AdvsPagerAdapter(RollPagerView viewPager, String[] pics, LruCache<String, Bitmap> mLruCaches, int[] ids, Context context) {
        super(viewPager);
        this.mLruCaches = mLruCaches;
    }

    public AdvsPagerAdapter(RollPagerView viewPager, String[] imgs, LruCache<String, Bitmap> mLruCaches, int[] ids, Context context, String[] shopIds,
                            int[] goodIds, int[] ints, String[] activityIds) {
        super(viewPager);
        this.imgs = imgs;
        this.mLruCaches = mLruCaches;
        this.ids = ids;
        this.mContext = context;
        this.shopIds = shopIds;
        this.goodIds = goodIds;
        this.type = ints;
        this.activityIds = activityIds;
    }
    public void setArgs(String[] imgs, int[] ids, String[] shopIds,
                        int[] goodIds, int[] ints, String[] activityIds){
        this.imgs = imgs;
        this.ids = ids;
        this.shopIds = shopIds;
        this.goodIds = goodIds;
        this.type = ints;
        this.activityIds = activityIds;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        Bitmap bitmap = getBitmapFromCache(imgs[position]);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setAdjustViewBounds(true);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);  //如果缓存中有图片，就直接放上去行了
        } else {
            //缓存中没有，只要去异步下载了
//            view.setImageResource(R.drawable.place_holder);
            AddbBitmapToView addbBitmapToView = new AddbBitmapToView(view);
            addbBitmapToView.execute(imgs[position]);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type[position]) {
//                    case 2:
//                        Intent intent = new Intent(mContext, TopicActivity.class);
//                        intent.putExtra("id", ids[position]);
//                        mContext.startActivity(intent);
//                        break;
//                    case 3:
//                        Intent intent1 = new Intent(mContext, ShopDetailActivity.class);
//                        intent1.putExtra("shopId", shopIds[position]);
//                        mContext.startActivity(intent1);
//                        break;
//                    case 4:
//                        Intent intent2 = new Intent(mContext, GoodDetailActivity.class);
//                        intent2.putExtra("productId", goodIds[position]);
//                        mContext.startActivity(intent2);
//                        break;
//                    case 1:
//                        if (TextUtils.isEmpty(SpConfig.getInstance().getString(Constants.SESSIONID_STRING))) {
//                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
//                            return;
//                        }
//                        ActivityDialogUtils.getActivityData(mContext);
//                        break;
                }
            }
        });
        return view;
    }


    @Override
    public int getRealCount() {
        return imgs.length;
    }

    //从缓存中获取图片
    private Bitmap getBitmapFromCache(String url) {
        return mLruCaches.get(url);
    }

    //这里使用Asyn异步的方式加载网络图片
    class AddbBitmapToView extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public AddbBitmapToView(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = getPicture(params[0]);
            if (bitmap != null) {
                addBitmapToCache(params[0], bitmap);
            }
            return bitmap;
        }
    }

    //网络地址获取图片
    private Bitmap getPicture(String path) {
        Bitmap bm = null;
        InputStream is;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.connect();
            is = connection.getInputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一
            bm = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }


    //将图片增加到缓存中
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {   //如果没有缓存，则增加到缓存中
            mLruCaches.put(url, bitmap);
        }
    }
}
