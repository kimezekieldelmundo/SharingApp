package com.example.sharingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddContactActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private ContactList contact_list;
    private Context context;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        context = getApplicationContext();
        contact_list.loadContacts(context);
    }

    public void saveContact(View view){

    }
}
