package com.mina.dev.ra3eya_app.presentation.familyform

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ScrollView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentMemberFormBinding
import com.mina.dev.ra3eya_app.domain.model.CollectionsKeys
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.hideKeyboard
import com.mina.dev.ra3eya_app.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberFormFragment : Fragment() {

    private val binding by lazy { FragmentMemberFormBinding.inflate(layoutInflater) }
    private lateinit var collectionsKeysObj: CollectionsKeys
    private lateinit var homeAddress: String
    private lateinit var familyName: String


    private val viewModel: MemberFormViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getCollectionSKeys()
        setUi()
        observeLiveData()
        handleSoftKeyboard()
        return binding.root
    }

    private fun getCollectionSKeys() {
        arguments?.let {
            collectionsKeysObj =
                it.getParcelable<CollectionsKeys>(getString(R.string.collectionsKeys_key)) as CollectionsKeys
            homeAddress = it.getString(getString(R.string.home_address_key), "")
            familyName = it.getString(getString(R.string.family_name_key), "")

        }
    }

    private fun setUi() {
        setRelationDropDownMenu()
        setSaveMemberHandler()
        handleIdImageBtn()
    }

    private fun setSaveMemberHandler() {
        binding.saveMemberBtn.setOnClickListener {
            if (validateMemberInputs()) {
                val member = Member(
                    name = binding.memberNameField.editText!!.text.toString(),
                    relation = binding.relationField.editText!!.text.toString(),
                    job = binding.jobField.editText!!.text.toString(),
                    spiritualFather = binding.spiritualFatherField.editText!!.text.toString(),
                    phone = binding.phoneField.editText!!.text.toString(),
                    familyName = familyName,
                    address = homeAddress,
                    churchId = collectionsKeysObj.churchKey!!,
                    homeId = collectionsKeysObj.homeKey!!,
                )
                viewModel.addMember(uri = uri, member = member)

            }
        }
    }

    private var memberCounter = 1
    private fun observeLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.saveMemberBtn.hide()
                    binding.progressBar.show()
                }
            }
        }
        viewModel.memberResult.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(
                    binding.root,
                    getString(R.string.family_adding_success_message),
                    Snackbar.LENGTH_LONG
                ).show()

                binding.memberTitle.text = "فرد $memberCounter"
                binding.progressBar.hide()
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result?.let {
                if (result.resultCode == Activity.RESULT_OK) {
                    uri = it.data!!.data
                    binding.addImageIcon.hide()
                    binding.idImage.setImageURI(uri)
                }

            }
        }
    private var uri: Uri? = null


    private fun handleIdImageBtn() {
        binding.idImage.setOnClickListener {
            getImageFromGallery()
        }
    }

    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }


    private fun validateMemberInputs(): Boolean {
        var isValid = true
        if (binding.memberNameField.editText!!.text.isEmpty()) {
            isValid = false
            binding.memberNameField.helperText = getString(R.string.name_field_err)
        }
        if (binding.relationField.editText!!.text.isEmpty()) {
            isValid = false
            binding.relationField.helperText = getString(R.string.name_field_err)
        }
        if (binding.phoneField.editText!!.text.isEmpty()) {
            isValid = false
            binding.phoneField.helperText = getString(R.string.phone_field_err)
        }
        if (binding.spiritualFatherField.editText!!.text.isEmpty()) {
            isValid = false
            binding.spiritualFatherField.helperText = getString(R.string.spiritual_father_filed_err)
        }
        return isValid
    }


    private fun setRelationDropDownMenu() {

        val relationsAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            listOf("ابنة", "ابن", "ام", "اب")
        )
        binding.relationsAutoCompleteTv.setAdapter(relationsAdapter)
    }

    private fun handleSoftKeyboard() {
        binding.parentLayout.setOnClickListener {
            (it as ConstraintLayout).children.forEach {
                it.hideKeyboard()
            }
           // binding.memberNameField.editText!!.hideKeyboard()
        }
    }


}