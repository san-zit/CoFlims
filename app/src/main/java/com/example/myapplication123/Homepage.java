package com.example.myapplication123;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.facebook.ParseFacebookUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

public class Homepage extends AppCompatActivity {

    Button signin;
    TextView signup;
    ImageButton facebookLogin;
    ImageButton googleLogin;
    ImageButton imageButtonMessage;
    ImageButton user;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        signin=findViewById(R.id.buttonSignIn);
        signup=findViewById(R.id.signUpTextView);
        //user=findViewById(R.id.imageButtonUser);
        facebookLogin=findViewById(R.id.imageButtonFaceboookLogo);
        //imageButtonMessage=findViewById(R.id.imageButtonMessage);
        //search=findViewById(R.id.imageButtonSearch);

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Homepage.this, userlist.class);
//                startActivity(intent);
//            }
//        });

//        imageButtonMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Homepage.this, Inbox.class);
//                startActivity(intent);
//            }
//        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection<String> permissions= Arrays.asList("public_profile","email");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(Homepage.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e!=null)
                        {
                            ParseUser.logOut();
                            Log.e("error","error",e);

                        }
                        if(user==null)
                        {

                            ParseUser.logOut();
                            Toast.makeText(Homepage.this,"The user cancelled the facebook login",Toast.LENGTH_LONG).show();
                        }
                        else if (user.isNew())
                        {
                            Toast.makeText(Homepage.this,"The user signed up and logged in through facebook",Toast.LENGTH_LONG).show();
                            getUserDetailsFromFacebook();


                        }
                        else
                        {
                            Toast.makeText(Homepage.this,"The user logged in through facebook.",Toast.LENGTH_LONG).show();
                            getUserDetailsFromParse();


                        }
                    }
                });
            }
        });

//        user.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Homepage.this, Editprofile.class);
//                startActivity(intent);
//            }
//        });

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

    private void getUserDetailsFromParse() {


        ParseUser user=ParseUser.getCurrentUser();
        String title="Welcome back";
        String message="User: "+user.getUsername()+"Login Email: "+user.getEmail();
        showAlert(title,message);

    }

    private void getUserDetailsFromFacebook() {
        GraphRequest request=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                ParseUser user=ParseUser.getCurrentUser();
                try {
                    user.setUsername(object.getString("name"));

                }
                catch (JSONException e){

                    e.printStackTrace();
                }
                try {
                    user.setEmail(object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        showAlert("First time login","Welcome");
                    }
                });
            }
        });
        Bundle parameters=new Bundle();
        parameters.putString("fields","name,eamail");
        request.setParameters(parameters);
        request.executeAsync();


    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent intent= new Intent(Homepage.this,personalProfile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog ok=builder.create();
        ok.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode,resultCode,data);
    }
}