package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.activities.User_profile;
import com.textilechat.ingenious.textilechat.classes.pc_class;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class pc_adaptor extends RecyclerView.Adapter< pc_adaptor.pcHolder>
{
    private Context context;
    private List<pc_class> ads_classList;

    class pcHolder extends RecyclerView.ViewHolder {
        ImageView user_image;
        TextView user_name,msgtxt,date;
        LinearLayout usr_card;

        public pcHolder(View itemView)
        {
            super(itemView);
            user_image=itemView.findViewById(R.id.user_image);
            user_name=itemView.findViewById(R.id.user_name);
            msgtxt=itemView.findViewById(R.id.msgtxt);
            date=itemView.findViewById(R.id.date);
            usr_card=itemView.findViewById(R.id.usr_card);
        }
    }

    public pc_adaptor(Context context, List<pc_class> ads_classList) {
        this.context = context;
        this.ads_classList = ads_classList;
    }

    @Override
    public void onBindViewHolder( pc_adaptor.pcHolder holder , int position)
    {
        final pc_class pcClass=ads_classList.get(position);
        final String id = Prefs.getString("user_id", "0");
        if(pcClass.getFrom_user_id().equals(id))
        {
            holder.usr_card.setVisibility(View.VISIBLE);
            Glide.with(context).load(pcClass.getTo_u_image()).into(holder.user_image);
            holder.user_name.setText(pcClass.getTo_u_name());
            holder.msgtxt.setText(pcClass.getMessage());
            holder.date.setText(pcClass.getDate());
            holder.usr_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(context, User_profile.class);
                    in.putExtra("other_user_id",pcClass.getTo_user_id());
                    context.startActivity(in);
                }
            });
        }
        else if(pcClass.getTo_user_id().equals(id))
        {
            holder.usr_card.setVisibility(View.VISIBLE);
            Glide.with(context).load(pcClass.getFrom_u_image()).into(holder.user_image);
            holder.user_name.setText(pcClass.getFrom_u_name());
            holder.msgtxt.setText(pcClass.getMessage());
            holder.date.setText(pcClass.getDate());
            holder.usr_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(context, User_profile.class);
                    in.putExtra("other_user_id",pcClass.getFrom_user_id());
                    context.startActivity(in);
                }
            });

        }
    }
    @Override
    public  pc_adaptor.pcHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pc_card, null);
        return new pc_adaptor.pcHolder(view);
    }

    @Override
    public int getItemCount() {
        return ads_classList.size();
    }



}
