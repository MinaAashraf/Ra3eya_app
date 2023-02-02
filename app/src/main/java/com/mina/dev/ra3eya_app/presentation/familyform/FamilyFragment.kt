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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentFamilyBinding
import com.mina.dev.ra3eya_app.domain.model.HomesList
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.presentation.utils.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamilyFragment : Fragment() {

    private val binding by lazy { FragmentFamilyBinding.inflate(layoutInflater) }
    //lateinit var homesList: HomesList
    var homeIndex: Int = 0
    private val viewModel : FamilyFormViewModel by viewModels ()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      //  getHomeData()
        setUi()
        return binding.root
    }
/*
    private fun getHomeData() {
        arguments?.let {
            homesList =
                it.getParcelableArrayList<HomesList>(getString(R.string.homes_list_key)) as HomesList
            homeIndex = it.getInt(getString(R.string.home_index_key))
        }
    }*/

    private fun setUi() {
        setRelationDropDownMenu()
        setSaveMemberHandler()
        handleIdImageBtn()
    }

    private fun setSaveMemberHandler() {
        binding.saveMemberBtn.setOnClickListener {
            if (validateMemberInputs()) {
                val member = Member(
                    binding.memberNameField.editText!!.text.toString(),
                    binding.relationField.editText!!.text.toString()
                )
                viewModel.addMember(uri = uri ,member = member)

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

    private fun saveAll() {

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
        return isValid
    }



    private fun setRelationDropDownMenu() {

        val relationsAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            listOf("ابنة","ابن","ام","اب")
        )
        binding.relationsAutoCompleteTv.setAdapter(relationsAdapter)
    }


}