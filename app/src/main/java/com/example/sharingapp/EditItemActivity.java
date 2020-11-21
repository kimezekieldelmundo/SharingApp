package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Editing a pre-existing item consists of deleting the old item and adding a new item with the old
 * item's id.
 */
public class EditItemActivity extends AppCompatActivity {

    private ItemList item_list = new ItemList();
    private Item item;
    private Context context;

    private Bitmap image;
    private int REQUEST_CODE = 1;
    private ImageView photo;

    private EditText title;
    private EditText maker;
    private EditText description;
    private EditText length;
    private EditText width;
    private EditText height;
    private TextView  borrower_tv;
    private Switch status;
    private Spinner borrower_spinner;
    private EditText invisible;
    private ContactList contact_list = new ContactList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        context = getApplicationContext();
//        data loading
        Intent intent = getIntent(); // Get intent from ItemsFragment
        int pos = intent.getIntExtra("position", 0);
        item_list.loadItems(context);
        item = item_list.getItem(pos);
        Dimensions dimensions = item.getDimensions();
        String status_str = item.getStatus();
        image = item.getImage();

        contact_list.loadContacts(context);
        Contact borrowerContact = item.getBorrower();

//      form fields
        title = (EditText) findViewById(R.id.title);
        maker = (EditText) findViewById(R.id.maker);
        description = (EditText) findViewById(R.id.description);
        length = (EditText) findViewById(R.id.length);
        width = (EditText) findViewById(R.id.width);
        height = (EditText) findViewById(R.id.height);
        borrower_tv = (TextView) findViewById(R.id.borrower_tv);
        photo = (ImageView) findViewById(R.id.image_view);
        status = (Switch) findViewById(R.id.available_switch);

        borrower_spinner = (Spinner) findViewById(R.id.borrower_spinner);
        ArrayAdapter<String> borrowerSprinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, contact_list.getAllUsernames());
        borrowerSprinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        borrower_spinner.setAdapter(borrowerSprinnerArrayAdapter);
        borrower_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String)adapterView.getItemAtPosition(i);
                Contact contact = contact_list.getContactByUsername(selected);
                item.setBorrower(contact);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//      set form fields value
        title.setText(item.getTitle());
        maker.setText(item.getMaker());
        description.setText(item.getDescription());
        length.setText(dimensions.getLength());
        width.setText(dimensions.getWidth());
        height.setText(dimensions.getHeight());

        if (status_str.equals("Borrowed")) {
            status.setChecked(false);
            if (borrowerContact != null){
                borrower_spinner.setSelection(borrowerSprinnerArrayAdapter.getPosition(borrowerContact.getUsername()));
            }
        } else {
            borrower_tv.setVisibility(View.GONE);
            borrower_spinner.setVisibility(View.GONE);
        }

        if (image != null) {
            photo.setImageBitmap(image);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void deletePhoto(View view) {
        image = null;
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent){
        if (request_code == REQUEST_CODE && result_code == RESULT_OK){
            Bundle extras = intent.getExtras();
            image = (Bitmap) extras.get("data");
            photo.setImageBitmap(image);
        }
    }

    public void deleteItem(View view) {
        item_list.deleteItem(item);
        item_list.saveItems(context);

        // End EditItemActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveItem(View view) {

        String title_str = title.getText().toString();
        String maker_str = maker.getText().toString();
        String description_str = description.getText().toString();
        String length_str = length.getText().toString();
        String width_str = width.getText().toString();
        String height_str = height.getText().toString();
//        String borrower_str = borrower.getText().toString();
        Contact borrowerObj = contact_list.getContactByUsername((String) borrower_spinner.getSelectedItem());
        Dimensions dimensions = new Dimensions(length_str, width_str, height_str);

        if (title_str.equals("")) {
            title.setError("Empty field!");
            return;
        }

        if (maker_str.equals("")) {
            maker.setError("Empty field!");
            return;
        }

        if (description_str.equals("")) {
            description.setError("Empty field!");
            return;
        }

        if (length_str.equals("")) {
            length.setError("Empty field!");
            return;
        }

        if (width_str.equals("")) {
            width.setError("Empty field!");
            return;
        }

        if (height_str.equals("")) {
            height.setError("Empty field!");
            return;
        }

//        if (borrowerObj.equals(null) && !status.isChecked()) {
//            borrower.setError("Empty field!");
//            return;
//        }

        // Reuse the item id
        String id = item.getId();
        item_list.deleteItem(item);

        Item updated_item = new Item(title_str, maker_str, description_str, dimensions, image, id);

        boolean checked = status.isChecked();
        if (!checked) {
            updated_item.setStatus("Borrowed");
            updated_item.setBorrower(borrowerObj);
        }
        item_list.addItem(updated_item);

        item_list.saveItems(context);

        // End EditItemActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Checked = Available
     * Unchecked = Borrowed
     */
    public void toggleSwitch(View view){
        if (status.isChecked()) {
            // Means was previously borrowed
            borrower_spinner.setVisibility(View.GONE);
            borrower_tv.setVisibility(View.GONE);
            item.setBorrower(null);
            item.setStatus("Available");

        } else {
            // Means was previously available
            borrower_spinner.setVisibility(View.VISIBLE);
            borrower_tv.setVisibility(View.VISIBLE);
        }
    }
}