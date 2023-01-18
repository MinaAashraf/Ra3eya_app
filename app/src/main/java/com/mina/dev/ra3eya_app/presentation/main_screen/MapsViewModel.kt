package com.mina.dev.ra3eya_app.presentation.main_screen

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.usecases.ReadChurchUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val readChurchUseCase: ReadChurchUseCase,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _churchData: MutableLiveData<Church> = MutableLiveData()
    val churchData: LiveData<Church> = _churchData


    fun readChurch(context: Context) {
        val churchId = getStoredChurchId(context)
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            readChurchUseCase.execute(churchId!!)
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        _churchData.value = it
                        _loading.value = false
                    }
                }.onFailure {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                    }
                }
        }

    }

    fun setChurch(church: Church) {
        _churchData.value = church
    }


    private fun getStoredChurchId(context: Context): String? {
        if (sharedPreferences.contains(context.getString(R.string.church_id_key)))
            return sharedPreferences.getString(context.getString(R.string.church_id_key), "")
        return null
    }


}