package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LoadSearchedUser extends AppCompatActivity {
    Button followUnfollowbtn;
    ImageView options;
    ImageButton ib;
    ImageView photo;
    TextView following, followers, likes,userName;
    EditText bio;
    ArrayList<String> list;
    ParseFile img;
    String getUsernameFromIntent;
   // boolean flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_searched_user);


        followUnfollowbtn=findViewById(R.id.editProfileButton);
        options=findViewById(R.id.buttonSettings);
        ib=findViewById(R.id.imageButtonSearch);
        photo=findViewById(R.id.profilePhotoImageView);
        following=findViewById(R.id.followingCountTextView);
        followers=findViewById(R.id.followersCountTextView);
        likes=findViewById(R.id.likesCountTextView);
        userName=findViewById(R.id.userNameTextView);
        bio=findViewById(R.id.bioTextMultiLine);

        getUsernameFromIntent=getIntent().getStringExtra("UNAME");



        followUnfollowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!followUnfollowbtn.getText().equals("Unfollow"))
                {
                    follow(getUsernameFromIntent);
                }
                else
                {
                    unFollow(getUsernameFromIntent);
                }

            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getApplicationContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.profileoptions, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.sendmessage:
                                Toast.makeText(getApplicationContext(), "send Message menu clicked", Toast.LENGTH_SHORT).show();
                                //open message window

                                Intent intent=new Intent(LoadSearchedUser.this,MessageActivity.class);
                                intent.putExtra("USERNAME",getUsernameFromIntent);
                                startActivity(intent);
                                break;

                            case R.id.block:
                                Toast.makeText(getApplicationContext(), "block menu clicked", Toast.LENGTH_SHORT).show();
                                //open selected user profile
                                break;

                            case R.id.report:
                                Toast.makeText(getApplicationContext(), "report menu clicked", Toast.LENGTH_SHORT).show();
                                //open selected user profile
                                break;

                        }

                        return true;
                    }
                });



            }
        });



        loadData(getUsernameFromIntent);
        followCheck(getUsernameFromIntent);





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
                    //Object obj=ugetObjectId();


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
               // following.setText(list.get(2));
                //followers.setText(list.get(3));
                likes.setText(list.get(4));
                // editProfile.setText('f'+'g');












            }
            else
            {
                Toast.makeText(getApplicationContext(),"can't fetch the data: "+e, Toast.LENGTH_LONG).show();
            }


        });

        followersCount(uname);
        followingCount(uname);
    }

    private void follow(String uname)
    {
        ParseObject entity = new ParseObject("Following");

        entity.put("uname", uname);
        entity.put("followedby", ParseUser.getCurrentUser().getUsername());
       // entity.put("message", message.getText().toString());

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        entity.saveInBackground(e -> {
            if (e==null)

            {

                followUnfollowbtn.setText("Unfollow");
                followersCount(uname);

                //followUnfollowbtn.setBackgroundColor(@color/red);


              // followers.setText(followers.getText(String.valueOf()));




                    Toast.makeText(this, uname+" is followed by "+ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG).show();
            }
            else{
                //Something went wrong
                Toast.makeText(this, "Following failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void unFollow(String str)
    {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Following");
        query.whereEqualTo("followedby",ParseUser.getCurrentUser().getUsername());
        //query.whereEqualTo("followedby", ParseUser.getCurrentUser().getUsername());




        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> results, ParseException e) {
                if(e==null)
                {
                    for(int i=0;i<results.size();i++)
                    {


                        if(results.get(i).getString("uname").equals(str))
                        {
                            followUnfollowbtn.setText("Follow");
                            results.get(i).deleteInBackground();
                            Toast.makeText(getApplicationContext(), str +" is unfollowed by "+ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG).show();
                            followersCount(str);
                            break;


                        }
                    }

                }
                else
                {
                    // flag=false;
                    Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }



            }
        });





    }

    private void followCheck(String str)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Following");
        query.whereEqualTo("followedby",ParseUser.getCurrentUser().getUsername());
        //query.whereEqualTo("followedby", ParseUser.getCurrentUser().getUsername());




        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> results, ParseException e) {
                if(e==null)
                {
                    for(int i=0;i<results.size();i++)
                    {


                        if(results.get(i).getString("uname").equals(str))
                        {
                            followUnfollowbtn.setText("Unfollow");
                            break;


                        }
                    }

                    }
                else
                {
                   // flag=false;
                    Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }



            }
        });


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
            e.printStackTrace();
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