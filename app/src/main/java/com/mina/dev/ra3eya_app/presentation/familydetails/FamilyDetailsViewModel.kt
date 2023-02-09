package com.mina.dev.ra3eya_app.presentation.familydetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.usecases.ReadFamilyUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FamilyDetailsViewModel @Inject constructor(private val readFamilyDetailsUseCase: ReadFamilyUseCase) :
    ViewModel() {


    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _success = MutableLiveData<Boolean?>(null)
    val success: LiveData <Boolean?> = _success


    private val _family = MutableLiveData<Family>()
    val family: LiveData<Family> = _family



    fun readFamily(context: Context, familyId: String, churchId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            readFamilyDetailsUseCase.execute(familyId, churchId).onSuccess {
                withContext(Dispatchers.Main) {
                    _family.value = it
                    _success.value = true
                    _message.value = context.getString(R.string.family_adding_success_message)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    _message.value = it.message
                    _success.value = false
                }
            }

        }
    }


}