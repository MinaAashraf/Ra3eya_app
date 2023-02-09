package com.mina.dev.ra3eya_app.presentation.homedetails

import androidx.recyclerview.widget.DiffUtil
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId

class FamiliesDiffUtilsCallback : DiffUtil.ItemCallback<FamilyNameId>() {
    override fun areItemsTheSame(oldItem: FamilyNameId, newItem: FamilyNameId): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FamilyNameId, newItem: FamilyNameId): Boolean = oldItem == newItem
}