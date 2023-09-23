package com.medkissi.contactmanagergroupe1.data.repository

import androidx.lifecycle.LiveData
import com.medkissi.contactmanagergroupe1.ContactApp
import com.medkissi.contactmanagergroupe1.data.localdatasource.ContactDatabase
import com.medkissi.contactmanagergroupe1.data.model.Contact

class ContactRepository {
    private val dao= ContactDatabase.getDatabaseInstance(ContactApp.getAppContext()).doa()

    fun getContacts():LiveData<List<Contact>>{
        return dao.getAllContacts()
    }

    suspend fun insertContact(contact:Contact){
        dao.insertContact(contact)
    }

    suspend fun  deleteContact(contact:Contact){
        dao.deleteContact(contact)
    }
    suspend fun  updateContact(contact:Contact){
        dao.updateContact(contact)
    }

}