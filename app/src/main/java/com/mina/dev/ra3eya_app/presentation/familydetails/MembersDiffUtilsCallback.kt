package com.mina.dev.ra3eya_app.presentation.familydetails

import androidx.recyclerview.widget.DiffUtil
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.domain.model.MemberNameId

class MembersDiffUtilsCallback : DiffUtil.ItemCallback<Member>() {
    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean = oldItem.key == newItem.key

    override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean = oldItem == newItem

}