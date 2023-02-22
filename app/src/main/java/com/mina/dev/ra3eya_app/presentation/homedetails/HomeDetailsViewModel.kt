package com.mina.dev.ra3eya_app.presentation.homedetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.usecases.AddFamilyUseCase
import com.mina.dev.ra3eya_app.domain.usecases.ReadFamiliesOfHomeUseCase
import com.mina.dev.ra3eya_app.domain.usecases.ReadFamiliesUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeDetailsViewModel @Inject constructor(
    private val addFamilyUseCase: AddFamilyUseCase,
    private val readFamiliesOfHomeUseCase : ReadFamiliesOfHomeUseCase,
) :
    ViewModel() {

    private val _familyResult = MutableLiveData<FamilyNameId?>()
    val familyResult: LiveData<FamilyNameId?> = _familyResult

    private val _loading = MutableLiveData<Pair<Boolean, String?>>(null)
    var loading: LiveData<Pair<Boolean, String?>> = _loading

    fun addFamily(context: Context, family: Family) {
        _loading.value = Pair(true, null)
        viewModelScope.launch(Dispatchers.IO) {
            addFamilyUseCase.addFamily(family).onSuccess {
                withContext(Dispatchers.Main) {
                    _familyResult.value = it
                    _loading.value =
                        Pair(false, context.getString(R.string.family_adding_success_message))
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    _loading.value =
                        Pair(false, context.getString(R.string.family_adding_failure_message))
                }
            }
        }
    }

    fun getHomeFamilies(homeId: String): LiveData<List<Family>> = readFamiliesOfHomeUseCase.execute(homeId)


}