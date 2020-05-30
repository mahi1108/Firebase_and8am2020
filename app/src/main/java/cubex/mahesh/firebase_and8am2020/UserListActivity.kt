package cubex.mahesh.firebase_and8am2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_list.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class UserListActivity : AppCompatActivity() {
    var list = mutableListOf<UserData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

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
            var fcm_tokens = mutableListOf<String>()
            for(user in list){
                if(user.isSelected){
                    fcm_tokens.add(user.fcm_token)
                }
            }
        sendFcmMessageToAll(msg.text.toString(), fcm_tokens)
    }

    fun sendToAll(v:View)
    {
        var fcm_tokens = mutableListOf<String>()
        for(user in list){
                fcm_tokens.add(user.fcm_token)
        }
        sendFcmMessageToAll(msg.text.toString(), fcm_tokens)
    }

    fun sendFcmMessage(token:String?, msg:String?)
    {
        var jsonObjec: JSONObject? = null
        var bodydata:String = msg!!

        jsonObjec =  JSONObject()
        var list = mutableListOf<String>()
        list.add(token!!)

        var   jsonArray: JSONArray = JSONArray(list)
        jsonObjec.put("registration_ids", jsonArray);
        var jsonObjec2: JSONObject = JSONObject()
        jsonObjec2.put("body", bodydata);
        jsonObjec2.put("title", "Text Message from Android 8AM2020-INDI")
        jsonObjec2.put("fcm_type", "text")
        jsonObjec.put("notification", jsonObjec2);

        jsonObjec.put("time_to_live", 172800);
        jsonObjec.put("priority", "HIGH");

        println("*************")
        print(jsonObjec)
        println("*************")


        val client = OkHttpClient()
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = RequestBody.create(JSON, jsonObjec.toString())
        val request = Request.Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=AAAAMwllv10:APA91bEfjwDXakiUAK77siPVFH6DzYPUal0joUAg6JZ40UZjShRK2rsQLZOqxdjcD67-ci7FW-eJCmOGmxnVTe479B1qYZafto_i8cuihHaQvHylvatqz-pLY68eIGt2Sv2xTFVnS_Y4")
            .url("https://fcm.googleapis.com/fcm/send")
            .post(body)
            .build()
        val call = client.newCall(request)
        call.enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
            }
            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    fun sendFcmMessageToAll(msg:String,fcm_tokens_list:MutableList<String>)
    {
        var bodydata:String = msg

        var  jsonObjec =  JSONObject()

        var   jsonArray: JSONArray = JSONArray(fcm_tokens_list)
        jsonObjec.put("registration_ids", jsonArray);
        var jsonObjec2: JSONObject = JSONObject()
        jsonObjec2.put("body", bodydata);
        jsonObjec2.put("title", "Text Message from And8AM2020-NAll ")
        jsonObjec2.put("image_url","https://st.depositphotos.com/1020482/3088/i/950/depositphotos_30885339-stock-photo-android-with-adjustable-wrench-technology.jpg")
        jsonObjec.put("notification", jsonObjec2);

        jsonObjec.put("time_to_live", 172800);
        jsonObjec.put("priority", "HIGH");
        jsonObjec.put("content_available", true);


        println("*************")
        print(jsonObjec)
        println("*************")


        val client = OkHttpClient()
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = RequestBody.create(JSON, jsonObjec.toString())
        val request = Request.Builder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "key=AAAAMwllv10:APA91bEfjwDXakiUAK77siPVFH6DzYPUal0joUAg6JZ40UZjShRK2rsQLZOqxdjcD67-ci7FW-eJCmOGmxnVTe479B1qYZafto_i8cuihHaQvHylvatqz-pLY68eIGt2Sv2xTFVnS_Y4")
            .url("https://fcm.googleapis.com/fcm/send")
            .post(body)
            .build()
        val call = client.newCall(request)
        call.enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }
}
