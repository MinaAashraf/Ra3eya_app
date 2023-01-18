package com.mina.dev.ra3eya_app.presentation.signup

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.usecases.AddChurchUseCase
import com.mina.dev.ra3eya_app.domain.usecases.SignInUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val addChurchUseCase: AddChurchUseCase,private val sharedPreferences: SharedPreferences.Editor) :
    ViewModel() {

    private val _succeeded = MutableLiveData<Pair<Boolean, String?>?>()
    val succeeded: LiveData<Pair<Boolean, String?>?> = _succeeded

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading

    var church = Church()


    fun addChurch(context: Context) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            addChurchUseCase.execute(church)
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        saveState(context)
                        _succeeded.postValue(Pair(true, "تم الاضافة بنجاح"))
                        _loading.postValue(false)
                    }
                }
                .onFailure {
                    withContext(Dispatchers.Main) {
                        _succeeded.postValue(Pair(false, it.message))
                        _loading.postValue(false)
                    }
                }
        }
    }

    private fun saveState(context: Context) {
         sharedPreferences.apply {
            putBoolean(context.getString(R.string.authenticated_key), true)
            putString(context.getString(R.string.church_id_key),church.id)
            apply()
        }
    }


    fun clearLiveData() {
        _succeeded.value = null
        _loading.value = null
    }


}