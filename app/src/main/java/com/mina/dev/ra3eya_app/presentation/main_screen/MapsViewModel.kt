package com.mina.dev.ra3eya_app.presentation.main_screen

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.usecases.*
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
    readHomesUseCase: ReadHomesUseCase,
    readFamiliesUseCase: ReadFamiliesUseCase,
    readAllMemberUseCase: ReadAllMembersUseCase,
    private val refreshHomesUseCase: RefreshHomesUseCase,
    private val refreshMembersUseCase: RefreshMembersUseCase,
    private val refreshFamiliesUseCase: RefreshFamiliesUseCase,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {


    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _churchData: MutableLiveData<Church> = MutableLiveData()
    val churchData: LiveData<Church> = _churchData

    var homes: LiveData<List<Home>> = readHomesUseCase.execute()
    var families: LiveData<List<Family>> = readFamiliesUseCase.execute()
    var members: LiveData<List<Member>> = readAllMemberUseCase.execute()


    fun readChurch(context: Context) {
        val churchId = getStoredChurchId(context)
        viewModelScope.launch(Dispatchers.IO) {
            readChurchUseCase.execute(churchId!!)
                .onSuccess {
                    withContext(Dispatchers.Main) {
                        _churchData.value = it
                    }
                }.onFailure {
                    withContext(Dispatchers.Main) {
                    }
                }
        }

    }

    fun refreshHomes(context: Context) {
        _loading.value = true
        val churchId = getStoredChurchId(context)
        viewModelScope.launch(Dispatchers.IO) {
            refreshHomesUseCase.execute(churchId!!).onSuccess {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }.onFailure {
                _loading.value = false
            }
        }

    }

    fun refreshFamilies(context: Context) {
        val churchId = getStoredChurchId(context)
        viewModelScope.launch(Dispatchers.IO) {
            refreshFamiliesUseCase.execute(churchId!!)
        }
    }

    fun refreshMembers(context: Context) {
        val churchId = getStoredChurchId(context)
        viewModelScope.launch(Dispatchers.IO) {
            refreshMembersUseCase.execute(churchId!!)
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