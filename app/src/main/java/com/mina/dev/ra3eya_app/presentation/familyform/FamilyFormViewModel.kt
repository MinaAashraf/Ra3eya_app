package com.mina.dev.ra3eya_app.presentation.familyform

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.usecases.AddMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyFormViewModel @Inject constructor(
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {


    fun addMember(uri: Uri?, member: Member) {
        viewModelScope.launch {
            addMemberUseCase.execute(uri, member)
        }
    }


}