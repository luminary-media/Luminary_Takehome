package cvdevelopers.takehome.utils

import androidx.recyclerview.widget.DiffUtil
import cvdevelopers.takehome.models.Client

class ItemDiffCallback(
        private val old: List<Client>,
        private val new: List<Client>
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex].name == new[newIndex].name
    }

    override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex] == new[newIndex]
    }
}