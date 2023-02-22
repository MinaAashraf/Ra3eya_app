package com.mina.dev.ra3eya_app.presentation.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val churchRepository: ChurchRepository) :
    ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    fun refreshChurches() {
        viewModelScope.launch(Dispatchers.IO) {
            churchRepository.refreshChurches().onSuccess {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }.onFailure {
                Log.d("churches error", it.message.toString())
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }

        }
    }


}