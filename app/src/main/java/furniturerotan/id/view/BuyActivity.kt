package furniturerotan.id.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import furniturerotan.id.databinding.ActivityBuyBinding

class BuyActivity : AppCompatActivity() {
    private var binding: ActivityBuyBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
    }
}