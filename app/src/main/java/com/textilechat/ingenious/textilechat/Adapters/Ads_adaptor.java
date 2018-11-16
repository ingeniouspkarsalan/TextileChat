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
import com.textilechat.ingenious.textilechat.classes.Ads_class;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class Ads_adaptor extends RecyclerView.Adapter<Ads_adaptor.AdsHolder>
{
    private Context context;
    private List<Ads_class> ads_classList;

    class AdsHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView news_image;
        TextView news_title,news_des;

        public AdsHolder(View itemView)
        {
            super(itemView);
            view=itemView.findViewById(R.id.news_card_view);
            news_image=itemView.findViewById(R.id.news_image);
            news_title=itemView.findViewById(R.id.news_title);
            news_des=itemView.findViewById(R.id.news_desc);

        }
    }

    public Ads_adaptor(Context context, List<Ads_class> ads_classList) {
        this.context = context;
        this.ads_classList = ads_classList;
    }

    @Override
    public void onBindViewHolder(Ads_adaptor.AdsHolder holder , int position)
    {
        final Ads_class ads_class = ads_classList.get(position);
        Glide.with(context)
                .load(ads_class.getAd_image())
                .into(holder.news_image);
        holder.news_title.setText(ads_class.getCat_name());
        holder.news_des.setText(ads_class.getSub_name());
        holder.news_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(context, ads_class.getAd_id(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public Ads_adaptor.AdsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_card_design, null);
        return new Ads_adaptor.AdsHolder(view);
    }

    @Override
    public int getItemCount() {
        return ads_classList.size();
    }



}
