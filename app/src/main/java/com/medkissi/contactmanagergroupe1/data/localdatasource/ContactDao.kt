package com.medkissi.contactmanagergroupe1.data.localdatasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.medkissi.contactmanagergroupe1.data.model.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table")
    fun getAllContacts(): LiveData<List<Contact>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
    @Update
    suspend fun updateContact(contact: Contact)
}