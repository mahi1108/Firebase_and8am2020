package cubex.mahesh.firebase_and8am2020

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService :  FirebaseMessagingService(){
    override fun onMessageReceived(msg: RemoteMessage?) {
        super.onMessageReceived(msg)
        Log.i("msg","FCM Msg Payload : "+ msg?.data)

    }
}