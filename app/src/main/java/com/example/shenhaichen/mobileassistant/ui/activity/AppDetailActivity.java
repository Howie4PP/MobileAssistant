package com.example.shenhaichen.mobileassistant.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.util.DensityUtil;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.presenter.AppDetailPresenter;
import com.example.shenhaichen.mobileassistant.ui.fragment.AppDetailFragment;

import butterknife.BindView;

/**
 * 将这个activity设置成透明的
 */
public class AppDetailActivity extends BaseActivity<AppDetailPresenter> {

    @BindView(R.id.view_content)
    FrameLayout mFrameLayout;

    private AppInfo mAppInfo;

    @Override
    public int setLayout() {
        return R.layout.activity_app_detail;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

        mAppInfo = (AppInfo) getIntent().getSerializableExtra(Constant.APPINTO);

        View view = mApplication.getmView();
        Bitmap bitmap = getViewImageCache(view);
        if (bitmap != null) {
            mFrameLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
        setItemLocation(view);
        extend();
    }

    /**
     * 确定这个被点击的Item项在屏幕上的位置
     *
     * @param view
     */
    private void setItemLocation(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(mFrameLayout.getLayoutParams());
        //需要减去状态栏的高度，要不然实际图像会比RecyclerView中的item低
        marginLayoutParams.topMargin = top - DensityUtil.getStatusBarH(this);
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height = view.getHeight();
        //得到位置后
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);
        //设置到imageView中，让这个imageView与原来的item位置保持一致
        mFrameLayout.setLayoutParams(params);

    }

    /**
     * frameLayout 的扩展动画
     */
    private void extend() {

        int height = DensityUtil.getScreenH(this);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mFrameLayout, "scaleY",
                1f, (float) height);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFrameLayout.setBackgroundColor(getResources().getColor(R.color.white));
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束之后显示内容页面
                initFragment();
            }
        });

        //动画延迟
        animator.setStartDelay(400);
        animator.setDuration(10000);
        animator.start();
    }

    /**
     * 得到 RecyclerView 中之前被点击的某个item的缓存图像
     *
     * @param view
     * @return
     */
    private Bitmap getViewImageCache(View view) {
        view.setDrawingCacheEnabled(true);
        //绘制缓存
        view.buildDrawingCache();
        //得到缓存
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null) {
            return null;
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        //然后再销毁
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 将fragment加入到frameLayout之中去
     */
    private void initFragment() {
        AppDetailFragment appDetailFragment = new AppDetailFragment(mAppInfo.getId());

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction mTransaction = manager.beginTransaction();

        mTransaction.add(R.id.view_content, appDetailFragment);
        mTransaction.commit();

    }

}
