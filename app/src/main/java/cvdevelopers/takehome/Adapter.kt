package cvdevelopers.takehome

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.utils.ItemDiffCallback
import cvdevelopers.takehome.utils.image.CircleTransformation
import kotlinx.android.synthetic.main.users.view.*

typealias ClickListener = (Client) -> Unit

class Adapter(private val clickListener: ClickListener) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var client: List<Client> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.users, parent, false
        ) as LinearLayout

        val viewHolder = ViewHolder(view)
        view.setOnClickListener { clickListener(client[viewHolder.adapterPosition]) }
        return viewHolder
    }

    override fun getItemCount(): Int = client.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(client[position])
    }

    fun updateClient(client: List<Client>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(this.client, client))
        this.client = client
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
        fun bind(client: Client) {
            val picture = client.picture.medium

            Picasso.get().load(picture).transform(CircleTransformation()).into(itemView.img)
            itemView.user_first.text = client.name.first
            itemView.user_last.text = client.name.last
        }
    }
}