package com.example.shenhaichen.mobileassistant.ui.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.common.util.DensityUtil;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;

import butterknife.BindView;

/**
 *  将这个activity设置成透明的
 */
public class AppDetailActivity extends BaseActivity {

    @BindView(R.id.app_detail_img)
    ImageView mImageView;

    @Override
    public int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {
         View view = mApplication.getmView();
         Bitmap bitmap = getViewImageCache(view);
         if (bitmap != null){
             mImageView.setImageBitmap(bitmap);
         }
         setItemLocation(view);

    }

    /**
     * 确定这个被点击的Item项在屏幕上的位置
     * @param view
     */
    private void setItemLocation(View view){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(mImageView.getLayoutParams());
        //需要减去状态栏的高度，要不然实际图像会比RecyclerView中的item低
        marginLayoutParams.topMargin = top - DensityUtil.getStatusBarH(this);
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height = view.getHeight();
        //得到位置后
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);
        //设置到imageView中，让这个imageView与原来的item位置保持一致
        mImageView.setLayoutParams(params);
    }

    /**
     *  得到 RecyclerView 中之前被点击的某个item的缓存图像
     * @param view
     * @return
     */
    private Bitmap getViewImageCache(View view){
         view.setDrawingCacheEnabled(true);
         //绘制缓存
         view.buildDrawingCache();
         //得到缓存
         Bitmap bitmap = view.getDrawingCache();
         if (bitmap == null){
             return null;
         }
         bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight());
         //然后再销毁
         view.destroyDrawingCache();
         return bitmap;
    }

}
