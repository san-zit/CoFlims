package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MessengerList extends AppCompatActivity {

    ListView lv;
    List<String> uList;
    List<String> newList;
    Button newMsgBtn;
    ImageButton backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_list);


        lv=findViewById(R.id.messenger_list);
        newMsgBtn=findViewById(R.id.new_message_button);
        backarrow=findViewById(R.id.back_arrow);

        newMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MessengerList.this,SentNewMessage.class);
                startActivity(intent);

            }
        });

        loadMessengerList();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=newList.get(position);
                Intent intent=new Intent(MessengerList.this,MessageActivity.class);
                intent.putExtra("USERNAME",value.substring(1));
                startActivity(intent);
            }
        });



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
                        loadAdapter();





                    }


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"can't read messages "+e, Toast.LENGTH_LONG).show();

                }
            }

        });


    }

    void loadAdapter()
    {
        filterList();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,newList);
        lv.setAdapter(adapter);

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
}