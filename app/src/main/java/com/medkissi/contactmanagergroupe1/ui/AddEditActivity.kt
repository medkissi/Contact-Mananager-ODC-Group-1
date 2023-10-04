package com.medkissi.contactmanagergroupe1.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import com.google.android.material.textfield.TextInputLayout
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.model.Contact
import com.medkissi.contactmanagergroupe1.databinding.ActivityAddEditBinding
import de.hdodenhof.circleimageview.CircleImageView

class AddEditActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivityAddEditBinding
    lateinit var nom: TextInputLayout
    lateinit var tel: TextInputLayout
    lateinit var email: TextInputLayout
    lateinit var images: CircleImageView
    var  url : Uri? = null

    val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        url = it
        images.setImageURI(url)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*    gestion des champs    */
        nomFocusListener()
        telephoneFocusListener()
        emaiFocusListener()

        nom = findViewById<TextInputLayout>(R.id.text_nom)
        tel = findViewById<TextInputLayout>(R.id.text_tel)
        email = findViewById<TextInputLayout>(R.id.text_email)
        images = findViewById<CircleImageView>(R.id.textView)
        nom.requestFocus()

        images.setOnClickListener {
             launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        val btnEnregister = findViewById<Button>(R.id.btn_save)
        btnEnregister.setOnClickListener {
                if (intent.hasExtra(CONTACT_TO_UPDATE)) {
                    updateContact()
                } else {
                    enregistrerContact()
                }
            }

        // bouton retour
        val btnReturn = findViewById<ImageButton>(R.id.backSearch)
        btnReturn.setOnClickListener {
                finish()
            }

        // recuperer les informations et pre-remplir les champs pour modifier
        if (intent.hasExtra(CONTACT_TO_UPDATE)) {
            val contact = intent.getSerializableExtra(CONTACT_TO_UPDATE) as Contact
            nom.editText?.setText(contact.nomComplet)
            tel.editText?.setText(contact.telephone)
            email.editText?.setText(contact.email)
            images.setImageURI(Uri.parse(contact.image))
        }

    }

    private fun enregistrerContact() {
        val validatenom = validatenom()
        val validtelephone = validtelephone()
        val validEmail = validEmail()

        var message = ""
        var isvalid = true

        if (validatenom != null) {
            message = "Name must have at least 3 characters."
            nom.requestFocus()
            isvalid = false
        }

        if (validtelephone != null) {
            message = "Phone number can't exceed 15 characters"
            tel.requestFocus()
            isvalid = false
        }

        if (validEmail != null) {
            message = "Email address is invalid (email format)"
            email.requestFocus()
            isvalid = false
        }

        if (isvalid) {
            if (url == null) {
                // Récupérer l'URI de l'image par défaut depuis les ressources
                val defaultImageUri = Uri.parse("android.resource://${packageName}/${R.drawable.b}")
                url = defaultImageUri
            }
            saveContact()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Errors in your form !!!!")
                .setMessage(message)
                .setPositiveButton("Okay") { _, _ ->
                }
                .show()
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
                email = email.editText?.text.toString(),
                image = url.toString()
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
                telephone =  tel.editText?.text.toString(),
                image = url?.toString() ?:data.image
            )
            val intent = Intent(this,DetailsActivty::class.java)
            intent.putExtra(UPDATED_CONTACT,contact)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun nomFocusListener() {
        binding.nonEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textNom.helperText = validatenom()
            }
        }
    }

    private fun validatenom(): String? {
        val nomText = binding.nonEditText.text.toString()
        if (nomText.length < 3) {
            return "Name field can't be empty"
        }
        return null
    }

    private fun emaiFocusListener() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused){
                binding.textEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String ? {
        val emailText = binding.emailEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
             return "Email field can't be empty"
        }
        return null
    }

    private fun telephoneFocusListener() {
        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val phoneText = s.toString()
                if (phoneText.length >= 14) {
                    binding.textTel.helperText = validtelephone()
                   /* binding.phoneEditText.isEnabled = false*/
                } else {
                    binding.textTel.helperText = ""
                   /* binding.phoneEditText.isEnabled = true*/
                }
            }
        })

        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textTel.helperText = validtelephone()
            }
        }
    }

    private fun validtelephone(): String ? {
        val phoneText = binding.phoneEditText.text.toString()
        if (!phoneText.matches(".*[0-9].*" .toRegex())) {
            return "Phone number can't be empty"
        }
        if (phoneText.length >= 15 ) {
            return "Phone number can exceed 15 caracters"
        }
        return null
    }
}
