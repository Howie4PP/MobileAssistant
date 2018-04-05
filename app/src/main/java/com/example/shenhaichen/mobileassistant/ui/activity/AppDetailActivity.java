package com.example.shenhaichen.mobileassistant.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;
import com.example.shenhaichen.mobileassistant.common.util.DensityUtil;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;
import com.example.shenhaichen.mobileassistant.presenter.AppDetailPresenter;
import com.example.shenhaichen.mobileassistant.ui.fragment.AppDetailFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import butterknife.BindView;

/**
 * 将这个activity设置成透明的
 */
public class AppDetailActivity extends BaseActivity<AppDetailPresenter> {

    @BindView(R.id.view_content)
    FrameLayout mFrameLayout;
    @BindView(R.id.img_icon)
    ImageView mImgIcon;
    @BindView(R.id.icon_temp)
    View mIcon_temp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.view_coordinator)
    CoordinatorLayout mViewCoordinator;

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

        //设置Icon图标和标题
        ImageLoader.load(Constant.BASE_IMG_URL + mAppInfo.getIcon(), mImgIcon);
        mTxtName.setText(mAppInfo.getDisplayName());

        //设置返回的导航键
        mToolbar.setNavigationIcon(
                new IconicsDrawable(this)
                        .icon(Ionicons.Icon.ion_ios_arrow_back)
                        .sizeDp(16)
                        .color(getResources().getColor(R.color.md_white_1000)
                        )
        );

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View view = mApplication.getmView();
        Bitmap bitmap = getViewImageCache(view);
        if (bitmap != null) {
            mIcon_temp.setBackground(new BitmapDrawable(bitmap));
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

        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(mIcon_temp.getLayoutParams());
        //需要减去状态栏的高度，要不然实际图像会比RecyclerView中的item低
        marginLayoutParams.topMargin = top - DensityUtil.getStatusBarH(this);
        marginLayoutParams.leftMargin = left;
        marginLayoutParams.width = view.getWidth();
        marginLayoutParams.height = view.getHeight();
        //得到位置后
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginLayoutParams);
        //设置到imageView中，让这个imageView与原来的item位置保持一致
        mIcon_temp.setLayoutParams(params);

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
     * frameLayout 的扩展动画
     */
    private void extend() {

        int height = DensityUtil.getScreenH(this);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mIcon_temp, "scaleY",
                1f, (float) height);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIcon_temp.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIcon_temp.setVisibility(View.GONE);
                mViewCoordinator.setVisibility(View.VISIBLE);
                //动画结束之后显示内容页面
                initFragment();
            }
        });

        //动画延迟
        animator.setStartDelay(1000);
//        animator.setDuration(10000);
        animator.start();
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
