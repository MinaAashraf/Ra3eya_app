package com.mina.dev.ra3eya_app.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mina.dev.ra3eya_app.R
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)


        lifecycleScope.launch(Dispatchers.IO){

        }


    }
}