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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.activities.Sub_Category;
import com.textilechat.ingenious.textilechat.classes.CategoryClass;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class Catergory_adapter extends RecyclerView.Adapter<Catergory_adapter.CategoryViewHolder>
{
    private Context context;
    private List<CategoryClass> categoryList;

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView banner_image;
        TextView categor_name;
        CardView for_click;

        public CategoryViewHolder(View itemView)
        {
            super(itemView);
            banner_image = (ImageView) itemView.findViewById(R.id.banner_image);
            categor_name = (TextView) itemView.findViewById(R.id.categor_name);
            for_click = (CardView) itemView.findViewById(R.id.card_view);
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
        holder.for_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context, Sub_Category.class);
                in.putExtra("c_id",category.getC_id());
                context.startActivity(in);
            }
        });

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
