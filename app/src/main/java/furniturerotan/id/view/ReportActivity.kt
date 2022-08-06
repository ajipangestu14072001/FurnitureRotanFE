package furniturerotan.id.view

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import furniturerotan.id.databinding.ActivityReportBinding
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ReportActivity : AppCompatActivity() {
    private var binding : ActivityReportBinding? = null
    private val filePath = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        binding!!.reportTransaksi.setOnClickListener {
            val apiInterface =
                APIClient().getClient(applicationContext).create(APIInterface::class.java)
            apiInterface.getReport("Bearer $token")!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if (response.isSuccessful){
                        val file = File(filePath, "Transaksi.pdf")
                        val os = file.outputStream()
                        os.write(response.body()!!.bytes())
                        os.flush()
                        os.close()
                        Toast.makeText(applicationContext, "Berhasil Export Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.i("Ini Log", t.message!!)
                }
            })
        }
        binding!!.keluarReport.setOnClickListener{
            val apiInterface =
                APIClient().getClient(applicationContext).create(APIInterface::class.java)
            apiInterface.getReport("Bearer $token")!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if (response.isSuccessful){
                        val file = File(filePath, "Barang Keluar.pdf")
                        val os = file.outputStream()
                        os.write(response.body()!!.bytes())
                        os.flush()
                        os.close()
                        Toast.makeText(applicationContext, "Berhasil Export Data", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.i("Ini Log", t.message!!)
                }
            })
        }
        binding!!.barangMasuk.setOnClickListener{
            val apiInterface =
                APIClient().getClient(applicationContext).create(APIInterface::class.java)
            apiInterface.getReportMasuk("Bearer $token")!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    if (response.isSuccessful){
                        val file = File(filePath, "Barang Masuk.pdf")
                        val os = file.outputStream()
                        os.write(response.body()!!.bytes())
                        os.flush()
                        os.close()
                        Toast.makeText(applicationContext, "Berhasil Export Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.i("Ini Log", t.message!!)
                }
            })
        }
    }
}