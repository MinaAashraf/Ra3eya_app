package com.mina.dev.ra3eya_app.presentation.homeform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.usecases.InsertHomeUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val insertHomeUseCase: InsertHomeUseCase) :
    ViewModel() {

    private val _succeeded = MutableLiveData<Pair<Boolean, String?>?>()
    val succeeded: LiveData<Pair<Boolean, String?>?> = _succeeded

    private val _loading = MutableLiveData<Boolean?>()
    val loading: LiveData<Boolean?> = _loading


    var home: Home = Home()
    fun insertHome(homeName: String, homeFamiliesNo:Int, churchId : String, addressLine:String) {
        _loading.value = true
        setHomeData(homeName,homeFamiliesNo,churchId,addressLine)
        viewModelScope.launch(Dispatchers.IO) {
            insertHomeUseCase.execute(home).onSuccess {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                    _succeeded.value = Pair(true, it)
                }
            }.onFailure {
                withContext(Dispatchers.Main){
                    _loading.value = false
                    _succeeded.value = Pair(false, null)
                }

            }

        }
    }

   private fun setHomeData (homeName: String, homeFamiliesNo:Int, homeChurchId : String, homeAddressLine:String){
        home.apply {
            name = homeName
            familiesNo = homeFamiliesNo
            churchId = homeChurchId
            addressLine = homeAddressLine
        }
    }


}