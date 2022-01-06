package com.example.mycontacts.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    public void insert(Contact contact);

    @Query("SELECT * FROM contacts")
    LiveData<List<Contact>> getLiveContacts();

    @Update
    public void update(Contact contact);

    @Delete
    public void delete(Contact contact);

}
