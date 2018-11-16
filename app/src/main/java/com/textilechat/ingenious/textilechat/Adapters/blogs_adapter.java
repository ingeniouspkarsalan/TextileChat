package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.News_Class;
import com.textilechat.ingenious.textilechat.classes.blog_class;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class blogs_adapter extends RecyclerView.Adapter<blogs_adapter.blogsHolder>
{
    private Context context;
    private List<blog_class> blog_classList;

    class blogsHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView news_image;
        TextView news_title,news_des;

        public blogsHolder(View itemView)
        {
            super(itemView);
            view=itemView.findViewById(R.id.news_card_view);
            news_image=itemView.findViewById(R.id.news_image);
            news_title=itemView.findViewById(R.id.news_title);
            news_des=itemView.findViewById(R.id.news_desc);

        }
    }

    public blogs_adapter(Context context, List<blog_class> blog_classList) {
        this.context = context;
        this.blog_classList = blog_classList;
    }

    @Override
    public void onBindViewHolder(blogs_adapter.blogsHolder holder , int position)
    {
        final blog_class blogclass = blog_classList.get(position);
        Glide.with(context)
                .load(blogclass.getB_image())
                .into(holder.news_image);
        holder.news_title.setText(blogclass.getB_title());
        holder.news_des.setText(blogclass.getB_description());
        holder.news_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(context, blogclass.getB_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public blogs_adapter.blogsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_card_design, null);
        return new blogs_adapter.blogsHolder(view);
    }

    @Override
    public int getItemCount() {
        return blog_classList.size();
    }



}

