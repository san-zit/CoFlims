package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class UserActivity extends AppCompatActivity {
    Button logOutButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        logOutButton=findViewById(R.id.logoutButton);
        textView=findViewById(R.id.welcomeTextView);

        String welcomeMessage="Welcome"+ ParseUser.getCurrentUser().getUsername();
        textView.setText(welcomeMessage);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent=new Intent(UserActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}