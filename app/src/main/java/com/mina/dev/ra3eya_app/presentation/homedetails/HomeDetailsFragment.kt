package com.mina.dev.ra3eya_app.presentation.homedetails

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentFamilyBinding
import com.mina.dev.ra3eya_app.databinding.FragmentHomeDetailsBinding
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.presentation.utils.hide
import com.mina.dev.ra3eya_app.presentation.utils.show

class HomeDetailsFragment : Fragment() {
    private val binding by lazy { FragmentHomeDetailsBinding.inflate(layoutInflater) }
    private lateinit var home: Home
    private var homeIndex: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            home = it.getParcelable<Home>(getString(R.string.home_key)) as Home
            homeIndex = it.getInt(getString(R.string.home_index_key))
        }
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.homeAddressText.text = home.detailedAddress
        handleFamiliesContainerVisibility()
        setClickListenerOnAddFamilyBtn()
    }

    private fun handleFamiliesContainerVisibility() {
        if (home.families == null)
            binding.homeFamiliesContainer.hide()
        else
            binding.homeFamiliesContainer.show()
    }

    private fun setClickListenerOnAddFamilyBtn() {
        binding.addFamilyBtn.setOnClickListener {
            //findNavController().navigate(R.id.action_homeDetailsFragment_to_familyFragment)
             showAddingFamilyDialog()
        }
    }

    private fun showAddingFamilyDialog() {
        val familyDialog = Dialog(requireContext())
        familyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = FragmentFamilyBinding.inflate(layoutInflater)
        familyDialog.setContentView(dialogBinding.root)
        familyDialog.show()
    }


}