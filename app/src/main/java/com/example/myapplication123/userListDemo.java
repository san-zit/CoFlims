package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class userListDemo extends AppCompatActivity {

    ListView lv;
    List<String> usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_demo);

        lv=findViewById(R.id.ulistdemo);


        fetchUserList();



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),usr.get(position), Toast.LENGTH_LONG).show();

                String value=usr.get(position);
                Intent intent=new Intent(userListDemo.this,LoadSearchedUser.class);
                intent.putExtra("UNAME",value.substring(1));
                startActivity(intent);


            }
        });



    }
    void fetchUserList()

    {
        usr=new ArrayList<>();

        ParseQuery<ParseUser> query =ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null && objects!=null)
                {
                    for(int i=0;i<objects.size();i++)
                    {
                        usr.add("@"+objects.get(i).getUsername().toString());
                        //showAlert(objects.get(i).getUsername().toString());
                        loadAdapter();
                        //fetchUserList();



                    }
                    //showAlert("job done");


                }
                else
                {
                    showAlert(e+ "errror");

                }
            }
        });
    }
    void loadAdapter()
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,usr);
        lv.setAdapter(adapter);

    }

    void showAlert( String s)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(s);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
}