package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.Single_user_msg_list;
import com.textilechat.ingenious.textilechat.classes.chat_messages;

import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Singlechat_adaptor extends RecyclerView.Adapter<Singlechat_adaptor.singlechatViewHolder> {
    private Context context;
    private List<Single_user_msg_list> chat_messagesList;
    private String other_user_id,other_image,other_name;

    class singlechatViewHolder extends RecyclerView.ViewHolder {
        TextView message, timestamp, owner_message, owner_timestamp, username, owner_username;
        RelativeLayout layoutofother, layoutofowner;
        ImageView owner_image, other_image;

        public singlechatViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            timestamp = itemView.findViewById(R.id.timestamp);
            owner_message = itemView.findViewById(R.id.owner_message);
            owner_timestamp = itemView.findViewById(R.id.owner_timestamp);
            username = itemView.findViewById(R.id.user_name);
            owner_username = itemView.findViewById(R.id.owner_username);
            layoutofother = itemView.findViewById(R.id.layoutofother);
            layoutofowner = itemView.findViewById(R.id.layoutofowner);
            owner_image = itemView.findViewById(R.id.owner_image);
            other_image = itemView.findViewById(R.id.other_image);
        }
    }

    public Singlechat_adaptor(Context context, List<Single_user_msg_list> chat_messagesList,String other_user_id,String other_image,String other_name) {
        this.context = context;
        this.chat_messagesList = chat_messagesList;
        this.other_image=other_image;
        this.other_name=other_name;
        this.other_user_id=other_user_id;
    }

    @Override
    public void onBindViewHolder(singlechatViewHolder holder, int position) {
        final Single_user_msg_list c_message = chat_messagesList.get(position);
        final String id = Prefs.getString("user_id", "0");
        final String owner_name = Prefs.getString("user_name", "0");
        final String image_owner = Prefs.getString("owner_image", "0");

        if(c_message.getFrom_user_id().equals(id) && c_message.getTo_user_id().equals(other_user_id)) {
            if (c_message.getFrom_user_id().equals(id)) {
                holder.layoutofowner.setVisibility(View.VISIBLE);
                holder.owner_username.setText(owner_name);
                holder.owner_message.setText(c_message.getMessages().toString());
                holder.owner_timestamp.setText(c_message.getTimestamp().toString());
                Glide.with(context)
                        .load(image_owner)
                        .into(holder.owner_image);
            }
            else if(c_message.getTo_user_id().equals(other_user_id)){
                holder.layoutofother.setVisibility(View.VISIBLE);
                holder.username.setText(other_name);
                holder.message.setText(c_message.getMessages().toString());
                holder.timestamp.setText(c_message.getTimestamp().toString());
                Glide.with(context)
                        .load(other_image)
                        .into(holder.other_image);
            }
        }
        else if(c_message.getFrom_user_id().equals(other_user_id) && c_message.getTo_user_id().equals(id))
        {
            if(c_message.getFrom_user_id().equals(other_user_id)) {
                holder.layoutofother.setVisibility(View.VISIBLE);
                holder.username.setText(other_name);
                holder.message.setText(c_message.getMessages().toString());
                holder.timestamp.setText(c_message.getTimestamp().toString());
                Glide.with(context)
                        .load(other_image)
                        .into(holder.other_image);
            }
            else if(c_message.getTo_user_id().equals(id))
            {
                holder.layoutofowner.setVisibility(View.VISIBLE);
                holder.owner_username.setText(owner_name);
                holder.owner_message.setText(c_message.getMessages().toString());
                holder.owner_timestamp.setText(c_message.getTimestamp().toString());
                Glide.with(context)
                        .load(image_owner)
                        .into(holder.owner_image);
            }
        }




    }

    @Override
    public singlechatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.chat_cards, null);
        return new singlechatViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return chat_messagesList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}


