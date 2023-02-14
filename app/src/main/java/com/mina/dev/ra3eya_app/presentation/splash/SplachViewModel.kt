package com.mina.dev.ra3eya_app.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val churchRepository: ChurchRepository) :
    ViewModel() {


    init {
        viewModelScope.launch(Dispatchers.IO) {
            churchRepository.refreshChurches()
        }
    }

}