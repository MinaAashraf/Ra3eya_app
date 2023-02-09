package com.mina.dev.ra3eya_app.presentation.homeform

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentHomeFormBinding
import com.mina.dev.ra3eya_app.presentation.main_screen.MapsViewModel
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.hideUsingGone
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFormFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private val mapsViewModel: MapsViewModel by activityViewModels()
    private val binding: FragmentHomeFormBinding by lazy {
        FragmentHomeFormBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var churchId: String
    private lateinit var churchAddressLine: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpUi()
        getPositionLatLngFromMapIfExist()
        arguments?.apply {
            churchId = getString(getString(R.string.church_id_key), "")
            churchAddressLine = getString(getString(R.string.church_address_line_key), "")
            Log.d("churchArgs", "$churchId --- $churchAddressLine")
        }

        return binding.root
    }

    private fun setUpUi() {
        setUpAddAddressBtn()
        setUpSaveBtn()
        handleAddressBtnText()
    }

    private fun getPositionLatLngFromMapIfExist() {
        arguments?.let {
            // homePosition = MyLocation(it.getDouble("lat"), it.getDouble("long"))
            //binding.addingAddressBtn.hide()
        }
    }

    private fun setUpAddAddressBtn() {
        binding.addingAddressBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFormFragment_to_mapWithSearchFragment,
                Bundle().apply {
                    putInt(getString(R.string.from_which_key), 1)
                    putString(
                        getString(R.string.home_name_key),
                        binding.homeNameField.editText!!.text.toString()
                    )
                })
        }
    }

    private fun handleAddressBtnText() {
        if (viewModel.home.location != null) {
            binding.addingAddressBtn.text = getString(R.string.change_address_label)
        }
    }

    private fun setUpSaveBtn() {
        binding.saveHomeBtn.setOnClickListener {
            if (validate()) {
                val homeName = binding.homeNameField.editText!!.text.toString()
                val homeFamNum = binding.familyNumberField.editText!!.text.toString().toInt()
                val homeDetailedAddress = binding.detailedAddressField.editText!!.text.toString()
                viewModel.insertHome(
                    homeName = homeName,
                    homeFamiliesNo = homeFamNum,
                    churchId = churchId,
                   // addressLine = churchAddressLine,
                    detailedAddress = homeDetailedAddress
                )
                observeModelData()
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (binding.homeNameField.editText!!.text.isEmpty()) {
            binding.homeNameField.helperText = getString(R.string.home_name_field_err)
            isValid = false
        }

        if (binding.familyNumberField.editText!!.text.isEmpty()) {
            binding.homeNameField.helperText = getString(R.string.families_num_field_err)
            isValid = false
        }
        if (binding.detailedAddressField.editText!!.text.isEmpty()) {
            binding.detailedAddressField.helperText = getString(R.string.detailed_address_err)
            isValid = false
        }
        if (viewModel.home.location == null) {
            binding.addressErrTxt.show()
            isValid = false
        } else {
            binding.addressErrTxt.hideUsingGone()
        }
        return isValid
    }

    private fun observeModelData() {
        viewModel.succeeded.observe(viewLifecycleOwner) {
            it?.let {
                if (it.first) {
                    findNavController().navigate(R.id.action_homeFormFragment_to_mapFragment)
                    Snackbar.make(binding.root, it.second!!, Snackbar.LENGTH_SHORT).show()
                } else
                    Log.d("home insertion err", it.second!!)
            }

        }
    }


}