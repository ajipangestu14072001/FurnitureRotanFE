package furniturerotan.id.view

import android.Manifest
import android.R
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import furniturerotan.id.databinding.ActivityProductBinding
import furniturerotan.id.model.Data
import furniturerotan.id.response.DataObject
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProductActivity : AppCompatActivity() {
    private var binding : ActivityProductBinding? = null
    private var imageUri: Uri? = null
    private var storageReference: StorageReference? = null
    private var databaseReference: DatabaseReference? = null
    private var storagePath = "Poto"
    private var progressDialog: ProgressDialog? = null
    private val kategori = arrayOf("" ,"Kursi", "Meja", "Lemari", "Lain-lain")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path)
        storageReference = FirebaseStorage.getInstance().reference
        progressDialog = ProgressDialog(this)
        setSupportActionBar(binding!!.toolbar)
        val kategoriRotan =
            ArrayAdapter(this@ProductActivity, R.layout.simple_list_item_1, kategori)
        binding!!.kategori.adapter = kategoriRotan

        binding!!.submit.setOnClickListener { uploadImageFileToFirebaseStorage() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                == PackageManager.PERMISSION_DENIED
            ) {
                val permission =
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, 111)
            }
        }

        binding!!.action2.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE)
        }
        binding!!.action3.setOnClickListener { openCamera() }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            Glide.with(this).load(imageUri).into(binding!!.imgview)
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            Glide.with(this).load(imageUri).into(binding!!.imgview)
        }
    }

    private fun getFileExtension(uri: Uri?): String? {
        val c = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(c.getType(uri!!))
    }

    private fun uploadImageFileToFirebaseStorage() {
        if (imageUri == null) {
            Toast.makeText(this, "Poto Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            return
        }
        progressDialog!!.setTitle(" Data is Uploading...")
        progressDialog!!.setMessage(" Transfer Data Ke Server")
        progressDialog!!.isIndeterminate = false
        progressDialog!!.show()
        val storageReference2 = storageReference
        storageReference2!!.child(
            storagePath + System.currentTimeMillis() + "." + getFileExtension(
                imageUri
            )
        ).putFile(
            imageUri!!
        ).addOnSuccessListener { taskSnapshot ->
            val sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE)
            val cookie = sharedPreferences1.getString("token", "")
            val key = databaseReference!!.push().key
            progressDialog!!.dismiss()
            val downloadUrl = taskSnapshot.storage.downloadUrl
            do {
            } while (!downloadUrl.isSuccessful)
            val barang = DataObject(
                binding!!.desc.text.toString(),
                binding!!.harga.text.toString().toInt(),
                binding!!.kategori.selectedItem.toString(),
                binding!!.lokasi.text.toString(),
                binding!!.namaBarang.text.toString(),
                Objects.requireNonNull(downloadUrl.result.toString()),
                binding!!.stock.text.toString().toInt()
            )
            val apiInterface =
                APIClient().getClient(applicationContext).create(APIInterface::class.java)
            apiInterface.addProduct("Bearer $cookie", barang)!!.enqueue(object : Callback<DataObject?> {
                override fun onResponse(call: Call<DataObject?>, response: Response<DataObject?>) {
                    if (response.body() != null) {
                        Toast.makeText(
                            this@ProductActivity,
                            "Input Barang Berhasil",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DataObject?>, t: Throwable) {
                    Log.i("Ini Log", t.message!!)
                }
            })
            val imageUploadInfo = DataObject(
                binding!!.desc.text.toString(),
                binding!!.harga.text.toString().toInt(),
                binding!!.kategori.selectedItem.toString(),
                binding!!.lokasi.text.toString(),
                binding!!.namaBarang.text.toString(),
                Objects.requireNonNull(downloadUrl.result.toString()),
                binding!!.stock.text.toString().toInt()
            )
            val databaseReference = databaseReference
            databaseReference!!.child("List$key").setValue(imageUploadInfo)
        }.addOnFailureListener { exc ->
            progressDialog!!.dismiss()
            Toast.makeText(this@ProductActivity, exc.message, Toast.LENGTH_SHORT).show()
        }
            .addOnProgressListener { progressDialog!!.setTitle("Data is Uploading...") }
    }
    companion object {
        const val Database_Path = "FurnitureRotan"
        private const val RESULT_LOAD_IMAGE = 123
        const val IMAGE_CAPTURE_CODE = 654
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}