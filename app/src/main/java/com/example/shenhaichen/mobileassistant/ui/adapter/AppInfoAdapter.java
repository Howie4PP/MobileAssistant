package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;

/**
 * Created by shenhaichen on 22/03/2018.
 */

public class AppInfoAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {

    String baseImgUrl = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";

    private Builder mBuilder;

    private AppInfoAdapter(Builder builder) {
        super(builder.layoutId);

        this.mBuilder = builder;
        openLoadAnimation();
    }

    /**
     * 建造者模式，去控制需要显示的控件
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        ImageLoader.load(baseImgUrl + item.getIcon(), (ImageView) helper.getView(R.id.img_app_icon));
        helper.setText(R.id.txt_app_name, item.getDisplayName())
                .setText(R.id.txt_brief, item.getBriefShow());

        TextView txtViewPosition = helper.getView(R.id.txt_position);
        if (txtViewPosition != null) {
            txtViewPosition.setVisibility(mBuilder.isShowPosition ? View.VISIBLE : View.GONE);
            txtViewPosition.setText(item.getPosition() + 1 + ". ");
        }

        TextView txtViewCategory = helper.getView(R.id.txt_category);
        if (txtViewCategory != null) {
            txtViewCategory.setVisibility(mBuilder.isShowCategoryName ? View.VISIBLE : View.GONE);
            txtViewCategory.setText(item.getLevel1CategoryName());
        }

        TextView txtViewBrief = helper.getView(R.id.txt_brief);
        if (txtViewBrief != null) {
            txtViewBrief.setVisibility(mBuilder.isShowBrief ? View.VISIBLE : View.GONE);
            txtViewBrief.setText(item.getBriefShow());
        }

        TextView textViewSize = helper.getView(R.id.txt_apk_size);
        if (textViewSize != null){
            textViewSize.setText((item.getApkSize() / 1014 / 1024) + "MB");
        }
    }

    public static class Builder {
        private boolean isShowPosition;
        private boolean isShowCategoryName;
        private boolean isShowBrief;
        //默认的布局
        private int layoutId = R.layout.template_appinfo_horizontal;

        public Builder showPosition(boolean isShow) {
            this.isShowPosition = isShow;
            return this;
        }

        public Builder showCategoryName(boolean isShow) {
            this.isShowCategoryName = isShow;
            return this;
        }

        public Builder showBrief(boolean isShow) {
            this.isShowBrief = isShow;
            return this;
        }

        public AppInfoAdapter build() {
            return new AppInfoAdapter(this);
        }

        /**
         * 使用自己定义的布局
         * @param resId
         * @return
         */
        public Builder layout(int resId){
            this.layoutId = resId;
            return this;
        }
    }


}
