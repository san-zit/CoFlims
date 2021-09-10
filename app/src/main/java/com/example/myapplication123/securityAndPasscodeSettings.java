package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class securityAndPasscodeSettings extends AppCompatActivity {

    //EditText oldpsw;
    EditText newpsw1;
    EditText newpsw2;
    Button save;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_and_passcode_settings);

        //oldpsw=findViewById(R.id.oldpassword);
        newpsw1=findViewById(R.id.newpassword1);
        newpsw2=findViewById(R.id.newpassword2);
        save=findViewById(R.id.buttonSaveChanges);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call save change function
                //chagePassword();
                if(newpsw1.getText().toString().equals(newpsw2.getText().toString()) ||!newpsw1.getText().toString().isEmpty())
                {
                    savePsw();
                    //showAlert("Success","Password reset completed!");
                }
                else
                {

                    showAlert("Error","Password didn't match");
                }


            }
        });



    }


    private void savePsw()
    {
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.setPassword(newpsw1.getText().toString());
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null)
                {
                    showAlert("Success","Password reset completed!");
                }
                else
                {
                    showAlert("Error","Password reset failed!"+e.getMessage());
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

}