package com.example.shenhaichen.mobileassistant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eftimoff.androipathview.PathView;
import com.example.shenhaichen.mobileassistant.R;

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
        pathView.getPathAnimator().delay(300).duration(5000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        WelcomeActivity.this.finish();
                    }
                })
                .start();

    }
}
