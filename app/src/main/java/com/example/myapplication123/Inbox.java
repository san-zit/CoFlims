package com.example.myapplication123;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Inbox extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> userList;
    Adapter adapter;
    Button newMsg;
    List<String> uList;
    List<String> newList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        newMsg=findViewById(R.id.newMessage);
        newMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Inbox.this, SentNewMessage.class);
                startActivity(intent);
            }
        });


        initData();
        initRecyclerView();





    }

    private void initData() {

        userList =new ArrayList<>();



        

//        userList.add(new ModelClass(R.drawable.img_1, "Sanjit","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Binod","10:00pm"," Call me when you are free","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "abc","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Mr X","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "No name","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Sovam","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Sanjit","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Binod","10:00pm"," Call me when you are free","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "abc","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Mr X","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "No name","12:00pm"," I am fine","------------------------------------------------------------------------"));
//        userList.add(new ModelClass(R.drawable.img_1, "Sovam","12:00pm"," I am fine","------------------------------------------------------------------------"));
//
    }

    private void initRecyclerView()
    {
        recyclerView=findViewById(R.id.recycleView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
      //  adapter=new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    private void loadMessengerList()
    {
        uList=new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inbox");






        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> results, ParseException e) {


                if(e==null)
                {
                    for(int i=0;i<results.size();i++)
                    {
                        //String str=results.get(i).getString("message");
                        String sen=results.get(i).getString("sender");
                        String rec=results.get(i).getString("receiver");

                        if(sen.equals(ParseUser.getCurrentUser().getUsername())||rec.equals(ParseUser.getCurrentUser().getUsername()))
                        {

                            if(sen.equals(ParseUser.getCurrentUser().getUsername()))
                            {
                                uList.add("@"+rec);
                            }
                            else
                            {
                                uList.add("@"+sen);

                            }

                        }



                        // if(!sen.equals())
                        //loadAdapter();





                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"can't read messages "+e, Toast.LENGTH_LONG).show();

                }
            }

        });


    }
    void filterList()//removing duplicate values from messengerList and copying value into another List
    {
        newList=new ArrayList<>();

        int index=0;
        int count=0;

        for(int i=0;i<uList.size();i++)
        {

            int j;
            for(j=0;j<i;j++)
            {
                if(uList.get(i).equals(uList.get(j)))
                {

                    break;

                }

            }
            if(j==i)
            {
                uList.add(index++, uList.get(i));
                count++;


            }

        }

        //System.out.print(uList.toString()+" = "+String.valueOf(count));

        newList=uList.subList(0, count);

        //System.out.print(newList.toString());

    }

    private void getUserDetails(String uname)
    {


        for(int i=0;i<newList.size();i++)
        {
            ParseQuery<ParseUser> query =ParseUser.getQuery();
            query.whereEqualTo("username",uname);


            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e==null && objects!=null)
                    {
                        for(int i=0;i<objects.size();i++)
                        {
                            ParseFile file;
                            String usr;
                            //list.add(new userListModelClass(objects.get(i).getUsername().toString(),objects.get(i).getParseFile("profilePicture")));
                            //showAlert(objects.get(i).getUsername().toString());
                           // loadAdapter();
                            //fetchUserList();
                            usr=objects.get(i).getUsername().toString();
                            file=objects.get(i).getParseFile("profilePicture");





                        }
                        //showAlert("job done");


                    }
                    else
                    {
                        //showAlert(e+ "error");

                    }
                }
            });


        }


    }

}