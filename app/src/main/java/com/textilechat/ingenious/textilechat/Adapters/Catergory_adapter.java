package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.CategoryClass;

import java.util.List;

public class Catergory_adapter extends RecyclerView.Adapter<Catergory_adapter.CategoryViewHolder>
{
    private Context context;
    private List<CategoryClass> categoryList;

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView banner_image;
        TextView categor_name;

        public CategoryViewHolder(View itemView)
        {
            super(itemView);
            banner_image = (ImageView) itemView.findViewById(R.id.banner_image);
            categor_name = (TextView) itemView.findViewById(R.id.categor_name);
        }
    }

    public Catergory_adapter(Context context, List<CategoryClass> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder , int position)
    {
        final CategoryClass category = categoryList.get(position);
        Glide.with(context)
                .load(category.getC_image())
                .into(holder.banner_image);
        holder.categor_name.setText(category.getC_name());

    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.desing_category_list, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }



}
