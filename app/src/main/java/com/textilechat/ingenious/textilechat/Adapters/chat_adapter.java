package com.textilechat.ingenious.textilechat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.textilechat.ingenious.textilechat.activities.Singal_User_Chat;
import com.textilechat.ingenious.textilechat.activities.User_profile;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.CategoryClass;
import com.textilechat.ingenious.textilechat.classes.chat_messages;

import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.chatViewHolder> {
    private Context context;
    private List<chat_messages> chat_messagesList;
    private static String today;
    private int for_unread=0;

    public class chatViewHolder extends RecyclerView.ViewHolder {
        TextView message,timestamp,owner_message,owner_timestamp,username,owner_username,new_msg,admin_msg,nad_msg;
        RelativeLayout layoutofother,layoutofowner,admin_layout;
        ImageView owner_image,other_image;

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
            owner_image=itemView.findViewById(R.id.owner_image);
            other_image=itemView.findViewById(R.id.other_image);
            new_msg=itemView.findViewById(R.id.new_msg);

            admin_layout=itemView.findViewById(R.id.admin_layout);
            admin_msg=itemView.findViewById(R.id.admin_msg);
            nad_msg=itemView.findViewById(R.id.nad_msg);
        }
    }

    public chat_adapter(Context context, List<chat_messages> chat_messagesList) {
        this.context = context;
        this.chat_messagesList = chat_messagesList;
        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onBindViewHolder(chatViewHolder holder, final int position) {
        final chat_messages c_message = chat_messagesList.get(position);
        final String id = Prefs.getString("user_id", "0");
        final Intent i = new Intent(context, User_profile.class);
        //when user click on other user profile picture to view user profile
        holder.other_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("other_user_id",c_message.getIds());
                context.startActivity(i);
                Animation.fade(context);
            }
        });

        if(c_message.getIds().equals(id) && c_message.getAdmin_msg().equals("0")){
            holder.layoutofowner.setVisibility(View.VISIBLE);
            holder.owner_username.setText(c_message.getUser_name().toString());
            holder.owner_message.setText(" "+c_message.getMessages().toString());
            holder.owner_timestamp.setText(c_message.getTimestamp().toString());
            Glide.with(context)
                    .load(c_message.getU_image())
                    .into(holder.owner_image);
            i.putExtra("owner_image",c_message.getU_image());
            Prefs.putString("owner_image",c_message.getU_image());
            if(c_message.getU_status().equals("0")){
                holder.owner_message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.for_msg_alert,0,0,0);
                holder.owner_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toasty.error(context,"Wait for Admin approve", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else if(!c_message.getIds().equals(id) && c_message.getAdmin_msg().equals("0")) {
            if(!c_message.getU_status().equals("0")){
                holder.layoutofother.setVisibility(View.VISIBLE);
                if(for_unread!=0){
                    if(for_unread==position){
                       holder.new_msg.setVisibility(View.VISIBLE);
                    }
                }
                holder.username.setText(c_message.getUser_name().toString());
                holder.message.setText(c_message.getMessages().toString());
                holder.message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in=new Intent(context,Singal_User_Chat.class);
                        in.putExtra("user_id",c_message.getIds());
                        in.putExtra("user_name",c_message.getUser_name());
                        in.putExtra("other_image",c_message.getU_image());
                        context.startActivity(in);
                        Animation.shrink(context);
                    }
                });
                holder.timestamp.setText(c_message.getTimestamp().toString());
                Glide.with(context)
                        .load(c_message.getU_image())
                        .into(holder.other_image);
                i.putExtra("other_image",c_message.getU_image());
            }

        }else if(!c_message.getIds().equals(id) && c_message.getAdmin_msg().equals("1")){
        holder.admin_layout.setVisibility(View.VISIBLE);
        holder.admin_msg.setText(c_message.getMessages());
            if(for_unread!=0){
                if(for_unread==position){
                    holder.nad_msg.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public chatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.chat_cards, null);
        return new chatViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return chat_messagesList.size();
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public int getItemPosition(int id)
    {
        for (int position=0; position<chat_messagesList.size(); position++)
            if (chat_messagesList.get(position).getM_id().equals(String.valueOf(id))){
                for_unread=position;
                return position;
            }

        return 0;
    }
    public void searchedList(List<chat_messages> _list)
    {
        chat_messagesList = _list;
        notifyDataSetChanged();
    }

}