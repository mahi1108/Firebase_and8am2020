package cubex.mahesh.firebase_and8am2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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
    }

    fun send(v:View){

    }

    fun sendToAll(v:View)
    {

    }
}
