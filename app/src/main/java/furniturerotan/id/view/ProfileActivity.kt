package furniturerotan.id.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import furniturerotan.id.databinding.ActivityProfileBinding
import furniturerotan.id.helper.SharedPrefManager
import furniturerotan.id.home.HomeActivity
import furniturerotan.id.model.LogOut
import furniturerotan.id.model.Login
import furniturerotan.id.response.MessageResponse
import furniturerotan.id.services.APIClient
import furniturerotan.id.services.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private var binding : ActivityProfileBinding? = null
    var sharedPrefManager: SharedPrefManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
        setSupportActionBar(binding!!.toolbar)
        sharedPrefManager = SharedPrefManager(this)
        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val cookie = sharedPreferences.getString("token", "")
        val id = sharedPreferences.getString("userId", "")
        val logOut = LogOut(id)
        binding!!.card3.setOnClickListener {
            val apiInterface =
                APIClient().getClient(applicationContext).create(APIInterface::class.java)

            val call: Call<MessageResponse?>? =
                apiInterface.logout("Bearer $cookie", logOut)
            call?.enqueue(object : Callback<MessageResponse?> {
                override fun onResponse(
                    call: Call<MessageResponse?>,
                    response: Response<MessageResponse?>
                ) {
                    Toast.makeText(
                        applicationContext,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    PreferenceManager.getDefaultSharedPreferences(applicationContext).edit().clear().apply()
                    sharedPrefManager!!.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false)
                    startActivity(
                        Intent(
                            this@ProfileActivity,
                            HomeActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                    finish()
                }

                override fun onFailure(call: Call<MessageResponse?>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        binding!!.card1.setOnClickListener {
            val intent = Intent(applicationContext, AccountActivity::class.java)
            startActivity(intent)
        }

        binding!!.card2.setOnClickListener {
            val intent = Intent(applicationContext, BantuanActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}