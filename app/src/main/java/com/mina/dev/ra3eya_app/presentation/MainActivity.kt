package com.mina.dev.ra3eya_app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mina.dev.ra3eya_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}