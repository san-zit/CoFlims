package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseInstallation;

public class Homepage extends AppCompatActivity {

    Button signin;
    TextView signup;
    ImageButton facebookLogin;
    ImageButton googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        signin=findViewById(R.id.buttonSignIn);
        signup=findViewById(R.id.signUpTextView);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, signIn.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, signUp.class);
                startActivity(intent);
            }
        });





    }

}