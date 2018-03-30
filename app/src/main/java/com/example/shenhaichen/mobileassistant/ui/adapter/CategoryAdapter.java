package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.Category;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;

/**
 * Created by shenhaichen on 30/03/2018.
 */

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder> {
    public CategoryAdapter() {
        super(R.layout.template_category);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category item) {
        helper.setText(R.id.text_name, item.getName());
        ImageLoader.load(Constant.BASE_IMG_URL + item.getIcon(), (ImageView) helper.getView(R.id.img_icon));
    }
}
