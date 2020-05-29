package cubex.mahesh.firebase_and8am2020

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_pic.*
import java.io.ByteArrayOutputStream


class ProfilePicActivity : AppCompatActivity() {
    var uri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_pic)

        var status = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA)
        if(status != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                11)
        }
    }

    fun addPhoto(v:View){
        var listener = DialogInterface.OnClickListener { dialog, which ->
            if(which == DialogInterface.BUTTON_POSITIVE){
                var i = Intent("android.media.action.IMAGE_CAPTURE")
                startActivityForResult(i,123)
            }else if(which == DialogInterface.BUTTON_NEGATIVE){
                var i = Intent()
                i.setAction(Intent.ACTION_GET_CONTENT)
                i.setType("image/*")
                startActivityForResult(i,124)
            }
        }

        var aDialog = AlertDialog.Builder(this)
        aDialog.setTitle("Message")
        aDialog.setMessage("Please select an option to upload the Image. ")
        aDialog.setPositiveButton("Camera",listener)
        aDialog.setNegativeButton("Gallery",listener)
        aDialog.show()
    }

    fun next(v:View)
    {
            var sRef = FirebaseStorage.getInstance().getReference("profile_pics")
            var childRef = sRef.child(MainActivity.uid!!)
            var fileRef = childRef.child("profile_pic")
            var task = fileRef.putFile(uri!!)
            task.addOnSuccessListener {
                Toast.makeText(this@ProfilePicActivity,
                    "File upload success...", Toast.LENGTH_LONG).show()
            }
            task.addOnFailureListener {
                Toast.makeText(this@ProfilePicActivity,
                    "File upload failed...", Toast.LENGTH_LONG).show()
            }
        val urlTask = task.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fileRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                var dBase = FirebaseDatabase.getInstance()
                var dRef = dBase.getReference("users")
                var uid_ref = dRef.child(MainActivity.uid!!)
                uid_ref.child("profile_pic_url").setValue(downloadUri.toString())

                var i = Intent(this@ProfilePicActivity,
                    MainActivity::class.java)
                startActivity(i)

            } else {
                // Handle failures
                // ...
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123 && resultCode== Activity.RESULT_OK)
        {
            var bmp = data?.extras?.get("data") as Bitmap
            add_image.setImageBitmap(bmp)
            uri = getImageUri(this@ProfilePicActivity,bmp)
        }else if(requestCode == 124 && resultCode== Activity.RESULT_OK)
        {
              uri =   data?.data
            add_image.setImageURI(uri)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }
}
