package com.app.tiktok.ui.main.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.tiktok.R
import com.app.tiktok.base.BaseActivity
import com.app.tiktok.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            val navController = findNavController(R.id.nav_host_fragment)
            navView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener(this@MainActivity)
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        with(binding) {
            when (destination.id) {
                R.id.navigation_home -> {
                    changeStatusBarColor(R.color.colorBlack)
                    val colorDark = ContextCompat.getColorStateList(
                        this@MainActivity,
                        R.color.bottom_tab_selector_item_dark
                    )

                    val colorBlack = ContextCompat.getColorStateList(
                        this@MainActivity,
                        R.color.colorBlack
                    )

                    navView.backgroundTintList = colorBlack
                    navView.itemTextColor = colorDark
                    navView.itemIconTintList = colorDark
                    imageViewAddIcon.setImageResource(R.drawable.ic_add_icon_light)
                }
                else -> {
                    changeStatusBarColor(R.color.colorWhite)
                    val colorDark = ContextCompat.getColorStateList(
                        this@MainActivity,
                        R.color.bottom_tab_selector_item_light
                    )

                    val colorWhite = ContextCompat.getColorStateList(
                        this@MainActivity,
                        R.color.colorWhite
                    )

                    navView.backgroundTintList = colorWhite
                    navView.itemTextColor = colorDark
                    navView.itemIconTintList = colorDark
                    imageViewAddIcon.setImageResource(R.drawable.ic_add_icon_dark)
                }
            }
        }
    }
}