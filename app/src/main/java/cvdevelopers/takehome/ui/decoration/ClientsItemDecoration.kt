package cvdevelopers.takehome.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ClientsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        with(outRect) {
            left = 30

            val position = parent.getChildAdapterPosition(view)
            if (position == 0) {
                top = 30
                return
            }

            top = 50

            parent.adapter?.itemCount?.let {
                if (position == it - 1) {
                    bottom = 30
                }
            }
        }
    }
}
