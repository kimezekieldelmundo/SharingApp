package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditContactActivity  extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private ContactList contact_list = new ContactList();
    private Contact contact;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        context = getApplicationContext();
        contact_list.loadContacts(context);
        Button delete_button = (Button) findViewById(R.id.delete_button);
        Button save_button = (Button) findViewById(R.id.save_button);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("contact",0);
        contact = contact_list.getContact(pos);
        ItemList itemList = new ItemList();
        itemList.loadItems(context);
        ArrayList<Contact> borrowerContacts = itemList.getActiveBorrowers();
        boolean canEdit = true;
        for(Contact i: borrowerContacts){
            if(i.getId().equals(contact.getId())){
                canEdit = false;
                break;
            }
        }
        username.setText(contact.getUsername());
        email.setText(contact.getEmail());
        if (!canEdit){
            username.setEnabled(false);
            email.setEnabled(false);
            delete_button.setEnabled(false);
            save_button.setEnabled(false);
        }
    }

    public void saveContact(View view){
        String username_str = username.getText().toString();
        String email_str = email.getText().toString();
        String id = contact.getId();
        if (username_str.equals("")){
            username.setError("Empty username");
            return;
        }

        if(
                !contact.getUsername().equals(username_str) /*username changed*/
                &&
                !contact_list.isUsernameAvailable(username_str) /*username unavailable*/
        ){
            username.setError("Username already taken");
            return;
        }

        if (email_str.equals("")){
            email.setError("Empty email");
            return;
        }
        // delete old contact
        contact_list.deleteContact(contact);

        // add new contact with same id but updated username and email
        Contact newContact = new Contact(username_str, email_str);
        newContact.updateId(id);
        contact_list.addContact(newContact);
        contact_list.saveContacts(context);
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void deleteContact(View view){
        contact_list.deleteContact(contact);
        contact_list.saveContacts(context);
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}

