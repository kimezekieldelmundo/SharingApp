package com.example.sharingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private ContactList contact_list;
    private Context context;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void saveContact(View view){

    }
}