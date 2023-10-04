package com.medkissi.contactmanagergroupe1.ui

import ContactViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.model.Contact
import de.hdodenhof.circleimageview.CircleImageView

class DetailsActivty : AppCompatActivity() {
    val viewModel: ContactViewModel by viewModels()
    lateinit var nom : TextView
    lateinit var tel : TextView
    lateinit var email: TextView
    lateinit var image: CircleImageView

    private  val isRecordSaved = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_activty)
        image = findViewById<CircleImageView>(R.id.imag)
        nom = findViewById<TextView>(R.id.textview_nom)
        tel  = findViewById<TextView>(R.id.textView_tel)
        email = findViewById<TextView>(R.id.textView_email)


        val data = intent.getSerializableExtra(CONTACT_TO_SAVE) as Contact

        nom.text = data.nomComplet
        tel.text = data.telephone
        email.text = data.email
        image.setImageURI(Uri.parse(data.image))

         // bouton update
         val btnUpdate = findViewById<ImageButton>(R.id.btn_update)
             btnUpdate.setOnClickListener {
                 val intent = Intent(this, AddEditActivity::class.java)
                 intent.putExtra(CONTACT_TO_UPDATE, data)
                 // startActivity for result permet demarrer une activite et d'attendre un resultat
                 startActivityForResult(intent, 2)
             }

        // bouton retour
         val btnReturn = findViewById<ImageButton>(R.id.backSearch)
             btnReturn.setOnClickListener {
                 finish()
             }

        // pour le bouton partage
        val btnPartage = findViewById<ImageButton>(R.id.btn_share)
        btnPartage.setOnClickListener {
            val message = "Contact details :\n" +
                    "Nom : ${nom.text}\n" +
                    "Téléphone : ${tel.text}\n" +
                    "Email : ${email.text}"

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, message)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "Partager via..."))
            } else {
                Toast.makeText(this, "Aucune application de partage disponible", Toast.LENGTH_SHORT).show()
            }
        }

        // Le click sur l'email
        email.setOnClickListener {
            val email = email.text.toString()
            openGmailApp(email)
        }

        // le click sur le numero pour l'appel
        tel.setOnClickListener {
            val phoneNumber = tel.text.toString()
            openPhoneApp(phoneNumber)
        }

        // le click sur le numero pour le message
         val sms = findViewById<ImageButton>(R.id.sms)
        sms.setOnClickListener {
            val phoneNumber = tel.text.toString()
            openMessagingApp(phoneNumber)
        }

        // suprimer un contact
        val monBouton = findViewById<ImageButton>(R.id.btn_delete)
        monBouton.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun openMessagingApp(phoneNumber: String) {
        val smsUri = Uri.parse("smsto:$phoneNumber")
        val intent = Intent(Intent.ACTION_SENDTO, smsUri)
        startActivity(intent)
    }

    private fun openPhoneApp(phoneNumber: String) {
        val phoneIntentUri = Uri.parse("tel:$phoneNumber")
        val intent = Intent(Intent.ACTION_DIAL, phoneIntentUri)
        startActivity(intent)
    }

    private fun openGmailApp(email: String) {
        val gmailIntentUri = viewModel.generateGmailIntentUri(email)
        val intent = Intent(Intent.ACTION_VIEW, gmailIntentUri)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK) {
            val contact1 = data?.getSerializableExtra(UPDATED_CONTACT) as Contact
            nom.text = contact1.nomComplet
            tel.text =contact1.telephone
            email.text = contact1.email
            image.setImageURI(Uri.parse(contact1.image))
            viewModel.updateContact(contact1)
        }
    }

    // boite de diallogue supprime
    private fun showConfirmationDialog() {
        val data = intent.getSerializableExtra(CONTACT_TO_SAVE) as Contact
        AlertDialog.Builder(this)
            .setTitle("Deleting a contact alert dialog")
            .setMessage("Do you want to delete this contact ?")
            .setPositiveButton("Yeah") { _, _ ->
                if (!isRecordSaved) {
                    viewModel.deleteContact(contact = data)
                    finish()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}