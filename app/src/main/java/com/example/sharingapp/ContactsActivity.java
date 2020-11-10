package com.example.sharingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsActivity  extends AppCompatActivity {
    private ContactList contact_list;
    private ContactList active_borrowers_list;
    private ItemList item_list;
    private ListView my_contacts;
    private ArrayAdapter<Contact> adapter;
    private Context context;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void AddContactActivity(View view){

    }
}
