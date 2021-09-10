package com.example.myapplication123;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Chat> mChat;
    //String imageurl;

    public MessageAdapter(Context mContext, List<Chat> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
       // this.imageurl = imageUrl;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT)
        {
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);

        }
        else
        {
            View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

//        View view= LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
//        return new ViewHolder(view);

       // return new userListAdapter.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MessageAdapter.ViewHolder holder, int position) {

        //Chat chat=mChat.get(position);
        holder.show_message.setText(mChat.get(position).getMessage());


       // mChat.get(position).getMessage()

        //show Image as well





    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
       // public ImageView profile_image;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            show_message=itemView.findViewById(R.id.show_message);
            //profile_image=itemView.findViewById(R.id.profile_image);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if(mChat.get(position).type==1)//type1 is for incoming messages and type 0 is for outgoing messages
        {
            return MSG_TYPE_LEFT;

        }
        else
        {
            return MSG_TYPE_RIGHT;
        }

    }
}
