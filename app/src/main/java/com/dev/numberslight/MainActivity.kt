package com.dev.numberslight

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.dev.numberslight.numbers.NumbersFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.list_fragment, NumbersFragment())
                .commit()
        }
    }
}