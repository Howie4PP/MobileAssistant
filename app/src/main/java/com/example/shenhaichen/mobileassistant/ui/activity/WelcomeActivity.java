package com.example.shenhaichen.mobileassistant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eftimoff.androipathview.PathView;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.pathView)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);
        //设定svg的动画
        pathView.getPathAnimator().delay(100).duration(3000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        transferActivity();
                    }
                })
                .start();

    }

    private void transferActivity(){
        String isShowGuide = ACache.get(this).getAsString(Constant.IS_SHOW_SPLASH);

        // 第一次启动进入引导页面
        if(null == isShowGuide){
            startActivity(new Intent(this,SplashActivity.class));

        }
        else{
            startActivity(new Intent(this,MainActivity.class));
        }

        this.finish();

    }
}
