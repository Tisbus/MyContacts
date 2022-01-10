package com.example.mycontacts.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 2, exportSchema = false)
public abstract class ContactDataBase extends RoomDatabase {

    private final static String DB_NAME = "contacts";
    private final static Object LOCK = new Object();
    private static ContactDataBase contactDataBase;
    public static ContactDataBase getInstance(Context context){
        synchronized (LOCK) {
            if (contactDataBase == null) {
                contactDataBase = Room.databaseBuilder(context.getApplicationContext(), ContactDataBase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return contactDataBase;
    }

    public abstract ContactDao contactDao();
}
