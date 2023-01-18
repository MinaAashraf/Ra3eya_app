package com.mina.dev.ra3eya_app.presentation.signin

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.usecases.ReadAllChurchesUseCase
import com.mina.dev.ra3eya_app.domain.usecases.SignInUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val sharedPref: SharedPreferences,
    private val readAllChurchesUseCase: ReadAllChurchesUseCase
) : ViewModel() {

    private val _succeeded = MutableLiveData<Pair<Boolean, String?>?>()
    val succeeded: LiveData<Pair<Boolean, String?>?> = _succeeded

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading

    private val _allChurches = MutableLiveData<List<Church>>()
    val allChurches: LiveData<List<Church>> = _allChurches

    var church = Church()

    init {
        readAllChurches()
    }

    fun signIn(churchCredentials: ChurchCredentials) {
        _loading.postValue(true)
        viewModelScope.launch {
            signInUseCase.execute(churchCredentials).onSuccess {
                church = it
                withContext(Dispatchers.Main) {
                    _succeeded.postValue(Pair(true, "تم الدخول بنجاح"))
                    _loading.postValue(false)
                }

            }.onFailure {
                withContext(Dispatchers.Main) {
                    _succeeded.postValue(Pair(false, it.message.toString()))
                    _loading.postValue(false)
                }
            }
        }
    }

    private fun readAllChurches() {
        viewModelScope.launch {
            readAllChurchesUseCase.execute().onSuccess {
                withContext(Dispatchers.Main) {
                    _allChurches.value = it
                }
            }
                .onFailure {
                    Log.d("allChurchesErr",it.message.toString() )
                }
        }
    }

    fun isAuthenticated(context: Context) =
        sharedPref.getBoolean(context.getString(R.string.authenticated_key), false)

    fun clearLiveData() {
        _succeeded.value = null
        _loading.value = null
    }


}