package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class signUp extends AppCompatActivity {
    EditText email;
    EditText userName;
    EditText password;
    ImageButton viewPsw;
    TextView signin;
    Button signup;
    int count=1;


    ImageButton backArrowSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ParseInstallation.getCurrentInstallation().saveInBackground();//connection with back4app


        backArrowSignUp=findViewById(R.id.backArrowSignUp);
        signup=findViewById(R.id.buttonSignUp);
        viewPsw=findViewById(R.id.viewPasswordImageButton);
        password=findViewById(R.id.editTextPassword);
        signin=findViewById(R.id.textViewSignIn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signUp.this, signIn.class);
                startActivity(intent);
            }
        });

        backArrowSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signUp.this, Homepage.class);
                startActivity(intent);
            }
        });

        viewPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               password.setInputType(144);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });




    }

    private boolean isVerified()
    {

        email=findViewById(R.id.editTextEmail);
        userName=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);



        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailID=email.getText().toString().trim();
        if(emailID.matches(emailPattern) )
        {
         if(!userName.getText().toString().trim().isEmpty())
         {
             if(!password.getText().toString().trim().isEmpty() && password.getText().toString().trim().length()>=6)
             {
                 return true;
             }
             else {
                 password.setError("Invalid password! Must be 6 characters");
                 return false;
             }


         }
         else {

             userName.setError("Please enter the username!");
             return false;
         }

        }
        else
        {

            email.setError("Invalid Email address!");
            return false;
        }



    }

    private void signup()
    {
        if(isVerified())
        {

            ParseUser user=new ParseUser();


            user.setEmail(email.getText().toString());
            user.setUsername(userName.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(),"Signup successful!", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(),e.getMessage()+"Signup failed!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Signup failed!", Toast.LENGTH_LONG).show();

        }

    }





}