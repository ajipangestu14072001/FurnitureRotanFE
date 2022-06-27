package furniturerotan.id.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import furniturerotan.id.MainActivity
import furniturerotan.id.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    private val mHandler = Handler(Looper.getMainLooper())
    private val mLauncher: Launcher = Launcher()

    override fun onStart() {
        super.onStart()
        mHandler.postDelayed(mLauncher, SPLASH_DELAY.toLong())
    }

    override fun onStop() {
        mHandler.removeCallbacks(mLauncher)
        super.onStop()
    }

    private fun launch() {
        if (!isFinishing) {
            startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            finish()
        }
    }

    private inner class Launcher : Runnable {
        override fun run() {
            launch()
        }
    }

    companion object {
        private const val SPLASH_DELAY = 1000
        private const val TAG = "MainActivity"
    }
}