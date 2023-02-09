package com.mina.dev.ra3eya_app.presentation.familydetails

import androidx.recyclerview.widget.DiffUtil
import com.mina.dev.ra3eya_app.domain.model.MemberNameId

class MembersDiffUtilsCallback : DiffUtil.ItemCallback<MemberNameId>() {
    override fun areItemsTheSame(oldItem: MemberNameId, newItem: MemberNameId): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MemberNameId, newItem: MemberNameId): Boolean = oldItem == newItem
}