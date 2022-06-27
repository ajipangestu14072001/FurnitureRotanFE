package furniturerotan.id.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import furniturerotan.id.adapter.CartAdapter
import furniturerotan.id.adapter.RecyclerViewAdapter
import furniturerotan.id.databinding.ActivityChartDetailBinding
import furniturerotan.id.model.Data
import furniturerotan.id.response.Chart
import furniturerotan.id.response.ResponseChart
import furniturerotan.id.response.ResponseDataObject
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChartDetailActivity : AppCompatActivity() {
    private var binding : ActivityChartDetailBinding? = null
    private var recyclerViewAdapter: CartAdapter? = null
    private var dataArrayList: List<Chart>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartDetailBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        val sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences1.getString("token", "")
        val userId = sharedPreferences1.getString("userId", "")
        val apiInterface = APIClient().getClient(applicationContext).create(APIInterface::class.java)
        val call: Call<ResponseChart?>? =
            apiInterface.getChartIdUsers("Bearer $cookie", userId)
        call?.enqueue(object : Callback<ResponseChart?> {
            override fun onResponse(
                call: Call<ResponseChart?>,
                response: Response<ResponseChart?>
            ) {
                val resource: ResponseChart? = response.body()
                val datumList = resource?.data
                if (response.isSuccessful) {
                    dataArrayList = datumList
                    for (i in dataArrayList?.indices!!) {
                        recyclerViewAdapter = CartAdapter(applicationContext,
                            dataArrayList!! as ArrayList<Chart>
                        )
                        val layoutManager = GridLayoutManager(applicationContext, 1)
                        binding!!.rvCart.layoutManager = layoutManager
                        binding!!.rvCart.adapter = recyclerViewAdapter

                    }


                }
            }

            override fun onFailure(call: Call<ResponseChart?>, t: Throwable) {
                call.cancel()
            }
        })
    }
}