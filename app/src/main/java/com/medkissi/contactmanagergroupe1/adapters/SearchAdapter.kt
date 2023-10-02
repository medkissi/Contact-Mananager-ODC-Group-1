import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.medkissi.contactmanagergroupe1.R
import com.medkissi.contactmanagergroupe1.data.model.Contact
import com.medkissi.contactmanagergroupe1.ui.MainActivity
import de.hdodenhof.circleimageview.CircleImageView

class SearchAdapter(private val onItemClick: (Contact) -> Unit) : ListAdapter<Contact, SearchAdapter.ContactViewHolder>(ContactDiffCallback()) {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView.rootView) {
        val nom: TextView = itemView.findViewById(R.id.tvNom)
        val tel: TextView = itemView.findViewById(R.id.tvTelephone)
        val image = itemView.findViewById<CircleImageView>(R.id.tvCiv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resultat, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = getItem(position)
        holder.nom.text = currentContact.nomComplet
        holder.tel.text = currentContact.telephone
        holder.image.setImageURI(Uri.parse(currentContact.image))

        holder.itemView.setOnClickListener {
            onItemClick(currentContact)
        }
    }

    private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}
