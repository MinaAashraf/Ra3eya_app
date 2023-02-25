package com.mina.dev.ra3eya_app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.mina.dev.ra3eya_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navigationHost.navController
        navigationHost.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title =
                when (destination.id) {
                    R.id.signUpFragment -> "اضافة كنيسة جديدة"
                    R.id.homeFormFragment -> "اضافة منزل جديد"
                    R.id.homeDetailsFragment -> "بيانات المنزل"
                    R.id.familyDetailsFragment -> "بيانات الأسرة"
                    R.id.memberFormFragment -> "اضافة فرد للأسرة"
                    R.id.memberDetailsFragment -> "بيانات الفرد"
                    else -> "الرعـــية"
                }

        }

    }
}