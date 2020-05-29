package cubex.mahesh.firebase_and8am2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var _verificationId : String? = null
    var _fromLogin : Boolean = false
    companion object{
        var email :String? = null
        var pass :String? = null
        var mno :String? = null
        var uid:String? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun register(v:View){
        _fromLogin = false
            var fAuth = FirebaseAuth.getInstance()
            fAuth.createUserWithEmailAndPassword(
                et_email.text.toString(),
                password.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    uid = FirebaseAuth.getInstance().uid
                            sendOTP()
                }else{
                    Toast.makeText(this@MainActivity,
                        "Please provide valid data...",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    fun submit(v:View)
    {
            var credential = PhoneAuthProvider.getCredential(
                _verificationId!!,otp_text.text.toString())
  FirebaseAuth.getInstance().signInWithCredential(credential).
      addOnCompleteListener {
          if(it.isSuccessful){
              if(!_fromLogin) {
                  Toast.makeText(
                      this@MainActivity,
                      "Valid OTP...",
                      Toast.LENGTH_LONG
                  ).show()
                  email = et_email.text.toString()
                  pass = password.text.toString()
                  mno = mobileno.text.toString()
                  var i = Intent(
                      this@MainActivity,
                      UserDetailsActivity::class.java
                  )
                  startActivity(i)
              }else{
                  var i = Intent(this@MainActivity,
                      UserListActivity::class.java)
                  startActivity(i)
              }
          }else{
              Toast.makeText(this@MainActivity,
                  "InValid OTP...",
                  Toast.LENGTH_LONG).show()
          }
      }
    }

    fun sendOTP()
    {
        PhoneAuthProvider.getInstance().
            verifyPhoneNumber(
                "+"+mobileno.text.toString(),
                60.toLong(),
                TimeUnit.SECONDS,
                this,
                object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                    override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
                        Toast.makeText(this@MainActivity,
                            "onVerificationCompleted...",
                            Toast.LENGTH_LONG).show()
                    }

                    override fun onVerificationFailed(p0: FirebaseException?) {
                        Toast.makeText(this@MainActivity,
                            "onVerificationFailed...",
                            Toast.LENGTH_LONG).show()
                    }

                    override fun onCodeSent(
                        verificationId: String?,
                        token: PhoneAuthProvider.ForceResendingToken?
                    ) {
                        super.onCodeSent(verificationId, token)
                        Toast.makeText(this@MainActivity,
                            "onCodeSent..."+verificationId,
                            Toast.LENGTH_LONG).show()
                        _verificationId = verificationId;
                    }

                })
    }

    fun login(v:View)
    {

            FirebaseAuth.getInstance().
                signInWithEmailAndPassword(
                    et_email.text.toString(),
                    password.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            _fromLogin = true
                           sendOTP()

                        }else{
                            Toast.makeText(this@MainActivity,
                                "Invalid user credentials...",
                                Toast.LENGTH_LONG).show()
                        }
            }
    }
}
