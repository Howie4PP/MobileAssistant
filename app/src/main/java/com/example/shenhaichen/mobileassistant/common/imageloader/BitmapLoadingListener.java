package com.example.shenhaichen.mobileassistant.common.imageloader;

import android.graphics.Bitmap;

/**
 * @author Ivan
 * @version V1.0
 * Created by shenhaichen on 14/01/2018.
 */

public interface BitmapLoadingListener {
    void onSuccess(Bitmap b);

    void onError();
}
