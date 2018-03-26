package com.example.shenhaichen.mobileassistant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.Banner;
import com.example.shenhaichen.mobileassistant.bean.IndexBean;
import com.example.shenhaichen.mobileassistant.common.imageloader.ImageLoader;
import com.example.shenhaichen.mobileassistant.ui.decoration.DividerItemDecoration;
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
    private Context mContext;
    private IndexBean mIndexBean;


    public IndexMultiAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setmIndexBean(IndexBean mIndexBean) {
        this.mIndexBean = mIndexBean;
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
        } else if(viewType==TYPE_APPS){
             //这里是一种取巧的方法，不把parent传进去，是因为在recyclerview中嵌套recyclerview的时候，
             //如果设定了，它就不会计算内部recyclerview的高度，所以设为null，让系统重新去计算
            return  new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recycler_view_with_title, null, false),TYPE_APPS);
        }
        else if(viewType==TYPE_GAMES){

            return  new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recycler_view_with_title, null, false),TYPE_GAMES);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if (position == 0){
               BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;

               List<Banner> banners = mIndexBean.getBanners();
               List<String> urls = new ArrayList<>(banners.size());

               for(Banner banner: banners){
                   urls.add(banner.getThumbnail());
               }
               bannerViewHolder.mBanner.setViewUrls(urls);
               bannerViewHolder.mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                   @Override
                   public void onItemClick(int position) {

                   }
               });

           }else if (position ==1){
               IconViewHolder iconViewHolder = (IconViewHolder) holder;
               iconViewHolder.mLayoutHotApp.setOnClickListener(this);
               iconViewHolder.mLayoutHotGame.setOnClickListener(this);
               iconViewHolder.mLayoutHotSubject.setOnClickListener(this);
           }
           else {
               AppViewHolder viewHolder = (AppViewHolder) holder;

               AppInfoAdapter appInfoAdapter = AppInfoAdapter.builder()
                       .showPosition(false)
                       .showBrief(true)
                       .showCategoryName(false).build();

               if(viewHolder.type==TYPE_APPS){
                   viewHolder.mText.setText("热门应用");
                   appInfoAdapter.addData(mIndexBean.getRecommendApps());
               }
               else{
                   viewHolder.mText.setText("热门游戏");
                   appInfoAdapter.addData(mIndexBean.getRecommendGames());
               }

               viewHolder.mRecyclerView.setAdapter(appInfoAdapter);

               viewHolder.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {

                   @Override
                   public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                   }
               });


           }
    }

    @Override
    public int getItemCount() {
        return 4;
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

    class AppViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;
        int type;
        public AppViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.type = type;

            initRecyclerView();


        }

        private void initRecyclerView() {

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext) {
                //垂直方向上不允许进行滚动
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
            mRecyclerView.addItemDecoration(itemDecoration);

        }
    }


    class ImgLoader implements BannerLayout.ImageLoader{

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.load(path,imageView);
        }
    }


}
