package com.mina.dev.ra3eya_app.presentation.familydetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mina.dev.ra3eya_app.databinding.MemberNameItemBinding
import com.mina.dev.ra3eya_app.domain.model.MemberNameId

class MembersAdapter(private val onItemClickListener: ItemClickListener) :
    ListAdapter<MemberNameId, MembersAdapter.MyViewHolder>(MembersDiffUtilsCallback()) {

    inner class MyViewHolder(private val binding: MemberNameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(member: MemberNameId) {
            binding.member = member
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MemberNameItemBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onItemClickListener.onMemberItemClick(getItem(holder.adapterPosition)) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    interface ItemClickListener {
        fun onMemberItemClick(member: MemberNameId)
    }


}