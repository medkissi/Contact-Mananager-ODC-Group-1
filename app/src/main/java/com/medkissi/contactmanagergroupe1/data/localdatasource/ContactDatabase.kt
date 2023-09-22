package com.medkissi.contactmanagergroupe1.data.localdatasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.medkissi.contactmanagergroupe1.data.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun doa(): ContactDao

    companion object {
        var INSTANCE: ContactDatabase? = null

        fun getDatabaseInstance(context: Context): ContactDatabase {
            var instance = INSTANCE
            if (instance == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    instance = INSTANCE

                }

            }

            return  INSTANCE!!

        }

    }


}