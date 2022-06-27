package furniturerotan.id.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import furniturerotan.id.adapter.RecyclerViewAdapter
import furniturerotan.id.databinding.ActivityMejaBinding
import furniturerotan.id.model.Barang
import furniturerotan.id.model.Data
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MejaActivity : AppCompatActivity() {
    private var binding : ActivityMejaBinding? = null
    private var dataArrayList: List<Data>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMejaBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences.getString("token", "")

        val apiInterface =
            APIClient().getClient(applicationContext).create(APIInterface::class.java)

        val call: Call<Barang?>? =
            apiInterface.getAllProductByKategori("Bearer $cookie","Meja")
        call?.enqueue(object : Callback<Barang?> {
            override fun onResponse(call: Call<Barang?>, response: Response<Barang?>) {
                if (response.isSuccessful) {
                    dataArrayList = response.body()!!.data
                    for (i in dataArrayList?.indices!!) {
                        recyclerViewAdapter = RecyclerViewAdapter(dataArrayList!!, this@MejaActivity)
                        val layoutManager = GridLayoutManager(applicationContext, 2)

                        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (position == 0) 2 else 1
                            }
                        }
                        binding!!.recyclerView.layoutManager = layoutManager
                        binding!!.recyclerView.adapter = recyclerViewAdapter

                    }
                }
            }

            override fun onFailure(call: Call<Barang?>, t: Throwable) {
                call.cancel()
            }
        })
    }
}