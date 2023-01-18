package com.mina.dev.ra3eya_app.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentSignUpBinding
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private val viewModel: SignUpViewModel by activityViewModels()
    private val binding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpViews()
        observeViewModel()
        return binding.root
    }

    private fun setUpViews() {
        binding.addingAddressBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_signUpFragment_to_mapWithSearchFragment,
                Bundle().apply {
                    putInt(getString(R.string.from_which_key), 0)
                    putString(
                        getString(R.string.church_name_key),
                        binding.churchNameField.editText!!.text.toString()
                    )
                })
        }

        binding.saveChurchBtn.setOnClickListener {
            if (validateInputs()) {
                val churchName = binding.churchNameField.editText?.text.toString()
                val churchPassword = binding.passwordField.editText?.text.toString()
                viewModel.church.apply {
                    name = churchName
                    password = churchPassword
                    id = churchName
                }
                viewModel.addChurch(requireContext())
            }
        }
        showAddressTextIfExist()

    }

    private fun showAddressTextIfExist() {
        viewModel.church.addressLine?.let {
            binding.addressTxt.text = it
        }
    }


    private fun validateInputs(): Boolean {
        var validate = true
        if (binding.churchNameField.editText!!.text!!.isEmpty()) {
            binding.churchNameField.helperText = getString(R.string.church_name_error_msg)
            validate = false
        }
        if (binding.passwordField.editText!!.text.isEmpty()) {
            binding.passwordField.helperText = getString(R.string.password_error_msg)
            validate = false
        }
        if (binding.passwordField.editText!!.text.length < 8) {
            binding.passwordField.helperText = getString(R.string.password_weak_msg)
            validate = false
        }

        if (viewModel.church.location == null) {
            binding.addressTxt.text = getString(R.string.add_church_address_msg)
            validate = false
        }

        return validate
    }

    private fun observeViewModel() {
        viewModel.succeeded.observe(viewLifecycleOwner) { it ->
            it?.let {
                if (it.first) {
                    //  Snackbar.make(binding.root, it.second.toString(), Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(
                        R.id.action_signUpFragment_to_mapFragment,
                        Bundle().apply {
                            putParcelable("church", viewModel.church)
                        }
                    )
                    viewModel.clearLiveData()
                } else {
                    //   Snackbar.make(binding.root, it.second.toString(), Snackbar.LENGTH_SHORT).show()
                    binding.progressBar.hide()
                    binding.saveChurchBtn.show()
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.progressBar.show()
                    binding.saveChurchBtn.hide()
                }
            }
        }
    }

}