package com.medkissi.contactmanagergroupe1.data.model

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo("nom_table") val nomComplet: String,
    val telephone: String,
    val email: String

) : Serializable
