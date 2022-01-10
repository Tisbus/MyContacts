package com.example.mycontacts.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontacts.R;
import com.example.mycontacts.adapter.ContactAdapter;
import com.example.mycontacts.databinding.ActivityMainBinding;
import com.example.mycontacts.model.Contact;
import com.example.mycontacts.view_model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding mB;
    private ContactViewModel viewModel;
    private ContactAdapter adapter;
    private List<Contact> listContact;
    FloatButtonHandler floatButtonHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.activity_main);*/
        mB = DataBindingUtil.setContentView(this, R.layout.activity_main);
        floatButtonHandler = new FloatButtonHandler(this);
        mB.setMainA(floatButtonHandler);
        listContact = new ArrayList<>();
/*        mB.floatAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContact(false, null, -1);
            }
        });*/
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);

        adapter = new ContactAdapter(listContact, MainActivity.this, this);
        getData();
        mB.recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        mB.recyclerViewContacts.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (listContact.size() > 0) {
                    Contact contact = listContact.get(viewHolder.getAdapterPosition());
                    viewModel.delete(contact);
                }
            }
        }).attachToRecyclerView(mB.recyclerViewContacts);
    }

    public class FloatButtonHandler{

        Context context;

        public FloatButtonHandler(Context context) {
            this.context = context;
        }

        public void onClick(View view){
            addAndEditContact(false, null, -1);
        }
    }

    private void getData() {
        LiveData<List<Contact>> getAllContacts = viewModel.getAllLiveContacts();
        getAllContacts.observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContactList(contacts);
            }
        });
    }


    public void addAndEditContact(final boolean isUpdate, final Contact contact, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.add_contact, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView newContactTitle = view.findViewById(R.id.textViewTitle);
        final EditText firstNameEditText = view.findViewById(R.id.editTextFirstName);
        final EditText lastNameEditText = view.findViewById(R.id.editTextLastName);
        final EditText emailEditText = view.findViewById(R.id.editTextEmail);
        final EditText phoneEditText = view.findViewById(R.id.editTextPhone);

        newContactTitle.setText(!isUpdate ? "Add Contact" : "Edit Contact");

        if (isUpdate && contact != null) {
            lastNameEditText.setText(contact.getLastName());
            firstNameEditText.setText(contact.getFirstName());
            emailEditText.setText(contact.getEmail());
            phoneEditText.setText(contact.getPhone());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(isUpdate ? "Delete" : "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {
                                    viewModel.delete(contact);
                                } else {
                                    dialogBox.cancel();

                                }

                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstNameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter contact first name!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(lastNameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter contact last name!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(emailEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter email!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter phone!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && contact != null) {
                    contact.setLastName(lastNameEditText.getText().toString());
                    contact.setFirstName(firstNameEditText.getText().toString());
                    contact.setEmail(emailEditText.getText().toString());
                    contact.setPhone(phoneEditText.getText().toString());
                    viewModel.update(contact);
                } else {
                    Contact contact = new Contact(0, lastNameEditText.getText().toString(),
                            firstNameEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            phoneEditText.getText().toString());
                    viewModel.insert(contact);
                }
            }
        });
    }

}