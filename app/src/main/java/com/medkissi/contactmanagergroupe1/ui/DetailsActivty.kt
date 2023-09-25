package com.medkissi.contactmanagergroupe1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.model.Contact
import com.medkissi.contactmanagergroupe1.viewmodels.ContactViewModel

class DetailsActivty : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_activty)
        val nom = findViewById<TextView>(R.id.textview_nom)
        val tel  = findViewById<TextView>(R.id.textView_tel)
        val email = findViewById<TextView>(R.id.textView_email)
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        val data = intent.getSerializableExtra(CONTACT_TO_SAVE) as Contact

        nom.text = data.nomComplet
        tel.text = data.telephone
        email.text = data.email


    }
}