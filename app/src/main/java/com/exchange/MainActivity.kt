package com.exchange

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.exchange.ui.ExchangeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Frankly, fragment is not required for this task.
        // But for the sake of demonstration and Single Activity hype...
        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(R.id.container, ExchangeFragment.newInstance())
            }
        }
    }
}
