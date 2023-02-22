package com.mina.dev.ra3eya_app.presentation.memberdetails

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.usecases.ReadMemberUseCase
import com.mina.dev.ra3eya_app.domain.util.onFailure
import com.mina.dev.ra3eya_app.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemberDetailsViewModel @Inject constructor(private val readMemberUseCase: ReadMemberUseCase) :
    ViewModel() {

    var member: LiveData<Member> = MutableLiveData<Member>(null)


    fun readMember(memberName: String, homeId: String) =
        readMemberUseCase.execute(memberName, homeId)
    /*viewModelScope.launch(Dispatchers.IO) {
        readMemberUseCase.execute(memberId, churchId)
            .onSuccess {
                withContext(Dispatchers.Main) {
                    _member.value = it
                }
            }
            .onFailure {
            }
    }*/


}