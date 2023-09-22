package com.medkissi.contactmanagergroupe1.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.adapters.ContactAdapter
import com.medkissi.contactmanagergroupe1.adapters.OnContactClickListner
import com.medkissi.contactmanagergroupe1.data.model.Contact
import com.medkissi.contactmanagergroupe1.viewmodels.ContactViewModel


class MainActivity : AppCompatActivity(), OnContactClickListner {
    val viewModel:ContactViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ContactAdapter(this )
        val rv = findViewById<RecyclerView>(R.id.rv)
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter =adapter
        viewModel.getData().observe(this){ contacts->
            adapter.submitList(contacts)

        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {
                val intent = Intent(this, AddEditActivity::class.java)

                // startActivity for result permet demarrer une activite et d'attendre un resultat

                startActivityForResult(intent,2)
            }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2 && resultCode == RESULT_OK){
            val contact = data?.getSerializableExtra(CONTACT_KEY) as Contact
            viewModel.insertContact(contact)

        }    }

    override fun onItemClick(contact: Contact) {

    }

    override fun onDeleteClick(contact: Contact) {

    }

    override fun onUpdateClick(contact: Contact) {

    }
}