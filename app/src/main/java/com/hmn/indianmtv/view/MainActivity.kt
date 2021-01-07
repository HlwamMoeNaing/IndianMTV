package com.hmn.indianmtv.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.hmn.indianmtv.R
import com.hmn.indianmtv.adapter.RecyclerViewClickInterface
import com.hmn.indianmtv.room.BannerEntity
import com.hmn.indianmtv.util.DarkTheme
import com.hmn.indianmtv.viewmodel.ViewModelImplement
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener,RecyclerViewClickInterface {
    lateinit var mViewModel: ViewModelImplement
    lateinit var container: FrameLayout
    lateinit var switch: SwitchCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProvider(this)[ViewModelImplement::class.java]

        container = findViewById(R.id.fragment_container)

        val navigationView = findViewById<NavigationView>(R.id.nav_view_main)
        navigationView.setNavigationItemSelectedListener(this@MainActivity)

        val menu = navigationView.menu
        val menuItem = menu.findItem(R.id.nav_switch)
        val actionView = MenuItemCompat.getActionView(menuItem)

        switch = actionView.findViewById(R.id.switch_id)
        switch.setOnClickListener {
            if (switch.isChecked) {
                setDark()
                DarkTheme.apply(true)
                main_drawer_layout.closeDrawer(GravityCompat.START)


            } else {
                setDark()
                DarkTheme.apply(false)
                main_drawer_layout.closeDrawer(GravityCompat.START)

            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).addToBackStack(null)
            .commit()

//

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Info")
                builder.setMessage("This Is Blah Blah")
                builder.setCancelable(false)
                builder.setPositiveButton("Yes") { _, _ ->

                }
                builder.setNegativeButton("No") { _, _ ->


                }
                val dialog = builder.create()
                dialog.show()

            }
            R.id.favourite -> {
                loadFragment(FavouriteFragment())
            }


            R.id.nav_switch -> {
//                if (switch.isChecked) {
//                    item.setIcon(R.drawable.moon)
//                } else {
//                    item.setIcon(R.drawable.ic_round_day)
//                }

            }



        }
        main_drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }


    fun loadFragment(fragment: Fragment) {
        val fmgr = supportFragmentManager.beginTransaction()
        fmgr.replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
        return true
    }
    fun openCloseNavigationDrawer() {
        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            main_drawer_layout.openDrawer(GravityCompat.START)
        }
    }


    private fun setDark() {
        DarkTheme.isEnabled(this)
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
        super.onBackPressed()
    }

//    override fun onBackPressed() {
//
//        if (main_drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            main_drawer_layout.closeDrawer(GravityCompat.START)
//        } else {
//
//            //val fragmentId = supportFragmentManager.findFragmentById(R.id.fragment_container)
//            val fragmentId = supportFragmentManager.findFragmentById(R.id.home_callback_container)
//            val homeFragment = HomeFragment()
//
//
//            if (fragmentId is HomeFragment){
//
//                val builder = AlertDialog.Builder(this@MainActivity)
//                builder.setTitle("Exit")
//                builder.setMessage("Are you want to Exit?")
//                builder.setCancelable(false)
//                builder.setPositiveButton("Yes") { _, _ ->
//                    finish()
//                }
//                builder.setNegativeButton("No") { _, _ ->
//
//
//                }
//                val dialog = builder.create()
//                dialog.show()
//            }
//            else{
//                super.onBackPressed()
//
//            }
//
//        }
//
//    }

    override fun onDetailClick(position: Int, bannerEntity: BannerEntity) {
        TODO("Not yet implemented")
    }

    override fun onItemCategoryClick(position: Int, bannerEntity: BannerEntity) {
        TODO("Not yet implemented")
    }

}