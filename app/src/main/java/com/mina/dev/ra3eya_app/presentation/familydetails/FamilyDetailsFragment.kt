package com.mina.dev.ra3eya_app.presentation.familydetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentFamilyDetailsBinding
import com.mina.dev.ra3eya_app.domain.model.*
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyDetailsFragment : Fragment(), MembersAdapter.ItemClickListener {

    private val binding by lazy { FragmentFamilyDetailsBinding.inflate(layoutInflater) }
    private lateinit var family: Family
    private lateinit var familyName: String
    private lateinit var homeId: String

    private val viewModel by viewModels<FamilyDetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getArgumentsData()
        readFamily(requireContext(), familyName = familyName, homeId = homeId)
        return binding.root
    }


    private fun getArgumentsData() {
        arguments?.let {
            if (it.containsKey(getString(R.string.family_key))) {
                family = it.getParcelable<Family>(getString(R.string.family_key)) as Family
                familyName = family.familyName
                homeId = family.homeId
            } else {
                familyName = it.getString(getString(R.string.family_name_key), "")
                homeId = it.getString(getString(R.string.homeId_key), "")
            }
        }
    }

    private fun setUi() {
        setFamilyAddress()
        setMembersRecyclerView()
        handleAddMemberBtn()
    }

    private fun setFamilyAddress() {
        binding.homeAddressText.text = family.familyAddress
    }

    private fun handleAddMemberBtn() {
        binding.addMemberBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_familyDetailsFragment_to_memberFormFragment,
                Bundle().apply {
                    putString(getString(R.string.family_name_key), family.familyName)
                    putString(getString(R.string.home_address_key), family.familyAddress)
                    putParcelable(
                        getString(R.string.collectionsKeys_key),
                        CollectionsKeys(
                            churchKey = family.churchId,
                            familyKey = family.familyName,
                            homeKey = family.homeId
                        )
                    )
                }
            )
        }
    }

    private fun readFamily(context: Context, familyName: String, homeId: String) {
        viewModel.readFamily(context, familyName, homeId).observe(viewLifecycleOwner) {
            it?.let {
                family = it
                setUi()
                viewModel.readFamilyMembers(familyName, homeId).observe(viewLifecycleOwner) {
                    it?.let {
                        if (it.isNotEmpty()) {
                            membersAdapter.submitList(it)
                            binding.progressBar.hide()
                            binding.notExistMemberLabel.hide()
                        } else
                            binding.notExistMemberLabel.show()
                    } ?: kotlin.run {
                        binding.notExistMemberLabel.show()
                    }
                }
            }
        }
    }

    private lateinit var membersAdapter: MembersAdapter;
    private fun setMembersRecyclerView() {
        membersAdapter = MembersAdapter(this)
        binding.membersRecyclerView.adapter = membersAdapter
    }


    override fun onMemberItemClick(member: Member) {
        findNavController().navigate(
            R.id.action_familyDetailsFragment_to_memberDetailsFragment,
            Bundle().apply {
                putParcelable(getString(R.string.member_key), member)
            }
        )
    }


}