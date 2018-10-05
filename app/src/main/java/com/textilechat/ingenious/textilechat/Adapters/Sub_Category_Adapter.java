package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.Sub_category_class;

import java.util.List;

public class Sub_Category_Adapter extends RecyclerView.Adapter<Sub_Category_Adapter.Sub_CategoryHolder>
{
    private Context context;
    private List<Sub_category_class> subcategoryList;

    class Sub_CategoryHolder extends RecyclerView.ViewHolder {
        TextView sc_name;
        ImageView sc_icon;

        public Sub_CategoryHolder(View itemView)
        {
            super(itemView);
            sc_name=itemView.findViewById(R.id.categor_name);
            sc_icon=itemView.findViewById(R.id.banner_image);

        }
    }

    public Sub_Category_Adapter(Context context, List<Sub_category_class> subcategoryList) {
        this.context = context;
        this.subcategoryList = subcategoryList;
    }

    @Override
    public void onBindViewHolder(Sub_CategoryHolder holder , int position)
    {
        final Sub_category_class sub_category = subcategoryList.get(position);
        Glide.with(context)
                .load(sub_category.getSc_image())
                .into(holder.sc_icon);
        holder.sc_name.setText(sub_category.getSc_name());

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
