package furniturerotan.id

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        fullScreen(this)
    }
//    fun fullScreen(activity: Activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            if (activity.window.insetsController != null) {
//                val insetsController = activity.window.insetsController
//                if (insetsController != null) {
//                    insetsController.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
//                    insetsController.systemBarsBehavior =
//                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                }
//            }
//        } else {
//            activity.window.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
//}
}