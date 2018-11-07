package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.News_Class;

import java.util.List;

public class New_Adapter extends RecyclerView.Adapter<New_Adapter.NewsHolder>
{
    private Context context;
    private List<News_Class> newsClassList;

    class NewsHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView news_image;
        TextView news_title,news_des;

        public NewsHolder(View itemView)
        {
            super(itemView);
            view=itemView.findViewById(R.id.news_card_view);
            news_image=itemView.findViewById(R.id.news_image);
            news_title=itemView.findViewById(R.id.news_title);
            news_des=itemView.findViewById(R.id.news_desc);

        }
    }

    public New_Adapter(Context context, List<News_Class> newsClassList) {
        this.context = context;
        this.newsClassList = newsClassList;
    }

    @Override
    public void onBindViewHolder(NewsHolder holder , int position)
    {
        final News_Class newsClass = newsClassList.get(position);
        Glide.with(context)
                .load(newsClass.getNews_image())
                .into(holder.news_image);
        holder.news_title.setText(newsClass.getNews_title());
        holder.news_des.setText(newsClass.getNews_des());

    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_card_design, null);
        return new NewsHolder(view);
    }

    @Override
    public int getItemCount() {
        return newsClassList.size();
    }



}
