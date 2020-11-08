package com.dev.numberslight

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.dev.numberslight.detail.DetailFragment
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.numbers.NumbersFragment

class MainActivity : FragmentActivity(), NumbersFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleOrientation()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, NumbersFragment(), NumbersFragment.TAG)
                .commit()
        }
    }

    private fun handleOrientation() {
        if (isLandscapeOrientation()) {
            val detailFragment = supportFragmentManager.findFragmentByTag(DetailFragment.TAG)

            if (detailFragment == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.detail_fragment, DetailFragment.newInstance(null), DetailFragment.TAG)
                    .commit()
            }

            val fragmentInContainerView = supportFragmentManager.findFragmentById(R.id.container)

            if (isSwitchingToLandscapeFromDetailView(fragmentInContainerView)) {
                supportFragmentManager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                moveDetailFragment(fragmentInContainerView)
                moveNumbersFragment()
            }
        }
    }

    private fun isLandscapeOrientation() = findViewById<FrameLayout>(R.id.detail_fragment) != null

    private fun isSwitchingToLandscapeFromDetailView(fragmentInContainerView: Fragment?) =
        fragmentInContainerView is DetailFragment

    private fun moveDetailFragment(fragmentInContainerView: Fragment?) {
        fragmentInContainerView?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.detail_fragment, it, DetailFragment.TAG)
                .commit()
        }
    }

    private fun moveNumbersFragment() {
        val numbersFragment = supportFragmentManager.findFragmentByTag(NumbersFragment.TAG)

        if (numbersFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, numbersFragment, NumbersFragment.TAG)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, NumbersFragment(), NumbersFragment.TAG)
                .commit()
        }
    }

    override fun onNumberClick(number: NumberLight) {
        if (isLandscapeOrientation()) {
            val detailFragment: DetailFragment? =
                supportFragmentManager.findFragmentById(R.id.detail_fragment) as DetailFragment
            detailFragment?.getDetail(number.name)
        } else {
            val fragment = supportFragmentManager.findFragmentByTag(DetailFragment.TAG)
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .remove(it)
                    .commit()
            }
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    DetailFragment.newInstance(number.name),
                    DetailFragment.TAG
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun shouldHighlight(): Boolean {
        return isLandscapeOrientation()
    }
}