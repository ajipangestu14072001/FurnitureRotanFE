package furniturerotan.id.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import furniturerotan.id.adapter.MyListAdapter
import furniturerotan.id.databinding.ActivityHistoryBinding
import furniturerotan.id.model.DataX
import furniturerotan.id.model.History
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    private var dataArrayList: List<DataX>? = null
    var adapter: MyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)
        val sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences1.getString("token", "")
        val idUsers = sharedPreferences1.getString("userId", "")
        val apiInterface =
            APIClient().getClient(applicationContext).create(APIInterface::class.java)
        val call: Call<History?>? =
            apiInterface.getHistory("Bearer $cookie", idUsers)
        call?.enqueue(object : Callback<History?> {
            override fun onResponse(
                call: Call<History?>,
                response: Response<History?>
            ) {
                try {
                    dataArrayList = response.body()!!.data
                    if (response.isSuccessful) {
                        adapter = MyListAdapter(this@HistoryActivity, dataArrayList!!) {
                            val shared = getSharedPreferences("HISTORY", MODE_PRIVATE)
                            val editor = shared.edit()
                            editor.putString("idBarang", it.idBarang)
                            editor.putString("total", it.totalHarga.toString())
                            editor.putString("pengirimaan", it.pengiriman)
                            editor.putString("jumlah", it.jumlahBarang.toString())
                            editor.apply()

                        }
                        binding!!.historyList.adapter = adapter
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<History?>, t: Throwable) {
                call.cancel()
            }
        })
    }
}