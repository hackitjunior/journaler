package com.hackitjunior.journaler.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem
import com.hackitjunior.journaler.R
import com.hackitjunior.journaler.fragment.ItemsFragment
import com.hackitjunior.journaler.navigation.NavigationDrawerAdapter
import com.hackitjunior.journaler.navigation.NavigationDrawerItem
import com.hackitjunior.journaler.preferences.PreferencesConfiguration
import com.hackitjunior.journaler.preferences.PreferencesProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val tag = "Main activity"
    private val keyPagePosition = "keyPagePosition"

    override fun getLayout() = R.layout.activity_main
    override fun getActivityTitle() = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = PreferencesProvider()
        val config = PreferencesConfiguration("journaler_prefs", Context.MODE_PRIVATE)
        val preferences = provider.obtain(config, this)

        pager.adapter = ViewPagerAdapter(supportFragmentManager)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Ignore
            }

            override fun onPageScrolled(position: Int, positionOffset:
            Float, positionOffsetPixels: Int) {
                // Ignore
            }

            override fun onPageSelected(position: Int) {
                Log.v(tag, "Page [ $position ]")
                preferences.edit().putInt(keyPagePosition, position).apply()
            }
        })

        val pagerPosition = preferences.getInt(keyPagePosition, 0)
        pager.setCurrentItem(pagerPosition, true)

        // Menu items creation
        val menuItems = mutableListOf<NavigationDrawerItem>()

        val today = NavigationDrawerItem(
            getString(R.string.today),
            Runnable {
                pager.setCurrentItem(0, true)
            }
        )

        val next7Days = NavigationDrawerItem(
            getString(R.string.next_seven_days),
            Runnable {
                pager.setCurrentItem(1, true)
            }
        )

        val todos = NavigationDrawerItem(
            getString(R.string.todos),
            Runnable {
                pager.setCurrentItem(2, true)
            }
        )

        val notes = NavigationDrawerItem(
            getString(R.string.notes),
            Runnable {
                pager.setCurrentItem(3, true)
            }
        )

        menuItems.add(today)
        menuItems.add(next7Days)
        menuItems.add(todos)
        menuItems.add(notes)

        val navgationDraweAdapter = NavigationDrawerAdapter(this, menuItems)
        left_drawer.adapter = navgationDraweAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawing_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.options_menu -> {
                Log.v(tag, "Options menu.")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
            return ItemsFragment()
        }

        override fun getCount(): Int {
            return 5
        }
    }
}