package furniturerotan.id.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import furniturerotan.id.databinding.ActivityDetailBinding
import furniturerotan.id.helper.SharedPrefManager
import furniturerotan.id.response.Chart
import furniturerotan.id.response.ResponseDataObject
import furniturerotan.id.response.ResponseRegister
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private  var binding : ActivityDetailBinding? =null
    private var sharedPrefManager: SharedPrefManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        sharedPrefManager = SharedPrefManager(applicationContext)
        val sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences1.getString("token", "")
        val userId = sharedPreferences1.getString("userId", "")
        val idBarang = intent.getStringExtra("id")
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
                val datumList = resource?.data
                if (response.isSuccessful) {
                    if (datumList != null) {
                        Glide.with(applicationContext)
                            .asBitmap()
                            .load(datumList.pathPhoto.replace("\\\\".toRegex(), ""))
                            .into(binding!!.imgview)
                        binding!!.title.text = datumList.namaBarang
                        binding!!.harga.text = "Rp."+datumList.harga.toString()
                        binding!!.desc.text = datumList.deskripsi
                        binding!!.lokasi.text = datumList.lokasi
                    }

                }
            }

            override fun onFailure(call: Call<ResponseDataObject?>, t: Throwable) {
                call.cancel()
            }
        })
        binding!!.chartBtn.setOnClickListener {
            if (!sharedPrefManager!!.sPSudahLogin) {
                startActivity(
                    Intent(
                        applicationContext,
                        AuthActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            } else {
                val call: Call<ResponseDataObject?>? =
                    apiInterface.getBarangByID("Bearer $cookie", idBarang)
                call?.enqueue(object : Callback<ResponseDataObject?> {
                    override fun onResponse(
                        call: Call<ResponseDataObject?>,
                        response: Response<ResponseDataObject?>
                    ) {
                        Log.d("TAG", response.code().toString() + "")
                        val resource: ResponseDataObject? = response.body()
                        val datumList = resource?.data
                        if (response.isSuccessful) {
                            if (datumList != null) {
                                val chart = Chart(idBarang!!, userId!!,datumList.namaBarang,datumList.harga, datumList.deskripsi, datumList.pathPhoto,datumList.kategori, datumList.lokasi)
                                val call: Call<Chart?>? =
                                    apiInterface.addChart("Bearer $cookie", chart)
                                call?.enqueue(object : Callback<Chart?> {
                                    override fun onResponse(
                                        call: Call<Chart?>,
                                        response: Response<Chart?>
                                    ) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(applicationContext, "Berhasil Menambahkan di Keranjang", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<Chart?>, t: Throwable) {
                                        call.cancel()
                                    }
                                })
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseDataObject?>, t: Throwable) {
                        call.cancel()
                    }
                })
            }

        }
        binding!!.buyBtn.setOnClickListener {
            if (!sharedPrefManager!!.sPSudahLogin) {
                startActivity(
                    Intent(
                        applicationContext,
                        AuthActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            } else {
                val intent = Intent(applicationContext, BuyActivity::class.java)
                startActivity(intent)
            }
        }
    }
}