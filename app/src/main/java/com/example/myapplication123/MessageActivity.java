package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ImageButton btn_send;
    EditText text_send;
    TextView username;
    ImageView photo;
   // AppBarLayout bar;
    MessageAdapter messageAdapter;
    ArrayList<Chat> mChat;
    RecyclerView recyclerView;
    String getUsernameFromIntent;
    ArrayList<String> list;
    ParseFile img;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

      //  bar=findViewById(R.id.bar_layout);
        text_send=findViewById(R.id.text_send);
        btn_send=findViewById(R.id.btn_send);
        photo=findViewById(R.id.profile_chat_image);
       username=findViewById(R.id.user_chat_name);

        recyclerView=findViewById(R.id.chatRecycleView);

        getUsernameFromIntent=getIntent().getStringExtra("USERNAME");

        //messageAdapter=new MessageAdapter(MessageActivity.this,mChat);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mChat=new ArrayList<>();

        readMessage(ParseUser.getCurrentUser().getUsername(),getUsernameFromIntent);


        messageAdapter=new MessageAdapter(MessageActivity.this,mChat);
        recyclerView.setAdapter(messageAdapter);

        loadReceiverDetails(getUsernameFromIntent);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendMessage(getUsernameFromIntent,text_send.getText().toString());

                loadAdapter();




                text_send.setText("");






            }
        });






    }

    private void loadReceiverDetails(String uname)
    {
        list=new ArrayList<>();

        ParseQuery<ParseUser> query =ParseUser.getQuery();


        query.whereEqualTo("username",uname);
        query.findInBackground((users, e)-> {
            if (e == null)
            {

                for(ParseUser user1:users)
                {

                    list.add(user1.get("fullName").toString());
                    img=user1.getParseFile("profilePicture");


                }
                String url=img.getUrl();

                Picasso.with(this).load(url).into(photo);

                username.setText(list.get(0));

            }
            else
            {
                Toast.makeText(getApplicationContext(),"can't fetch the data: "+e, Toast.LENGTH_LONG).show();
            }


        });


    }

    private void sendMessage(String receiver,String msg)
    {

        if(!receiver.equals("")|| msg.isEmpty())
        {
            ParseObject entity = new ParseObject("Inbox");

            entity.put("sender", ParseUser.getCurrentUser().getUsername());
            entity.put("receiver", receiver);
            entity.put("message", msg);

            // Saves the new object.
            // Notice that the SaveCallback is totally optional!
            entity.saveInBackground(e -> {
                if (e==null){
                    //Toast.makeText(this, "Message send", Toast.LENGTH_SHORT).show();
                    mChat.add(new Chat(msg,0));
                    loadAdapter();

                }else{
                    //Something went wrong
                    Toast.makeText(this, "Message sending failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
    private void readSendMessage(String receiver)//not used yet
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inbox");
        query.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        query.whereEqualTo("receiver",receiver);
        //query.whereEqualTo()
       // query.whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    for(int i=0;i<objects.size();i++)
                    {
                        String str=objects.get(i).getString("message");

                        mChat.add(new Chat(str,0));

                        //showAlert(objects.get(i).getUsername().toString());
                       loadAdapter();
                        //fetchUserList();



                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"can't read message "+e, Toast.LENGTH_LONG).show();

                }
            }
        });






    }

    private void readReceivedMessage(String sender)//not used yet
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inbox");
        query.whereEqualTo("sender", sender);
        query.whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());
        // query.whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    for(int i=0;i<objects.size();i++)
                    {
                        String str=objects.get(i).getString("message");

                        mChat.add(new Chat(str,1));

                        //showAlert(objects.get(i).getUsername().toString());
                        loadAdapter();
                        //fetchUserList();



                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"can't read message "+e, Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    private void readMessage(String user1, String user2)
    {
        ParseQuery<ParseObject> queryPart1 = ParseQuery.getQuery("Inbox");
        queryPart1.whereEqualTo("sender", user1);
        queryPart1.whereEqualTo("receiver", user2);

// build second AND condition
        ParseQuery<ParseObject> queryPart2 = ParseQuery.getQuery("Inbox");
        queryPart2.whereEqualTo("sender", user2);
        queryPart2.whereEqualTo("receiver", user1);

// list all queries condition for next step
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(queryPart1);
        queries.add(queryPart2);

// Compose the OR clause
        ParseQuery<ParseObject> innerQuery = ParseQuery.or(queries);
        innerQuery.addAscendingOrder("createdAt");

        innerQuery.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> results, ParseException e) {


                if(e==null)
                {
                    for(int i=0;i<results.size();i++)
                    {
                        String str=results.get(i).getString("message");
                        String sen=results.get(i).getString("sender");
                        if(sen.equals(ParseUser.getCurrentUser().getUsername()))
                        {
                            mChat.add(new Chat(str,0));//this is for loading rightside item design
                        }
                        else
                        {
                            mChat.add(new Chat(str,1));//this is for loading leftside item design
                        }



                        //showAlert(objects.get(i).getUsername().toString());
                        loadAdapter();
                        //fetchUserList();



                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"can't read message "+e, Toast.LENGTH_LONG).show();

                }
            }

        });




    }

    void loadAdapter()
    {

        messageAdapter=new MessageAdapter(MessageActivity.this,mChat);
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }


}