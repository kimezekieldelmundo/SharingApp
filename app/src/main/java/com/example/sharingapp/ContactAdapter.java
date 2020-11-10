package com.example.sharingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Item Adapter is responsible for what information is displayed in ListView entries.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater inflater;
    private Context context;

    public ContactAdapter(Context context, ArrayList<Contact> items, Fragment fragment) {
        super(context, 0, items);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getItem(position) gets the "item" at "position" in the "items" ArrayList
        // (where "items" is a parameter in the ItemAdapter creator as seen above ^^)
        // Note: getItem() is not a user-defined method in the Item/ItemList class!
        // The "Item" in the method name is a coincidence...
        Contact contact = getItem(position);

        String username = "Title: " + contact.getUsername();
        String email = "Description: " + contact.getEmail();

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.itemlist_item, parent, false);
        }

        TextView title_tv = (TextView) convertView.findViewById(R.id.title_tv);
        TextView description_tv = (TextView) convertView.findViewById(R.id.description_tv);

        title_tv.setText(username);
        description_tv.setText(email);

        return convertView;
    }
}
