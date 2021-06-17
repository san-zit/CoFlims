package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class signIn extends AppCompatActivity {

    ImageButton backArrow;
    EditText username;
    EditText password;
    ImageButton viewpsw;

    Button signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        backArrow=findViewById(R.id.backArrowSignIn);
        signin=findViewById(R.id.signinButton);
        viewpsw=findViewById(R.id.viewPasswordSigninImageButton);
        password=findViewById(R.id.editTextPassword);

        viewpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setInputType(144);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signIn.this, Homepage.class);
                startActivity(intent);
            }
        });


    }

    private boolean checkDetails()
    {
        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);

        if(username.getText().toString().isEmpty())
        {
            username.setError("Please enter the username!");
            return false;
        }
        else
        {

            if(password.getText().toString().isEmpty() && password.getText().toString().trim().length()<6)
            {

                password.setError("Invalid password! Must be 6 characters");
                return false;
            }
            else
            {
                return true;
            }
        }



    }

    public void login()
    {
        if(checkDetails())
        {
            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null){

                        Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
        else {

            Toast.makeText(getApplicationContext(),"Login failed!", Toast.LENGTH_LONG).show();
        }
    }
}