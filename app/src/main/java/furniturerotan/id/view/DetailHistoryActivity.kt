package furniturerotan.id.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import furniturerotan.id.databinding.ActivityDetailHistoryBinding
import furniturerotan.id.home.HomeActivity
import furniturerotan.id.model.DataX
import furniturerotan.id.response.DataObject
import furniturerotan.id.response.ResponseDataObject
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryActivity : AppCompatActivity() {
    private var binding : ActivityDetailHistoryBinding? = null
    private var datumList : DataObject? =  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        val sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences1.getString("token", "")
        val shared = getSharedPreferences("HISTORY", MODE_PRIVATE)
        val idBarang = shared.getString("idBarang","")
        val total = shared.getString("total","")
        val pengirimaan = shared.getString("pengirimaan","")
        val jumlah = shared.getString("jumlah","")
        val apiInterface = APIClient().getClient(applicationContext).create(APIInterface::class.java)
        val call: Call<ResponseDataObject?>? =
            apiInterface.getBarangByID("Bearer $cookie", idBarang)
        call?.enqueue(object : Callback<ResponseDataObject?> {
            override fun onResponse(
                call: Call<ResponseDataObject?>,
                response: Response<ResponseDataObject?>
            ) {
                Log.d("TAG", response.code().toString() + "")
                val resource: ResponseDataObject? = response.body()
                datumList = resource?.data
                if (response.isSuccessful) {
                    if (datumList != null) {
                        Glide.with(applicationContext)
                            .asBitmap()
                            .load(datumList!!.pathPhoto.replace("\\\\".toRegex(), ""))
                            .into(binding!!.imgHistory)
                        binding!!.namaBarangHistory.text = datumList!!.namaBarang
                        binding!!.hargaHistory.text = "Rp."+ datumList!!.harga.toString()
                        binding!!.jumlahBeliHistory.text = jumlah
                        binding!!.totalHargaHistory.text = total
                        binding!!.pengeirimanHistory.text = pengirimaan
                    }

                }
            }

            override fun onFailure(call: Call<ResponseDataObject?>, t: Throwable) {
                call.cancel()
            }
        })
        binding!!.lihatlistTransaksi.setOnClickListener{
            val intent = Intent(this@DetailHistoryActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}