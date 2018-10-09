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

import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.chat_messages;

import java.util.List;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.chatViewHolder> {
    private Context context;
    private List<chat_messages> chat_messagesList;

    class chatViewHolder extends RecyclerView.ViewHolder {
        TextView message,timestamp,owner_message,owner_timestamp;

        public chatViewHolder(View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            timestamp=itemView.findViewById(R.id.timestamp);
            owner_message=itemView.findViewById(R.id.owner_message);
            owner_timestamp=itemView.findViewById(R.id.owner_timestamp);
        }
    }

    public chat_adapter(Context context, List<chat_messages> chat_messagesList) {
        this.context = context;
        this.chat_messagesList = chat_messagesList;
    }

    @Override
    public void onBindViewHolder(chatViewHolder holder, int position) {
        final chat_messages c_message = chat_messagesList.get(position);
        final String id = Prefs.getString("user_id", "0");
        if(c_message.getIds().equals(id)){
            holder.owner_message.setVisibility(View.VISIBLE);
            holder.owner_message.setText(c_message.getMessages().toString());
            holder.owner_timestamp.setVisibility(View.VISIBLE);
            holder.owner_timestamp.setText(c_message.getTimestamp().toString());
        }else{
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(c_message.getMessages().toString());
            holder.timestamp.setVisibility(View.VISIBLE);
            holder.timestamp.setText(c_message.getTimestamp().toString());
        }

    }

    @Override
    public chatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.desing_category_list, null);
        return new chatViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return chat_messagesList.size();
    }
}