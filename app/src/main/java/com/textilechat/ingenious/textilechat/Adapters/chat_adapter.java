package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.chat_messages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.chatViewHolder> {
    private Context context;
    private List<chat_messages> chat_messagesList;
    private static String today;

    class chatViewHolder extends RecyclerView.ViewHolder {
        TextView message,timestamp,owner_message,owner_timestamp,username,owner_username;
        LinearLayout layoutofother,layoutofowner;

        public chatViewHolder(View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            timestamp=itemView.findViewById(R.id.timestamp);
            owner_message=itemView.findViewById(R.id.owner_message);
            owner_timestamp=itemView.findViewById(R.id.owner_timestamp);
            username=itemView.findViewById(R.id.user_name);
            owner_username=itemView.findViewById(R.id.owner_username);
            layoutofother=itemView.findViewById(R.id.layoutofother);
            layoutofowner=itemView.findViewById(R.id.layoutofowner);
        }
    }

    public chat_adapter(Context context, List<chat_messages> chat_messagesList) {
        this.context = context;
        this.chat_messagesList = chat_messagesList;
        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onBindViewHolder(chatViewHolder holder, int position) {
        final chat_messages c_message = chat_messagesList.get(position);
        final String id = Prefs.getString("user_id", "0");
        if(!c_message.getIds().equals(id)){
            holder.layoutofother.setVisibility(View.VISIBLE);
            holder.username.setText(c_message.getUser_name().toString());
            holder.message.setText(c_message.getMessages().toString());
            holder.timestamp.setText(c_message.getTimestamp().toString());
        }else  if(c_message.getIds().equals(id)){
            holder.layoutofowner.setVisibility(View.VISIBLE);
            holder.owner_username.setText(c_message.getUser_name().toString());
            holder.owner_message.setText(c_message.getMessages().toString());
            holder.owner_timestamp.setText(c_message.getTimestamp().toString());
        }

    }

    @Override
    public chatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_cards, null);
        return new chatViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return chat_messagesList.size();
    }



}