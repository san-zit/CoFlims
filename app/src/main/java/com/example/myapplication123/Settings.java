package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    ListView settings;
    ImageButton backArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings=findViewById(R.id.lvSettings);
        backArrow=findViewById(R.id.backArrowSettings);

        ArrayList<String> menuItems=new ArrayList<>();
        menuItems.add("Manage Accounts");
        menuItems.add("Security and Passcode");
        menuItems.add("Privacy");
        menuItems.add("Share Profile");
        menuItems.add("Notifications");
        menuItems.add("Content");
        menuItems.add("Sign Out");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,menuItems);
        settings.setAdapter(adapter);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.this, personalProfile.class);
                startActivity(intent);
            }
        });
        settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                //Toast.makeText(getApplicationContext(),menuItems.get(position)+position, Toast.LENGTH_LONG).show();
                //String index=menuItems.get(position);

                if(position==0)
                {

                    //load manage accounts
                    Intent intent=new Intent(Settings.this,manageAccountsSettings.class);
                    startActivity(intent);
                }
                else if(position==1)
                {
                    Intent intent=new Intent(Settings.this,securityAndPasscodeSettings.class);
                    startActivity(intent);

                }
                else if(position==2)
                {
                    Intent intent=new Intent(Settings.this,privacySettings.class);
                    startActivity(intent);
                }
                else if(position==3)
                {
                    showAlert( "This feature is under maintenance! Sorry for inconvinence!");
                }
                else if(position==4)
                {
                    Intent intent=new Intent(Settings.this,NotificationSettings.class);
                    startActivity(intent);

                }
                else if(position==5)
                {
                    showAlert( "Content will comming soon.. code pending..");
                }
                else if(position==6)
                {
                    ParseUser.logOut();
                    Intent intent=new Intent(Settings.this,signIn.class);
                    startActivity(intent);


                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid selection", Toast.LENGTH_LONG).show();
                    }
            }
        });



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
    private void showAlertYesNo(String title, String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ParseUser.logOut();
                Intent intent=new Intent(Settings.this,signIn.class);
                startActivity(intent);



            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();
    }
}