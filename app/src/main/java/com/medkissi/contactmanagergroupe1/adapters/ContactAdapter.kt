package com.medkissi.contactmanagergroupe1.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.model.Contact
import com.medkissi.contactmanagergroupe1.ui.MainActivity
import de.hdodenhof.circleimageview.CircleImageView

class ContactAdapter(
    val listner: MainActivity
) : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiff()) {

    inner class ContactViewHolder(itemView: View) : ViewHolder(itemView.rootView) {
        // pour le recycle view
        val nomComplet = itemView.findViewById<TextView>(R.id.nom)
        val image = itemView.findViewById<CircleImageView>(R.id.circleImageView)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val contact = getItem(adapterPosition)
                    listner.onItemClick(contact)
                }
            }
        }

        fun bind(contact: Contact) {
            nomComplet.text = contact.nomComplet
            image.setImageURI(Uri.parse(contact.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

}

class ContactDiff:DiffUtil.ItemCallback<Contact>(){
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return  oldItem == newItem
    }


}

interface OnContactClickListner {
    fun onItemClick(contact: Contact)

}

