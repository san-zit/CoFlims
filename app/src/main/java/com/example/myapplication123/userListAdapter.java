package com.example.myapplication123;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class userListAdapter extends RecyclerView.Adapter<userListAdapter.MyViewHolder>
{
    public Context mContext;
    private ArrayList<userListModelClass> list;
    View view;

    public userListAdapter(Context mContext, ArrayList<userListModelClass> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view=LayoutInflater.from(mContext).inflate(R.layout.userlistdesign,parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull userListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        String url=list.get(position).getImage().getUrl();
        //Picasso.with(this).load(url).into(imageView);
        Picasso.with(mContext).load(url).into(holder.imageView);
       // holder..setImageResource();




        holder.menuPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(mContext,v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.message:
                                String uname1=holder.name.getText().toString();
                                Intent intent1=new Intent(mContext,MessageActivity.class);//load chat
                                intent1.putExtra("USERNAME",uname1);
                                mContext.startActivity(intent1);



                                //Toast.makeText(mContext, "Message menu clicked",Toast.LENGTH_SHORT).show();
                                //open message window
                                break;

                            case R.id.profile:
                                String uname=holder.name.getText().toString();
                                Intent intent=new Intent(mContext,LoadSearchedUser.class);//load user
                                intent.putExtra("UNAME",uname);
                                mContext.startActivity(intent);
                                //Toast.makeText(mContext, "Profile menu clicked",Toast.LENGTH_SHORT).show();
                                //open selected user profile
                                break;

                        }



                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, menuPopUp;
        TextView name;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageViewProfilePicture);
            menuPopUp=itemView.findViewById(R.id.pupupMenuImageView);
            name=itemView.findViewById(R.id.userNameTextViewRV);

        }
    }



}