package com.example.shenhaichen.mobileassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.bean.AppInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 02/01/2018.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private List<AppInfo> mList;
    private Context context;

    public RecommendAdapter(List<AppInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.template_recommend_app,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
           AppInfo appInfo = mList.get(position);
        //设定icon和title,以及显示App大小
        String baseImgUrl ="http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";
        Picasso.with(context).load(baseImgUrl +appInfo.getIcon()).placeholder(R.drawable.pic_placeholder)
                .error(R.drawable.pic_error).into(holder.imageView);
        holder.mTextTitle.setText(appInfo.getDisplayName());
        holder.mTextSize.setText((appInfo.getApkSize() / 1024 /1024) +" MB");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_icon)
        ImageView imageView;
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_size)
        TextView mTextSize;
        @BindView(R.id.btn_dl)
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
