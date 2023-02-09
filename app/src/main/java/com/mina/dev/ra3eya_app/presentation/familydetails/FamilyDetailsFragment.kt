package com.mina.dev.ra3eya_app.presentation.familydetails

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentFamilyDetailsBinding
import com.mina.dev.ra3eya_app.domain.model.CollectionsKeys
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.model.MemberNameId
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyDetailsFragment : Fragment(), MembersAdapter.ItemClickListener {

    private val binding by lazy { FragmentFamilyDetailsBinding.inflate(layoutInflater) }
    private lateinit var familyNameId: FamilyNameId
    private lateinit var home: Home
    private val viewModel by viewModels<FamilyDetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getArgumentsData()
        setUi()
        readFamily(requireContext(),familyId =  familyNameId.id, churchId = home.churchId!!)
        observeLiveData()
        return binding.root
    }

    private fun getArgumentsData() {
        arguments?.let {
            familyNameId =
                it.getParcelable<FamilyNameId>(getString(R.string.familyNameId_key)) as FamilyNameId
            home = it.getParcelable<Home>(getString(R.string.home_key)) as Home
        }
    }

    private fun setUi() {
        setFamilyAddress()
        setMembersRecyclerView()
        handleAddMemberBtn()
    }

    private fun setFamilyAddress() {
        binding.homeAddressText.text = home.detailedAddress
    }

    private fun handleAddMemberBtn() {
        binding.addMemberBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_familyDetailsFragment_to_memberFormFragment,
                Bundle().apply {
                    putString(getString(R.string.family_name_key), familyNameId.name)
                    putString(getString(R.string.home_address_key), home.detailedAddress)
                    putParcelable(
                        getString(R.string.collectionsKeys_key),
                        CollectionsKeys(
                            churchKey = home.churchId,
                            familyKey = familyNameId.id,
                            homeKey = home.homeId
                        )
                    )
                }
            )
        }
    }

    private fun readFamily(context: Context, familyId: String, churchId: String) {
        viewModel.readFamily(context, familyId, churchId)
    }

    private lateinit var membersAdapter: MembersAdapter;
    private fun setMembersRecyclerView() {
        membersAdapter = MembersAdapter(this)
        binding.membersRecyclerView.adapter = membersAdapter
    }


    private fun observeLiveData() {
        viewModel.apply {
            family.observe(viewLifecycleOwner) {
                it?.let {
                    membersAdapter.submitList(it.persons)
                    binding.progressBar.hide()
                }
            }

            success.observe(viewLifecycleOwner) {
                it?.let {
                    if (!it) {
                        binding.notExistMemberLabel.show()
                        binding.membersRecyclerView.hide()
                    } else {
                        binding.membersRecyclerView.show()
                        binding.notExistMemberLabel.hide()
                    }
                }
            }
        }
    }

    override fun onMemberItemClick(member: MemberNameId) {
        findNavController().navigate(
            R.id.action_homeDetailsFragment_to_familyDetailsFragment,
            Bundle().apply {
                putParcelable(
                    getString(R.string.collectionsKeys_key),
                    CollectionsKeys(
                        churchKey = home.churchId,
                        memberKey = member.id
                    )
                )
            }
        )
    }


}