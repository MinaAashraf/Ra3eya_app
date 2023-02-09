package com.mina.dev.ra3eya_app.presentation.memberdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentMemberDetailsBinding
import com.mina.dev.ra3eya_app.databinding.FragmentMemberFormBinding
import com.mina.dev.ra3eya_app.domain.model.CollectionsKeys
import com.mina.dev.ra3eya_app.presentation.familydetails.FamilyDetailsViewModel
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberDetailsFragment : Fragment() {
    private val binding by lazy { FragmentMemberDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MemberDetailsViewModel>()
    private lateinit var memberId: String
    private lateinit var churchId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getArgumentsData()
        viewModel.readMember( memberId = memberId, churchId = churchId)
        observeLiveData()
        return binding.root
    }

    private fun getArgumentsData() {
        arguments?.let {
            val keys =
                it.getParcelable<CollectionsKeys>(getString(R.string.collectionsKeys_key)) as CollectionsKeys
            memberId = keys.memberKey!!
            churchId = keys.churchKey!!
        }
    }

    private fun observeLiveData() {
        viewModel.apply {
            member.observe(viewLifecycleOwner) {
                it?.let {
                    binding.member = it
                    binding.progressBar.hide()
                    binding.groupLayout.show()
                }
            }
        }
    }


}