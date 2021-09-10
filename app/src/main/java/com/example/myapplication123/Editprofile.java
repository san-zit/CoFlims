package com.example.myapplication123;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Editprofile extends AppCompatActivity {

    EditText name;
    EditText username;
    EditText email;
    EditText bio;
    EditText contact;
    ImageView profilePicture;
    Button editProfile;
    Button saveChanges;
    ImageButton backArrow;
    ArrayList<String> list;
    ParseFile img;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        ParseInstallation.getCurrentInstallation().saveInBackground();


        editProfile=findViewById(R.id.changePictureButton);
        profilePicture=findViewById(R.id.profilePictureImageView);
        backArrow=findViewById(R.id.backArrowEditProfile);
        username=findViewById(R.id.editTextUsername);
        name=findViewById(R.id.editTextName);
        bio=findViewById(R.id.editTextTextMultiLineBio);
        saveChanges=findViewById(R.id.buttonSaveChanges);



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Editprofile.this, personalProfile.class);
                startActivity(intent);
            }
        });

        //load gallery for selecting picture
        editProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"select picture"),1);

                count++;


            }
        });
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().trim().equals("")||name.getText().toString().trim().equals("")||bio.getText().toString().equals(""))

                {
                   showAlert("User Profile Details","All fields need to filled");

                }
                else
                {
                    showAlertYesNo("User Profile Details","Do you want to save changes?");

                }


            }
        });


        fetchData();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Uri imageUri=data.getData();
            profilePicture.setImageURI(imageUri);


        }
    }


    private void saveChange(ImageView photo)
    {
        String uname;
        String fullName;
        String bio1;
        boolean isDataChanged;
       // String imgPath;

        uname=username.getText().toString();
        fullName=name.getText().toString();
        bio1=bio.getText().toString();

      //  ParseFile photo=new ParseFile(profilePicture.);

       // ImageView imageView = (ImageView) findViewById(R.id.imageView);

        ParseUser currentUser=ParseUser.getCurrentUser();

        //String cu=ParseUser.getCurrentUser().getUsername();

        if(currentUser!=null)
        {
            if(count>0)
            {

                Bitmap bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                ParseFile file=new ParseFile(imageInByte);
                currentUser.put("profilePicture",file);

            }
            if(!uname.equals(list.get(0)))
            {
                currentUser.put("username",uname);

            }
            if(!bio1.equals(list.get(1)))
            {

                currentUser.put("bio",bio1);
            }
            if(!fullName.equals(list.get(2)))
            {
                currentUser.put("fullName",fullName);
            }
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        Toast.makeText(getApplicationContext(),"Changes are saved", Toast.LENGTH_LONG).show();
                        fetchData();


                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(),"Can't save the changes! "+e, Toast.LENGTH_LONG).show();
                    }
                }
            });




        }
        else
        {
            Toast.makeText(getApplicationContext(),"Can't fetch current user", Toast.LENGTH_LONG).show();

        }



    }

    private void fetchData()
    {
        list=new ArrayList<>();
        ParseQuery<ParseUser> query =ParseUser.getQuery();
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());

        query.findInBackground((users, e)-> {
            if (e == null)
            {

                for(ParseUser user1:users)
                {
                    list.add(user1.get("username").toString());
                    list.add(user1.get("bio").toString());
                    list.add(user1.get("fullName").toString());
                    img=user1.getParseFile("profilePicture");


                }
                String url=img.getUrl();

                Picasso.with(this).load(url).into(profilePicture);



                username.setText(list.get(0));

                bio.setText(list.get(1));
                name.setText(list.get(2));


            }
            else
            {
                Toast.makeText(getApplicationContext(),"can't fetch the data: "+e, Toast.LENGTH_LONG).show();
            }


        });


    }


    private void showAlert(String title, String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        }).show();
    }

    private void showAlertYesNo(String title, String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveChange(profilePicture);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();
    }


}