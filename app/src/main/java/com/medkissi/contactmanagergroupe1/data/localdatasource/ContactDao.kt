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
    @Query("SELECT * FROM contact_table WHERE nom_table LIKE '%' || :query || '%' OR telephone LIKE '%' || :query || '%'")
    suspend fun getDaoSearch(query: String): List<Contact>
    @Delete
    suspend fun deleteContact(contact: Contact)
    @Query("DELETE FROM contact_table")
    suspend fun deleteAllContact()

    @Update
    suspend fun updateContact(contact: Contact)



}