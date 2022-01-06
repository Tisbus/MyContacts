package com.example.mycontacts.view_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.mycontacts.model.Contact;
import com.example.mycontacts.model.ContactDataBase;

import java.util.List;


public class ContactViewModel extends AndroidViewModel {
    private LiveData<List<Contact>> allLiveContacts;
    private ContactDataBase dataBase;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        dataBase = ContactDataBase.getInstance(getApplication());
        allLiveContacts = dataBase.contactDao().getLiveContacts();
    }

    public LiveData<List<Contact>> getAllLiveContacts() {
        return allLiveContacts;
    }

    public void insert(Contact contact){new InsertTask().execute(contact);}
    public void update(Contact contact){new UpdateTask().execute(contact);}
    public void delete(Contact contact){new DeleteTask().execute(contact);}

    public class InsertTask extends AsyncTask<Contact, Void, Void>{
        @Override
        protected Void doInBackground(Contact... contacts) {
            if(contacts != null && contacts.length > 0){
                dataBase.contactDao().insert(contacts[0]);
            }
            return null;
        }
    }

    public class UpdateTask extends AsyncTask<Contact, Void, Void>{
        @Override
        protected Void doInBackground(Contact... contacts) {
            if(contacts != null && contacts.length > 0){
                dataBase.contactDao().update(contacts[0]);
            }
            return null;
        }
    }

    public class DeleteTask extends AsyncTask<Contact, Void, Void>{
        @Override
        protected Void doInBackground(Contact... contacts) {
            if(contacts != null && contacts.length > 0){
                dataBase.contactDao().delete(contacts[0]);
            }
            return null;
        }
    }
}
