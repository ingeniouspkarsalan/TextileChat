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
import com.github.florent37.shapeofview.shapes.CircleView;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.activities.Singal_User_Chat;
import com.textilechat.ingenious.textilechat.activities.User_profile;
import com.textilechat.ingenious.textilechat.classes.Sqlite_for_markers;
import com.textilechat.ingenious.textilechat.classes.pc_class;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class pc_adaptor extends RecyclerView.Adapter<pc_adaptor.pcHolder>
{
    private Context context;
    private List<pc_class> pc_classList;

    class pcHolder extends RecyclerView.ViewHolder {
        ImageView user_image;
        TextView user_name,date,markers_count;
        LinearLayout usr_card;
        CircleView marker;
        Sqlite_for_markers sqlite_for_markers;

        public pcHolder(View itemView)
        {
            super(itemView);
            user_image=itemView.findViewById(R.id.user_image);
            user_name=itemView.findViewById(R.id.user_name);
            date=itemView.findViewById(R.id.dates);
            usr_card=itemView.findViewById(R.id.usr_card);
            markers_count=itemView.findViewById(R.id.markers_count);
            marker=itemView.findViewById(R.id.mark_show);
            sqlite_for_markers=new Sqlite_for_markers(context);
        }
    }

    public pc_adaptor(Context context, List<pc_class> pc_classList) {
        this.context = context;
        this.pc_classList = pc_classList;
    }

    @Override
    public void onBindViewHolder( pc_adaptor.pcHolder holder , int position)
    {
        final pc_class pcClass=pc_classList.get(position);
        final String id = Prefs.getString("user_id", "0");
        if(pcClass.getFrom_user_id().equals(id))
        {
            holder.usr_card.setVisibility(View.VISIBLE);
            Glide.with(context).load(pcClass.getTo_u_image()).into(holder.user_image);
            holder.user_name.setText(pcClass.getTo_u_name());
            holder.date.setText(pcClass.getDate());

            try {
                int i = holder.sqlite_for_markers.getpersonalCount(pcClass.getFrom_user_id());
                if (i > 0) {
                    holder.marker.setVisibility(View.VISIBLE);
                    holder.markers_count.setText(i + "");
                }
            }catch (Exception e){}


            holder.usr_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(context, Singal_User_Chat.class);
                    in.putExtra("user_id",pcClass.getTo_user_id());
                    in.putExtra("user_name",pcClass.getTo_u_name().toString());
                    context.startActivity(in);
                }
            });
        }
        else if(pcClass.getTo_user_id().equals(id))
        {
            holder.usr_card.setVisibility(View.VISIBLE);
            Glide.with(context).load(pcClass.getFrom_u_image()).into(holder.user_image);
            holder.user_name.setText(pcClass.getFrom_u_name());
            holder.date.setText(pcClass.getDate());

            try {
                int i = holder.sqlite_for_markers.getpersonalCount(pcClass.getFrom_user_id());
                if (i > 0) {
                    holder.marker.setVisibility(View.VISIBLE);
                    holder.markers_count.setText(i + "");
                }
            }catch (Exception e){}

            holder.usr_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in=new Intent(context, Singal_User_Chat.class);
                    in.putExtra("user_id",pcClass.getFrom_user_id());
                    in.putExtra("user_name",pcClass.getFrom_u_name().toString());
                    context.startActivity(in);
                }
            });

        }
    }
    @Override
    public pc_adaptor.pcHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.pc_card, null);
        return new pc_adaptor.pcHolder(view);
    }

    @Override
    public int getItemCount() {
        return pc_classList.size();
    }



}
