package furniturerotan.id.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import furniturerotan.id.R
import furniturerotan.id.adapter.AdapterHome
import furniturerotan.id.adapter.SliderAdapter
import furniturerotan.id.databinding.ActivityHomeBinding
import furniturerotan.id.helper.SharedPrefManager
import furniturerotan.id.helper.SliderItems
import furniturerotan.id.model.Data
import furniturerotan.id.response.Chart
import furniturerotan.id.response.ResponseChart
import furniturerotan.id.response.ResponseDataObject
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import furniturerotan.id.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.ArrayList
import kotlin.math.abs

class HomeActivity : AppCompatActivity() {
    var adapter: AdapterHome? = null
    private val handler = Handler()
    private var sharedPrefManager: SharedPrefManager? = null
    private var dataArrayList: List<Chart>? = null

    private var  binding : ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        fullScreen(this)
        sharedPrefManager = SharedPrefManager(applicationContext)
        val imageList: MutableList<SliderItems> = ArrayList()
        binding!!.recyclerView.setHasFixedSize(true)
        binding!!.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        adapter = AdapterHome(applicationContext)
        binding!!.recyclerView.adapter = adapter

        binding!!.imageProfile.setOnClickListener{
            if (!sharedPrefManager!!.sPSudahLogin) {
                startActivity(
                    Intent(
                        applicationContext,
                        AuthActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            } else {
                val intent = Intent(applicationContext, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        imageList.add(SliderItems(R.drawable.bg_banner))
        imageList.add(SliderItems(R.drawable.bg_banner))
        imageList.add(SliderItems(R.drawable.bg_banner))
        imageList.add(SliderItems(R.drawable.bg_banner))
        val imageAdapter = SliderAdapter(imageList, binding!!.viewPagerImageSlider)
        binding!!.viewPagerImageSlider.adapter = imageAdapter
        binding!!.viewPagerImageSlider.offscreenPageLimit = 3
        binding!!.viewPagerImageSlider.clipChildren = false
        binding!!.viewPagerImageSlider.clipToPadding = false
        binding!!.viewPagerImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(65))
        compositePageTransformer.addTransformer { page: View, position: Float ->
            val r = 1 - abs(position)
            page.scaleX = 0.90f + r * 0.25f
        }
        binding!!.viewPagerImageSlider.setPageTransformer(compositePageTransformer)
        binding!!.viewPagerImageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000)
            }
        })

        binding!!.keranjang.setOnClickListener {
            if (!sharedPrefManager!!.sPSudahLogin) {
                startActivity(
                    Intent(
                        applicationContext,
                        AuthActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            } else {
                val intent = Intent(applicationContext, ChartDetailActivity::class.java)
                startActivity(intent)
            }
        }
        getCart()

        binding!!.histori.setOnClickListener {
            if (!sharedPrefManager!!.sPSudahLogin) {
                startActivity(
                    Intent(
                        applicationContext,
                        AuthActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            } else {
                val intent = Intent(applicationContext, HistoryActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun getCart(){
        val sharedPreferences1 = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences1.getString("token", "")
        val idUsers = sharedPreferences1.getString("userId", "")
        val apiInterface = APIClient().getClient(applicationContext).create(APIInterface::class.java)
        val call: Call<ResponseChart?>? =
            apiInterface.getChartIdUsers("Bearer $cookie", idUsers)
        call?.enqueue(object : Callback<ResponseChart?> {
            override fun onResponse(
                call: Call<ResponseChart?>,
                response: Response<ResponseChart?>
            ) {
                try {
                    dataArrayList = response.body()!!.data
                    if (response.isSuccessful) {
                        binding!!.txtChart.text = "Keranjang (${dataArrayList?.size})"
                    }
                }catch (e : Exception){
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ResponseChart?>, t: Throwable) {
                call.cancel()
            }
        })
    }

    private val runnable = Runnable {
        binding!!.viewPagerImageSlider.currentItem = binding!!.viewPagerImageSlider.currentItem + 1
    }

    public override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    public override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
        getCart()
    }

    private fun fullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (activity.window.insetsController != null) {
                val insetsController = activity.window.insetsController
                if (insetsController != null) {
                    insetsController.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    insetsController.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
}