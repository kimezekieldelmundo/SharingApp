package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsActivity  extends AppCompatActivity {
    private ContactList contact_list = new ContactList();
    private ContactList active_borrowers_list;
    private ItemList item_list;
    private ListView my_contacts = null;
    private ArrayAdapter<Contact> adapter;
    private Context context;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        context = getBaseContext();
        contact_list.loadContacts(context);
        my_contacts = (ListView)  findViewById(R.id.contacts_list_view);
        adapter = new ContactAdapter(this, contact_list.getContacts());
        my_contacts.setAdapter(adapter);
        my_contacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact selectedContact = (Contact)  adapterView.getItemAtPosition(i);
                Intent intent = new Intent(context, EditContactActivity.class);
                intent.putExtra("contact", contact_list.getIndex(selectedContact));
                intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addContactActivity(View view){
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
