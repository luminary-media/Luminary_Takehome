package cvdevelopers.takehome.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.model.users.Client
import cvdevelopers.takehome.picasso.CircleTransformation
import org.koin.java.KoinJavaComponent.get

import kotlin.collections.ArrayList

class ClientsAdapter(
        private val picasso: Picasso = get(Picasso::class.java)
) : RecyclerView.Adapter<ClientsAdapter.ClientEntryHolder>() {
    private val entries = ArrayList<ClientEntry>()

    fun updateClients(clients: List<Client>?) {
        if (clients.isNullOrEmpty()) {
            entries.clear()
            notifyDataSetChanged()
            return
        }

        val newEntries = ArrayList<ClientEntry>()
        for (client in clients) {
            val entry = ClientEntry(client)
            newEntries.add(entry)
        }

        entries.clear()
        entries.addAll(newEntries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientEntryHolder {
        val entryView = LayoutInflater.from(parent.context).inflate(R.layout.clients_list_item_layout, parent, false)
        return ClientEntryHolder(entryView)
    }

    @Synchronized
    override fun onBindViewHolder(entryHolder: ClientEntryHolder, position: Int) {
        val entry = entries[position]
        setAvatar(entryHolder, entry)
        setName(entryHolder, entry)
    }

    private fun setName(entryHolder: ClientEntryHolder, entry: ClientEntry) {
        val first = entry.client.name?.first ?: ""
        val last = entry.client.name?.last ?: ""
        entryHolder.fullName.text = "$first $last"
    }

    private fun setAvatar(entryHolder: ClientEntryHolder, entry: ClientEntry) {
        val uri = entry.client.picture?.thumbnail ?: return
        picasso
                .load(uri)
                .transform(CircleTransformation())
                .into(entryHolder.avatar)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class ClientEntryHolder(
            root: View
    ) : RecyclerView.ViewHolder(root) {
        val fullName: TextView = root.findViewById(R.id.tvName)
        val avatar: ImageView = root.findViewById(R.id.ivAvatar)
    }

    class ClientEntry(
            val client: Client
    )
}
