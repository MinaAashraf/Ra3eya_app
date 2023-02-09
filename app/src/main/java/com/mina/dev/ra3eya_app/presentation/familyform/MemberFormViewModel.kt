package com.mina.dev.ra3eya_app.presentation.familyform

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.model.MemberNameId
import com.mina.dev.ra3eya_app.domain.usecases.AddMemberUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemberFormViewModel @Inject constructor(
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _memberResult = MutableLiveData<MemberNameId?>(null)
    val memberResult: LiveData<MemberNameId?> = _memberResult


    fun addMember(uri: Uri?, member: Member) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            addMemberUseCase.execute(uri, member).onSuccess {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                    _memberResult.value = it
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }
        }
    }


}