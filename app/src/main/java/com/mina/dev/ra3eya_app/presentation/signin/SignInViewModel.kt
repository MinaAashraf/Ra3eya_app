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
import com.mina.dev.ra3eya_app.domain.repository.ChurchRepository
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
    private val sharedPrefEditor: SharedPreferences.Editor,
    private val readAllChurchesUseCase: ReadAllChurchesUseCase,
) : ViewModel() {

    private val _succeeded = MutableLiveData<Pair<Boolean, String?>?>()
    val succeeded: LiveData<Pair<Boolean, String?>?> = _succeeded

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading

    val allChurches = readAllChurchesUseCase.execute()



    fun signIn(churchCredentials: ChurchCredentials, context: Context) : LiveData<Church> {
        // _loading.postValue(true)
        return signInUseCase.execute(churchCredentials)

        /*       church = it
            _succeeded.postValue(Pair(true, context.getString(R.string.sign_in_successfully_message)))
            _loading.postValue(false)

        } ?: kotlin.run {
            _succeeded.postValue(Pair(false,context.getString(R.string.wrong_password_msg) ))
            _loading.postValue(false)
        }*/
    }


    /*viewModelScope.launch {
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
    }*/


/*  private fun readAllChurches() {
      viewModelScope.launch {
          readAllChurchesUseCase.execute().onSuccess {
              withContext(Dispatchers.Main) {
                  _allChurches.value = it
              }
          }
              .onFailure {
                  Log.d("allChurchesErr", it.message.toString())
              }
      }
  }*/

    fun isAuthenticated(context: Context) =
        sharedPref.getBoolean(context.getString(R.string.authenticated_key), false)

    fun saveState(context: Context,churchId : String) {
        sharedPrefEditor.apply {
            putBoolean(context.getString(R.string.authenticated_key), true)
            putString(context.getString(R.string.church_id_key), churchId)
            apply()
        }
    }

    fun clearLiveData() {
        _succeeded.value = null
        _loading.value = null
    }


}