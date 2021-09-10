package com.example.myapplication123;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class SentNewMessage extends AppCompatActivity {

    ImageView backArrow;
    Button send;
    EditText receiver;
    EditText message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_new_message);

        backArrow=findViewById(R.id.backArrowNewMessage);
        send=findViewById(R.id.buttonSend);
        receiver=findViewById(R.id.receiver);
        message=findViewById(R.id.messageBox);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SentNewMessage.this, Inbox.class);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertYesNo( "New message",  "Send Message?");
            }
        });







    }

    public void sendMessage()
    {

        if(!receiver.getText().toString().equals("")|| !message.getText().toString().equals(""))
        {
            ParseObject entity = new ParseObject("Inbox");

            entity.put("sender", ParseUser.getCurrentUser().getUsername());
            entity.put("receiver", receiver.getText().toString());
            entity.put("message", message.getText().toString());

            // Saves the new object.
            // Notice that the SaveCallback is totally optional!
            entity.saveInBackground(e -> {
                if (e==null){
                    Toast.makeText(this, "Message send", Toast.LENGTH_SHORT).show();
                }else{
                    //Something went wrong
                    Toast.makeText(this, "Message sending failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            Toast.makeText(this, "Fileds can't be blank", Toast.LENGTH_SHORT).show();

        }






    }

    private void showAlertYesNo(String title, String message1) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message1).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendMessage();
                receiver.setText("");
                message.setText("");



            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).show();

    }


}

