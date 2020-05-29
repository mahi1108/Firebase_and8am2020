package cubex.mahesh.firebase_and8am2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        var list = mutableListOf<UserData>()

        var lManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rview.layoutManager = lManager
        var myadapter = UserAdapter(this, list,rview)
        rview.adapter = myadapter

        var dBase = FirebaseDatabase.getInstance()
        var dRef = dBase.getReference("users")
        dRef.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(users_ds: DataSnapshot) {
                 var iterable_users =    users_ds.children
                 var iterator_users = iterable_users.iterator()
                list.clear()
                while (iterator_users.hasNext()){
                    var uid_ds = iterator_users.next()
                    var iterable_uid =    uid_ds.children
                    var iterator_uid = iterable_uid.iterator()
                    var userData = UserData("",
                        "","","","",
                        "",false,
                        "","")
                    while (iterator_uid.hasNext()){
                        var child_uid =  iterator_uid.next()
                        when(child_uid.key){
                            "address" -> userData.address = child_uid.value.toString()
                                "email" -> userData.email = child_uid.value.toString()
                            "fname" -> userData.fname = child_uid.value.toString()
                            "lname" -> userData.lname = child_uid.value.toString()
                            "mno" -> userData.mno = child_uid.value.toString()
                            "gender" -> userData.gender = child_uid.value.toString()
                            "fcm_token" -> userData.fcm_token = child_uid.value.toString()
                            "profile_pic_url" -> userData.profile_pic_url = child_uid.value.toString()
                        }
                    }
                    list.add(userData)
                }
                myadapter.notifyDataSetChanged()
            }

        })


    }

    fun send(v:View){

    }

    fun sendToAll(v:View)
    {

    }
}
