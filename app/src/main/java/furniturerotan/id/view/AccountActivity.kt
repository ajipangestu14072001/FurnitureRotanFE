package furniturerotan.id.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import furniturerotan.id.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private var binding : ActivityAccountBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        val view : View =binding!!.root
        setContentView(view)
        setSupportActionBar(binding!!.toolbar)
        val sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        binding!!.nameAccount.text = username
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}