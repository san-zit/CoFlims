package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class TermsAndConditions extends AppCompatActivity {

    ImageButton backArrow;
    EditText terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        backArrow=findViewById(R.id.backArrowTandC);
        terms=findViewById(R.id.termsAndConditionsBox);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TermsAndConditions.this, signUp.class);
                startActivity(intent);

            }
        });
        String termsandconditions="Terms and Conditions agreements act as a legal contract between you (the company) who has the website or mobile app and the user who access your website and mobile app.\n" +
                "\n" +
                "Having a Terms and Conditions agreement is completely optional. No laws require you to have one. Not even the super-strict and wide-reaching General Data Protection Regulation (GDPR).\n" +
                "\n" +
                "It's up to you to set the rules and guidelines that the user must agree to. You can think of your Terms and Conditions agreement as the legal agreement where you maintain your rights to " +
                "exclude users from your app in the event that they abuse your app, where you maintain your legal rights against potential app abusers, and so on.\n" +
                "\n" +
                "Terms and Conditions are also known as Terms of Service or Terms of Use.";
        terms.setText(termsandconditions);


    }
}