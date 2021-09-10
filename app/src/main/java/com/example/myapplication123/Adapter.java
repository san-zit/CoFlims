package com.example.myapplication123;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ModelClass> userList;
    public Context mContext;
    View view;

    public Adapter(List<ModelClass> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }

    @NonNull

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdesign,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        String url =userList.get(position).getPhoto().getUrl();

        Picasso.with(mContext).load(url).into(holder.photo);

        holder.username.setText((userList.get(position)).getUname());
        holder.message.setText(userList.get(position).getMessage());
        holder.time.setText(userList.get(position).getTime());

    }

    @Override
    public int getItemCount()
    {


        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView photo;
        private TextView username;
        private TextView message;
        private TextView time;
        private TextView divider;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            photo=itemView.findViewById(R.id.imageView1);
            username=itemView.findViewById(R.id.textview);
            message=itemView.findViewById(R.id.textviewmessage);
            time=itemView.findViewById(R.id.textviewtime);
            divider=itemView.findViewById(R.id.divider);
        }


    }
}
