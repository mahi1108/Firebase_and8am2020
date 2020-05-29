package cubex.mahesh.firebase_and8am2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
    }

    fun submit1(v:View)
    {
            var dBase = FirebaseDatabase.getInstance()
            var dRef = dBase.getReference("users")
            var uid_ref = dRef.child(MainActivity.uid!!)
            uid_ref.child("fname").setValue(et_fname.text.toString())
            uid_ref.child("lname").setValue(et_lname.text.toString())
            uid_ref.child("gender").setValue(et_gender.text.toString())
            uid_ref.child("address").setValue(address.text.toString())
            uid_ref.child("email").setValue(MainActivity.email)
            uid_ref.child("pass").setValue(MainActivity.pass)
            uid_ref.child("mno").setValue(MainActivity.mno)
            getFCMToken(uid_ref)
            var i = Intent(this, ProfilePicActivity::class.java)
            startActivity(i)
    }

    fun getFCMToken(uid_ref:DatabaseReference){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
             val token = task.result?.token
             uid_ref.child("fcm_token").setValue(token)
            })
    }
}
