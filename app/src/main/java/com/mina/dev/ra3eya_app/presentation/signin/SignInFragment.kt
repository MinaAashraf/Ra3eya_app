package com.mina.dev.ra3eya_app.presentation.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentSignInBinding
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.ChurchCredentials
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fra gment
        setUpViews()
        isAuthenticated()
        observeViewModel()
        return binding.root
    }

    private fun isAuthenticated() {
        if (viewModel.isAuthenticated(requireContext()))
            findNavController().navigate(R.id.action_signInFragment_to_mapFragment)
    }

    private fun setUpViews() {
        binding.signInBtn.setOnClickListener {
            if (validateInputs()) {
                val churchName = binding.churchNameField.editText?.text.toString()
                val password = binding.passwordField.editText?.text.toString()
                signIn(ChurchCredentials(churchName, password, churchName))

            }
        }

        binding.addingChurchTxt.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            viewModel.clearLiveData()
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
        } else if (binding.passwordField.editText!!.text.length < 8) {
            binding.passwordField.helperText = getString(R.string.password_weak_msg)
            validate = false
        }
        return validate
    }

    private fun signIn(churchCredentials: ChurchCredentials) {
        viewModel.signIn(
            churchCredentials, requireContext()
        ).observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    R.id.action_signInFragment_to_mapFragment,
                    Bundle().apply {
                        putParcelable("church", it)
                    })
                Snackbar.make(
                    binding.root,
                    getString(R.string.sign_in_successfully_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.saveState(requireContext(), it.id)
                viewModel.clearLiveData()
            } ?: kotlin.run {
                Snackbar.make(
                    binding.root,
                    getString(R.string.wrong_password_msg),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeViewModel() {


        /*     viewModel.succeeded.observe(viewLifecycleOwner) { it ->
                 it?.let {
                     if (it.first) {
                         Snackbar.make(binding.root, it.second.toString(), Snackbar.LENGTH_SHORT).show()
                         findNavController().navigate(
                             R.id.action_signInFragment_to_mapFragment,
                             Bundle().apply {
                                 putParcelable("church", viewModel.church)
                             })
                         viewModel.saveState(requireContext())
                         viewModel.clearLiveData()
                     } else {
                         Snackbar.make(binding.root, it.second.toString(), Snackbar.LENGTH_SHORT).show()
                         binding.progressBar.hide()
                         binding.signInBtn.show()
                     }
                 }
             }

             viewModel.loading.observe(viewLifecycleOwner) {
                 it?.let {
                     if (it) {
                         binding.progressBar.show()
                         binding.signInBtn.hide()
                     }
                 }
             }*/

        viewModel.allChurches.observe(viewLifecycleOwner) {
            it?.let {
                val churchesAdapter = ArrayAdapter(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    it.map { it.name })
                binding.churchNameAutoCompleteTv.setAdapter(churchesAdapter)
            }
        }

    }


}