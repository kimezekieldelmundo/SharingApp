package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddContactActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private ContactList contact_list = new ContactList();
    private Context context;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        context = getApplicationContext();
        contact_list.loadContacts(context);
    }

    public void saveContact(View view){
        String username_input = username.getText().toString();
        String email_input = email.getText().toString();

        if (username_input.equals("")){
            username.setError("Empty username");
            return;
        }
        if (email_input.equals("")){
            email.setError("Empty email");
            return;
        }
        Contact contact = new Contact(username_input, email_input);
        contact_list.addContact(contact);
        contact_list.saveContacts(context);
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
