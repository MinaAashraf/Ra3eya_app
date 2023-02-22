package com.mina.dev.ra3eya_app.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)
        viewModel.refreshChurches()
        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.loading.observe(this) {
            it?.let {
                if (!it) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        delay(2000)
                        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

}