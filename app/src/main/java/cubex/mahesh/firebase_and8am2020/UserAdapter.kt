package cubex.mahesh.firebase_and8am2020

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(var activity:Activity,
                  var list:MutableList<UserData>,
                  var recyclerView: RecyclerView) : RecyclerView.Adapter<UsersHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
        var infater = LayoutInflater.from(activity)
        var v = infater.inflate(R.layout.indi_row,
                            parent, false)
        v.setOnClickListener {
            var position = recyclerView.getChildLayoutPosition(v)
            var status = list.get(position).isSelected
            list.get(position).isSelected = !status
            this.notifyDataSetChanged()
        }
        return UsersHolder(v)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
            var user_obj = list.get(position)
            holder.fname.text = user_obj.fname
            holder.email.text = user_obj.email
            holder.mno.text = user_obj.mno
            holder.address.text = user_obj.address
            if(user_obj.gender.equals("male",
                    true)){
                holder.gender.setImageResource(R.drawable.male)
            }else{
                holder.gender.setImageResource(R.drawable.female)
            }
            if(user_obj.isSelected){
                holder.selectStatus.setImageResource(R.drawable.ic_correct)
            }else{
                holder.selectStatus.setImageResource(R.drawable.ic_correct_disabled)
            }
            Glide.with(activity).load(user_obj.profile_pic_url).into(holder.cview)
   }

}