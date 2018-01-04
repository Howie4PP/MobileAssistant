package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.mobileassistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 04/01/2018.
 */

public class SplashFragment extends Fragment {

    public static final String IMG_ID = "IMG_ID";
    public static final String COLOR_ID = "COLOR_ID";
    public static final String TEXT_ID = "TEXT_ID";

    @BindView(R.id.fragment_splash_image)
    ImageView imageView;
    @BindView(R.id.fragment_splash_txt)
    TextView textView;

    private View mRootView;

    //设定不同的背景颜色，图片和文字
    public static SplashFragment newInstance(int imgResId, int bgColor, int textId) {

        SplashFragment splashFragment = new SplashFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IMG_ID, imgResId);
        bundle.putInt(COLOR_ID, bgColor);
        bundle.putInt(TEXT_ID, textId);
        splashFragment.setArguments(bundle);

        return splashFragment;
    }

    public SplashFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_splash,container,false);

        ButterKnife.bind(this,mRootView);
        initData();
        return mRootView;
    }

    private void initData() {
        Bundle bundle = getArguments();
        int colorId = bundle.getInt(COLOR_ID);
        int imgId = bundle.getInt(IMG_ID);
        int textId = bundle.getInt(TEXT_ID);


        mRootView.setBackgroundColor(getResources().getColor(colorId));
        imageView.setImageResource(imgId);
        textView.setText(textId);



    }
}
