package com.medkissi.contactmanagergroupe1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.localdatasource.ContactDatabase
import com.medkissi.contactmanagergroupe1.data.model.Contact



class AddEditActivity : AppCompatActivity() {
    lateinit var nom: TextInputLayout
    lateinit var tel: TextInputLayout
    lateinit var email: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        nom = findViewById<TextInputLayout>(R.id.text_nom)
        tel = findViewById<TextInputLayout>(R.id.text_tel)
        email = findViewById<TextInputLayout>(R.id.text_email)
        val btnEnregister = findViewById<Button>(R.id.btn_save)
            .setOnClickListener {
                if (intent.hasExtra(CONTACT_TO_UPDATE)) {
                    updateContact(

                    )
                } else {
                    saveContact()

                }


            }

        if (intent.hasExtra(CONTACT_TO_UPDATE)) {
            val contact = intent.getSerializableExtra(CONTACT_TO_UPDATE) as Contact
            nom.editText?.setText(contact.nomComplet)
            tel.editText?.setText(contact.telephone)
            email.editText?.setText(contact.email)

        }


    }

    private fun saveContact(){

        if (nom.editText?.text.toString().isNotEmpty() &&
            tel.editText?.text.toString().isNotEmpty() &&
            email.editText?.text.toString().isNotEmpty()

        ) {
            val contact = Contact(
                nomComplet = nom.editText?.text.toString(),
                telephone = tel.editText?.text.toString(),
                email = email.editText?.text.toString()

            )
            val intent = Intent(this,MainActivity::class.java)

            intent.putExtra(CONTACT_TO_SAVE,contact)
            setResult(RESULT_OK,intent)
            finish()

        }
    }


    private fun updateContact(){
        if (intent.hasExtra(CONTACT_TO_UPDATE)){
            val data  = intent.getSerializableExtra(CONTACT_TO_UPDATE) as Contact
            val contact  = Contact(
                id =  data.id,
                nomComplet =  nom.editText?.text.toString(),
                email = email.editText?.text.toString(),
                telephone =  tel.editText?.text.toString()

            )
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(UPDATED_CONTACT,contact)
            setResult(RESULT_OK, intent)
            finish()

        }
    }
}
