package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class manageAccountsSettings extends AppCompatActivity {
    //TextView verifyEmail;
    TextView verifyEmailAready;
    TextView deleteAccount;
    EditText phone;
    EditText email;
    Button save;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts_settings);


        //verifyEmail=findViewById(R.id.verifyEmail);
        verifyEmailAready=findViewById(R.id.verifiedEmail);
        deleteAccount=findViewById(R.id.deleteMyAccount);
        phone=findViewById(R.id.phoneNumber);
        email=findViewById(R.id.emailAddress);
        save=findViewById(R.id.buttonSaveChanges);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";//email verification

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertYesNoDelete("Delete account Permanently","Are you sure?");

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(emailPattern.matches(email.getText().toString()) || !phone.getText().toString().equals(""))
                {
                    showAlertYesNo("Save chages","Do you want to save changes?");
                    verifyEmailAready.setVisibility(View.VISIBLE);

                }

                else
                {

                    Toast.makeText(getApplicationContext(),"Invalid details", Toast.LENGTH_LONG).show();
                }





            }
        });

        fetchData();






    }

    private void saveChanges()
    {
        String ph;
        String em;


        ph=phone.getText().toString();
        em=email.getText().toString();

        ParseUser currentUser=ParseUser.getCurrentUser();
        if(currentUser!=null)
        {
            if(!em.equals(list.get(0)))
            {

                currentUser.put("email",em);

            }
            if(!ph.equals(list.get(1)))
            {

                currentUser.put("phoneNo",ph);
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
                    list.add(user1.get("email").toString());
                    list.add(user1.get("phoneNo").toString());



                }




                email.setText(list.get(0));

                phone.setText(list.get(1));



            }
            else
            {
                Toast.makeText(getApplicationContext(),"can't fetch the data: "+e, Toast.LENGTH_LONG).show();
            }


        });


    }

    private void deleteAccount()
    {
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            // Deletes the user.
            // Notice that the DeleteCallback is totally optional!
            currentUser.deleteInBackground(e -> {
                if(e==null){
                    //Delete successfull
                    Toast.makeText(this, "User was deleted", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(manageAccountsSettings.this,Homepage.class);
                    startActivity(intent);
                }else{
                    // Something went wrong while deleting
                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        deleteFromFollowTable();




    }

    private void deleteFromFollowTable()
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
                        results.get(i).deleteInBackground();

                    }

                }
                else
                {
                    // flag=false;
                    Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }



            }
        });

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Following");
        query.whereEqualTo("uname",ParseUser.getCurrentUser().getUsername());
        //query.whereEqualTo("followedby", ParseUser.getCurrentUser().getUsername());




        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> results, ParseException e) {
                if(e==null)
                {
                    for(int i=0;i<results.size();i++)
                    {
                        results.get(i).deleteInBackground();

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
                saveChanges();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();
    }
    private void showAlertYesNoDelete(String title, String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               deleteAccount();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();
    }
}