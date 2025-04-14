package com.example.firstinubuntu

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.firstinubuntu.databinding.ActivityMainBinding
import com.example.firstinubuntu.fragments.HomeFragment
import com.example.firstinubuntu.fragments.LikeFragment
import com.example.firstinubuntu.fragments.NatureFragment
import com.example.firstinubuntu.fragments.RandomFragment
import com.example.firstinubuntu.fragments.WebFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

lateinit var binding: ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navigationView = binding.navigationView
        val menu = navigationView.menu
        val customItem = menu.findItem(R.id.item_custom)
        customItem.setActionView(R.layout.menu_item)


        val navController = findNavController(R.id.fragment_container)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavigationView.setupWithNavController(navController)



        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val show = AlertDialog.Builder(this@MainActivity)
                    .setMessage("Rostdan ham chiqishni istaysizmi?")
                    .setTitle("Ogohlantiramiz!")
                    .setPositiveButton("Ha") { _, _ -> finish() }
                    .setNegativeButton("Yo'q") { dialog, _ ->
                        dialog.dismiss()

                    }.show()
            }
        })

        binding.openIcon.setOnClickListener {
            binding.myDrawer.openDrawer(Gravity.START)
        }

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    binding.titleText.text = "Home"
                    binding.bottomNavView.visibility = View.VISIBLE
                }

                R.id.menu_popular -> {
                    loadFragment(NatureFragment())
                    binding.titleText.text = "Popular"
                    binding.bottomNavView.visibility = View.GONE
                }

                R.id.menu_random -> {
                    loadFragment(RandomFragment())
                    binding.titleText.text = "Random"
                    binding.bottomNavView.visibility = View.GONE
                }

                R.id.menu_liked -> {
                    loadFragment(LikeFragment())
                    binding.titleText.text = "Liked"
                    binding.bottomNavView.visibility = View.GONE
                }

                R.id.menu_about -> {
                    replaceFragment(WebFragment(), 1)
                    binding.titleText.text = "About"
                    binding.bottomNavView.visibility = View.GONE
                }

                R.id.item_custom -> {
                    loadFragment(WebFragment())
                    binding.titleText.text = "Sourse"
                    binding.bottomNavView.visibility = View.GONE
                }
            }

            binding.myDrawer.closeDrawer(GravityCompat.START)
            true
        }


    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment, data: Int) {
        val bundle = Bundle().apply { putInt("keys", data) }
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun loadFragment(fr: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fr)
            .commit()
    }


    override fun onBackPressed() {
        if (binding.myDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.myDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}