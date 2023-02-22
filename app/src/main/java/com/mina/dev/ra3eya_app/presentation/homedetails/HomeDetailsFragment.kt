package com.mina.dev.ra3eya_app.presentation.homedetails

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FamilyAddingDialogBinding

import com.mina.dev.ra3eya_app.domain.model.CollectionsKeys
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.FamilyNameId
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailsFragment : Fragment(), FamiliesAdapter.ItemClickListener {
    private val binding by lazy {
        com.mina.dev.ra3eya_app.databinding.FragmentHomeDetailsBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var home: Home
    private lateinit var homeId: String

    private val viewModel by viewModels<HomeDetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            if (it.containsKey(getString(R.string.home_key)))
                home = it.getParcelable<Home>(getString(R.string.home_key)) as Home
            else
                homeId = it.getString(getString(R.string.homeId_key) , "")
        }
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.homeAddressText.text = home.detailedAddress
        handleFamiliesContainerVisibility()
        setUpFamiliesRecyclerView()
        setClickListenerOnAddFamilyBtn()
        observeOnAddingFamily()
    }

    private fun handleFamiliesContainerVisibility() {
        if (home.families == null)
            binding.homeFamiliesContainer.hide()
        else
            binding.homeFamiliesContainer.show()
    }

    private fun setClickListenerOnAddFamilyBtn() {
        binding.addFamilyBtn.setOnClickListener {
            showAddingFamilyDialog()
        }
    }

    private lateinit var familiesAdapter: FamiliesAdapter
    private fun setUpFamiliesRecyclerView() {
        /*  home.families?.let {
              families = it.toMutableList()
              val familiesAdapter = FamiliesAdapter(this).apply { submitList(families) }
              binding.familiesRecyclerView.adapter = familiesAdapter
          }*/
        familiesAdapter = FamiliesAdapter(this)
        binding.familiesRecyclerView.adapter = familiesAdapter
        getHomeFamilies()
    }


    private lateinit var dialogBinding: FamilyAddingDialogBinding
    private lateinit var alertDialog: AlertDialog
    private fun showAddingFamilyDialog() {
        dialogBinding = FamilyAddingDialogBinding.inflate(layoutInflater)
        dialogBinding.saveFamilyBtn.setOnClickListener {
            handleOnSaveFamilyClick(
                familyName = dialogBinding.familyNameField.text.toString(),
                floorNum = dialogBinding.familyFloorNumField.text.toString(),
                homeId = home.homeId,
                churchId = home.churchId!!,
                familyAddress = home.detailedAddress!!
            )
        }

        val builder = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
            create()
        }
        alertDialog = builder.show()

    }

    private fun handleOnSaveFamilyClick(
        familyName: String,
        floorNum: String,
        churchId: String,
        homeId: String,
        familyAddress: String
    ) {
        if (validateInputFamily()) {
            val family = Family(
                familyName = familyName,
                churchId = churchId,
                homeId = homeId,
                floorNum = floorNum.toInt(),
                familyAddress = familyAddress
            )
            viewModel.addFamily(requireContext(), family)
            dialogBinding.saveFamilyBtn.hide()
            dialogBinding.loadingBar.show()
        }
    }

    private fun validateInputFamily(): Boolean {
        var isValid = true
        val familyName = dialogBinding.familyNameField.text.toString()
        val floorNum = dialogBinding.familyFloorNumField.text
        if (familyName.isEmpty()) {
            dialogBinding.familyNameField.error = getString(R.string.family_name_err)
            isValid = false
        }
        if (floorNum.isEmpty()) {
            dialogBinding.familyFloorNumField.error = getString(R.string.floor_num_err)
            isValid = false
        }
        return isValid
    }

    private fun observeOnAddingFamily() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                it.second?.let { message ->
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                    alertDialog.dismiss()
                }
            }
        }
        /* viewModel.familyResult.observe(viewLifecycleOwner) {
             it?.let {
                 families.add(it)
             }
         }*/

    }

    override fun onFamilyItemClick(family: Family) {
        findNavController().navigate(
            R.id.action_homeDetailsFragment_to_familyDetailsFragment,
            Bundle().apply {
                putParcelable(getString(R.string.family_key), family)
            })
    }

    private fun getHomeFamilies() {
        viewModel.getHomeFamilies(home.homeId).observe(viewLifecycleOwner) {
            it?.let {
                if (it.isNotEmpty()) {
                    familiesAdapter.submitList(it)
                    binding.homeFamiliesContainer.show()
                } else
                    binding.homeFamiliesContainer.hide()

            } ?: kotlin.run {
                binding.homeFamiliesContainer.hide()
            }
        }
    }


}