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

const val CONTACT_KEY = "contact"
class AddEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        val nomComplet = findViewById<TextInputLayout>(R.id.text_nom)
        val tel = findViewById<TextInputLayout>(R.id.text_tel)
        val email = findViewById<TextInputLayout>(R.id.text_email)
        val btnEnregister = findViewById<Button>(R.id.btn_save)
            .setOnClickListener {
                if (nomComplet.editText?.text.toString().isNotEmpty() &&
                    tel.editText?.text.toString().isNotEmpty() &&
                    email.editText?.text.toString().isNotEmpty()
                    ){
                    val contact = Contact(
                        nomComplet = nomComplet.editText?.text.toString(),
                        telephone =  tel.editText?.text.toString(),
                        email =  email.editText?.text.toString()
                    )
                    val intent = Intent(this,MainActivity::class.java)
                    intent.putExtra(CONTACT_KEY,contact)
                    setResult(RESULT_OK,intent)
                    finish()


                }
            }





    }
}