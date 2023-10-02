package com.medkissi.contactmanagergroupe1.ui

import ContactViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.adapters.ContactAdapter
import com.medkissi.contactmanagergroupe1.adapters.OnContactClickListner
import com.medkissi.contactmanagergroupe1.data.model.Contact

const val CONTACT_TO_SAVE = "contact"
const val UPDATED_CONTACT = "contact1"
const val CONTACT_TO_UPDATE ="contact2"

class MainActivity : AppCompatActivity(), OnContactClickListner {
    val viewModel: ContactViewModel by viewModels()
    private val PERMISSION_REQUEST_CODE = 123

    private val isRecordSaved = false

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ContactAdapter(this)
        val rv = findViewById<RecyclerView>(R.id.rv)
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.adapter = adapter

        requestStoragePermission()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {
                val intent = Intent(this, AddEditActivity::class.java)
                // startActivity for result permet demarrer une activite et d'attendre un resultat
                startActivityForResult(intent, 2)
            }

        viewModel.getData().observe(this) { contacts ->
            adapter.submitList(contacts)
        }

        // pour le bouton rechercher
        val research = findViewById<ImageView>(R.id.btnSearch)
            .setOnClickListener {
                val int = Intent(this, Research::class.java)
                startActivity(int)
            }

        /* Delete All contact */
        val deleteAll = findViewById<ImageView>(R.id.btnDeleteAll)
            deleteAll.setOnClickListener {
                showConfirmationDialog()
            }
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // L'utilisateur a accordé la permission, vous pouvez effectuer des opérations nécessitant la permission ici.
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == RESULT_OK) {
            val contact = data?.getSerializableExtra(CONTACT_TO_SAVE) as Contact
            viewModel.insertContact(contact)
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            val contact1 = data?.getSerializableExtra(UPDATED_CONTACT) as Contact
            viewModel.updateContact(contact1)
        }
    }

    override fun onItemClick(contact: Contact) {
        val intent = Intent(this, DetailsActivty::class.java)
        intent.putExtra(CONTACT_TO_SAVE, contact)
        startActivity(intent)
    }

    // boite de diallogue supprime
    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Deleting all contact alert dialog")
            .setMessage("Do you want to delete all contact ?")
            .setPositiveButton("Yeah") { _, _ ->
                if (!isRecordSaved) {
                    viewModel.deleteAllContact()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

}
