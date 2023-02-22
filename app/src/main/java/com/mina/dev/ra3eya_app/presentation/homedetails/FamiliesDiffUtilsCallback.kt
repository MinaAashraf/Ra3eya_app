package com.mina.dev.ra3eya_app.presentation.homedetails

import androidx.recyclerview.widget.DiffUtil
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId

class FamiliesDiffUtilsCallback : DiffUtil.ItemCallback<Family>() {
    override fun areItemsTheSame(oldItem: Family, newItem: Family): Boolean =
        oldItem.familyName == newItem.familyName && oldItem.homeId == newItem.homeId

    override fun areContentsTheSame(oldItem: Family, newItem: Family): Boolean = oldItem == newItem

}