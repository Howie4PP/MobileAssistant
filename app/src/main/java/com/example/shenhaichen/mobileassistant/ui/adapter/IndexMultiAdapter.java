package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.Banner;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;
import com.example.shenhaichen.mobileassistant.ui.widget.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 对应首页不同recyclerview布局的adapter
 * Created by shenhaichen on 14/01/2018.
 */
public class IndexMultiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    public static final int TYPE_BANNER = 1;
    public static final int TYPE_ICON = 2;
    public static final int TYPE_APPS = 3;
    public static final int TYPE_GAMES = 4;

    private LayoutInflater mLayoutInflater;
    private Context context;
    private IndexBean indexBean;


    public IndexMultiAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }

    @Override
    public int getItemViewType(int position) {
       if (position == 0){
           return TYPE_BANNER;
       }else  if (position == 1){
           return TYPE_ICON;
       }else  if (position == 2){
           return TYPE_APPS;
       }else  if (position == 3){
           return TYPE_GAMES;
       }
       return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_BANNER){
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.template_banner,parent,false));
        }else if (viewType == TYPE_ICON){
            return new IconViewHolder(mLayoutInflater.inflate(R.layout.template_nav_icon,parent,false));
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if (position == 0){
               BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;

               List<Banner> banners = indexBean.getBanners();
               List<String> urls = new ArrayList<>(banners.size());

               for(Banner banner: banners){
                   urls.add(banner.getThumbnail());
               }
               bannerViewHolder.mBanner.setViewUrls(urls);

           }else if (position ==1){
               IconViewHolder iconViewHolder = (IconViewHolder) holder;
               iconViewHolder.mLayoutHotApp.setOnClickListener(this);
               iconViewHolder.mLayoutHotGame.setOnClickListener(this);
               iconViewHolder.mLayoutHotSubject.setOnClickListener(this);
           }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onClick(View v) {

    }

    //根据不同的type去创建ViewHolder
    class BannerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.banner)
        BannerLayout mBanner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mBanner.setImageLoader(new ImgLoader());
        }
    }

    class IconViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.layout_hot_app)
        LinearLayout mLayoutHotApp;
        @BindView(R.id.layout_hot_game)
        LinearLayout mLayoutHotGame;
        @BindView(R.id.layout_hot_subject)
        LinearLayout mLayoutHotSubject;


        public IconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ImgLoader implements BannerLayout.ImageLoader{

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.load(path,imageView);
        }
    }


}
