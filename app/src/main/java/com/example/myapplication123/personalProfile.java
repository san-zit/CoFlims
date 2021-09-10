package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class personalProfile extends AppCompatActivity {

    Button editProfile;
    ImageView settings;
    ImageButton ib;
    ImageButton msgbox;
    ImageView photo;
    TextView following, followers, likes,userName;
    EditText bio;
    ArrayList<String> list;
    ParseFile img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);



        editProfile=findViewById(R.id.editProfileButton);
        settings=findViewById(R.id.buttonSettings);
        ib=findViewById(R.id.imageButtonSearch);
        photo=findViewById(R.id.profilePhotoImageView);
        following=findViewById(R.id.followingCountTextView);
        followers=findViewById(R.id.followersCountTextView);
        likes=findViewById(R.id.likesCountTextView);
        userName=findViewById(R.id.userNameTextView);
        bio=findViewById(R.id.bioTextMultiLine);
        msgbox=findViewById(R.id.imageButtonMessage);

        msgbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(personalProfile.this,MessengerList.class);
                startActivity(intent);
            }
        });


        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(personalProfile.this,userlist.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(personalProfile.this,Settings.class);
                startActivity(intent);



            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(personalProfile.this, Editprofile.class);
                startActivity(intent);
            }
        });

        loadData(ParseUser.getCurrentUser().getUsername());


    }

    void loadData(String uname)
    {
        list=new ArrayList<>();
        ParseQuery<ParseUser> query =ParseUser.getQuery();


        query.whereEqualTo("username",uname);
        query.findInBackground((users, e)-> {
            if (e == null)
            {

                for(ParseUser user1:users)
                {
                    //list.add(user1.get("username").toString());
                    list.add(user1.get("bio").toString());
                    list.add(user1.get("fullName").toString());
                    img=user1.getParseFile("profilePicture");
                    list.add(user1.get("following").toString());
                    list.add(user1.get("followers").toString());
                    list.add(user1.get("likes").toString());


                }
                String url=img.getUrl();

                Picasso.with(this).load(url).into(photo);
                bio.setText(list.get(0));
                userName.setText(list.get(1));
                //following.setText(list.get(2));
                //followers.setText(list.get(3));
                likes.setText(list.get(4));
               // editProfile.setText('f'+'g');

                followersCount(uname);
                followingCount(uname);












            }
            else
            {
                Toast.makeText(getApplicationContext(),"can't fetch the data: "+e, Toast.LENGTH_LONG).show();
            }


        });

    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent intent= new Intent(personalProfile.this,signIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog ok=builder.create();
        ok.show();
    }

    private void followingCount(String uname)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Following");
        //We can query in two ways
        query.whereEqualTo("followedby",uname);


        //Fetches count synchronously,this will block the main thread
        try {
            following.setText(String.valueOf(query.count()));
            //Toast.makeText(this, "Count : "+query.count(), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, "Parse exception : "+e, Toast.LENGTH_SHORT).show();
        }


    }
    private void followersCount(String uname)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Following");
        //We can query in two ways
        query.whereEqualTo("uname",uname);


        //Fetches count synchronously,this will block the main thread
        try {
            followers.setText(String.valueOf(query.count()));
            //Toast.makeText(this, "Count : "+query.count(), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(this, "Parse exception : "+e, Toast.LENGTH_SHORT).show();
        }

    }
}