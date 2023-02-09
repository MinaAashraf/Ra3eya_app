package com.mina.dev.ra3eya_app.presentation.homedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mina.dev.ra3eya_app.databinding.FamilyNameItemBinding
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId

class FamiliesAdapter(private val onItemClickListener: ItemClickListener) :
    ListAdapter<FamilyNameId, FamiliesAdapter.MyViewHolder>(FamiliesDiffUtilsCallback()) {

    inner class MyViewHolder(private val binding: FamilyNameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(family: FamilyNameId) {
            binding.family = family
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FamilyNameItemBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onItemClickListener.onFamilyItemClick(getItem(holder.adapterPosition)) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    interface ItemClickListener {
        fun onFamilyItemClick(familyNameId: FamilyNameId)
    }


}