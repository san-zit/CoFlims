package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        usernameEditText=findViewById(R.id.editTextUsername);
        passwordEditText=findViewById(R.id.editTextPassword);
        loginButton=findViewById(R.id.buttonSignin);
        signupButton=findViewById(R.id.buttonSignIn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! usernameEditText.getText().toString().isEmpty() && ! passwordEditText.getText().toString().isEmpty())
                {
                    ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null){

                                Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_LONG).show();
                                startUserActivity();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(! usernameEditText.getText().toString().isEmpty() && ! passwordEditText.getText().toString().isEmpty())
                {
                    ParseUser user= new ParseUser();


                    user.setUsername(usernameEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null)
                            {
                                Toast.makeText(getApplicationContext(),"signup successfull",Toast.LENGTH_LONG).show();
                                startUserActivity();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }

            }
        });

//        ParseObject firstObject = new ParseObject("FirstClass");
//        firstObject.put("message","Hey ! First message from android. Parse is now connected");
//        firstObject.saveInBackground(e -> {
//            if (e != null){
//                Log.e("MainActivity", e.getLocalizedMessage());
//            }else{
//                Log.d("MainActivity","Object saved.");
//            }
//        });
    }

    private void startUserActivity()
    {
        Intent intent=new Intent(MainActivity.this, Homepage.class);
        startActivity(intent);



    }
}