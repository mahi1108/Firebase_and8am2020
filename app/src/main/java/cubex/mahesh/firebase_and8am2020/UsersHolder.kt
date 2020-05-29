package cubex.mahesh.firebase_and8am2020

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.indi_row.view.*

class UsersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cview = itemView.cview
    var fname = itemView.fname
    var email = itemView.email
    var mno = itemView.mno
    var address = itemView.address
    var gender = itemView.gender
    var selectStatus = itemView.select_status
}