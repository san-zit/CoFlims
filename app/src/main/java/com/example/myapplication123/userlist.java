package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class userlist extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<userListModelClass> list;
    userListAdapter myAdapter;
    EditText searchBox;
    Button search_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        recyclerView=findViewById(R.id.recycleViewSearch);
        search_btn=findViewById(R.id.searchButtonUserList);
        searchBox=findViewById(R.id.searchBoxEditText);

        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //searchBox.selectAll();


        populateList();



        //myAdapter.notifyDataSetChanged();
    }

    private void populateList()
    {


        list =new ArrayList<>();
        final String[] str = new String[1];

        ParseQuery<ParseUser> query =ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null && objects!=null)
                {
                    for(int i=0;i<objects.size();i++)
                    {
                        list.add(new userListModelClass(objects.get(i).getUsername().toString(),objects.get(i).getParseFile("profilePicture")));
                        //showAlert(objects.get(i).getUsername().toString());
                        loadAdapter();
                        //fetchUserList();



                    }
                    //showAlert("job done");


                }
                else
                {
                    showAlert(e+ "error");

                }
            }
        });


//            list.add(new userListModelClass("Sanjit", R.drawable.img_1));
//            list.add(new userListModelClass("ranjit", R.drawable.img_1));
//            list.add(new userListModelClass("knowun", R.drawable.img_1));
//            list.add(new userListModelClass("mysterios", R.drawable.img_1));
//            list.add(new userListModelClass("sing", R.drawable.img_1));
//            list.add(new userListModelClass("bikram", R.drawable.img_1));
//            list.add(new userListModelClass("rohit", R.drawable.img_1));
//            list.add(new userListModelClass("subas", R.drawable.img_1));
//            list.add(new userListModelClass("kabita", R.drawable.img_1));
//            list.add(new userListModelClass("babita", R.drawable.img_1));

            // userListModelClass obj=new userListModelClass();



    }
    private void loadAdapter()
    {
        myAdapter=new userListAdapter(this,list);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(myAdapter);
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