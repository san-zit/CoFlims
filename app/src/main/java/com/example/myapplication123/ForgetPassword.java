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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgetPassword extends AppCompatActivity {

    Button pswRestButton;
    EditText pswResetEditText;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        pswRestButton=findViewById(R.id.passwordRestButton);
        pswResetEditText=findViewById(R.id.editTextPasswordResetEmail);
        backArrow=findViewById(R.id.backArrowResetPsw);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgetPassword.this, signIn.class);
                startActivity(intent);

            }
        });



        pswRestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordRest(pswResetEditText.getText().toString());
            }
        });



    }

    private boolean isVerified()
    {
        pswResetEditText=findViewById(R.id.editTextPasswordResetEmail);
        String email=pswResetEditText.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.matches(emailPattern))
        {

            return true;
        }
        else {

            return false;
        }



    }

    private void passwordRest(String email)
    {

        if(isVerified())
        {
            ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {
                        showAlert("Reset Password Success"," If you have a valid account, a reset email has been sent successfully.");
                        Intent intent=new Intent(ForgetPassword.this, signIn.class);
                        startActivity(intent);
                    } else {
                        showAlert("Password Reset Failed","Please check your email and try again!"+e);

                    }
                }
            });
        }
        else
            {
            showAlert("Invalid Email", "Please enter a valid email address");

        }



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
}