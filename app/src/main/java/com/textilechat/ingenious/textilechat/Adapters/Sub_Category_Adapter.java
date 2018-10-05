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

import java.util.List;

public class Sub_Category_Adapter extends RecyclerView.Adapter<Sub_Category_Adapter.Sub_CategoryHolder>
{
    private Context context;
    private List<String> subcategoryList;

    class Sub_CategoryHolder extends RecyclerView.ViewHolder {


        public Sub_CategoryHolder(View itemView)
        {
            super(itemView);

        }
    }

    public Sub_Category_Adapter(Context context, List<String> subcategoryList) {
        this.context = context;
        this.subcategoryList = subcategoryList;
    }

    @Override
    public void onBindViewHolder(Sub_CategoryHolder holder , int position)
    {
        //final CategoryClass sub_category = subcategoryList.get(position);


    }

    @Override
    public Sub_CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.desing_category_list, null);
        return new Sub_CategoryHolder(view);
    }

    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }



}
