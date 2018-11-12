package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.shapeofview.shapes.CircleView;
import com.pkmmte.view.CircularImageView;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.activities.Chat_Activity;
import com.textilechat.ingenious.textilechat.classes.Sqlite_for_markers;
import com.textilechat.ingenious.textilechat.classes.Sub_category_class;

import java.util.List;

public class Sub_Category_Adapter extends RecyclerView.Adapter<Sub_Category_Adapter.Sub_CategoryHolder>
{
    private Context context;
    private List<Sub_category_class> subcategoryList;
    private String c_id;

    class Sub_CategoryHolder extends RecyclerView.ViewHolder {
        TextView sc_name,markers_count;
        ImageView sc_icon;
        CardView for_click;
        CircleView marker;
        Sqlite_for_markers sqlite_for_markers;

        public Sub_CategoryHolder(View itemView)
        {
            super(itemView);
            sc_name=itemView.findViewById(R.id.categor_name);
            sc_icon=itemView.findViewById(R.id.banner_image);
            for_click=itemView.findViewById(R.id.card_view);
            markers_count=itemView.findViewById(R.id.markers_count);
            marker=itemView.findViewById(R.id.mark_show);
            sqlite_for_markers=new Sqlite_for_markers(context);
        }
    }

    public Sub_Category_Adapter(Context context, List<Sub_category_class> subcategoryList,String c_id) {
        this.context = context;
        this.subcategoryList = subcategoryList;
        this.c_id=c_id;
    }

    @Override
    public void onBindViewHolder(Sub_CategoryHolder holder , int position)
    {
        final Sub_category_class sub_category = subcategoryList.get(position);
        Glide.with(context)
                .load(sub_category.getSc_image())
                .into(holder.sc_icon);
        holder.sc_name.setText(sub_category.getSc_name());

        try {
            int i = holder.sqlite_for_markers.getSubCount(c_id, sub_category.getSc_id());
            if (i > 0) {
                holder.marker.setVisibility(View.VISIBLE);
                holder.markers_count.setText(i + "");
            }
        }catch (Exception e){}

        holder.for_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context, Chat_Activity.class);
                in.putExtra("c_id",c_id);
                in.putExtra("s_id",sub_category.getSc_id());
                in.putExtra("name",sub_category.getSc_name());
                in.putExtra("id_name","sub_category");
                context.startActivity(in);
            }
        });

    }

    @Override
    public Sub_CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.design_sub_catogory_list, null);
        return new Sub_CategoryHolder(view);
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }



}
