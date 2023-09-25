package com.medkissi.contactmanagergroupe1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.model.Contact

class ContactAdapter(
    val listner: OnContactClickListner
) : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiff()) {

    inner class ContactViewHolder(itemView: View) : ViewHolder(itemView.rootView) {
        val nomComplet = itemView.findViewById<TextView>(R.id.nom)
        val telephone = itemView.findViewById<TextView>(R.id.textView2)
        val deteteBtn = itemView.findViewById<ImageView>(R.id.btn_delete)
        val updateBtn = itemView.findViewById<ImageView>(R.id.btn_update)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val contact = getItem(adapterPosition)
                    listner.onItemClick(contact)
                }
            }
            deteteBtn.setOnClickListener {
                val contact = getItem(adapterPosition)
                listner.onDeleteClick(contact)
            }
            updateBtn.setOnClickListener {
                val contact = getItem(adapterPosition)
                listner.onUpdateClick(contact)
            }
        }

        fun bind(contact: Contact) {
            nomComplet.text = contact.nomComplet
            telephone.text = contact.telephone

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
    fun onDeleteClick(contact: Contact)
    fun onUpdateClick(contact: Contact)
}

