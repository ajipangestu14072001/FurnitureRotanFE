package furniturerotan.id.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import furniturerotan.id.databinding.ActivityDetailHistoryBinding
import furniturerotan.id.model.DataX

class DetailHistoryActivity : AppCompatActivity() {
    private var binding : ActivityDetailHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        val view : View = binding!!.root
        setContentView(view)
    }
}