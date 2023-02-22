package com.mina.dev.ra3eya_app.presentation.memberdetails

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentMemberDetailsBinding
import com.mina.dev.ra3eya_app.domain.model.Member

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberDetailsFragment : Fragment() {
    private val binding by lazy { FragmentMemberDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MemberDetailsViewModel>()
    private lateinit var member: Member


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getArgumentsData()
        handleButtonsClick()
        return binding.root
    }

    private fun getArgumentsData() {
        arguments?.let {
            member = it.getParcelable<Member>(getString(R.string.member_key)) as Member
            binding.member = member
        }
    }

    private fun handleButtonsClick() {
        binding.homeBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_memberDetailsFragment_to_homeDetailsFragment,
                Bundle().apply {
                    putString(getString(R.string.homeId_key), member.homeId)
                })
        }
        binding.familyBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_memberDetailsFragment_to_familyDetailsFragment,
                Bundle().apply {
                    putString(getString(R.string.family_name_key), member.familyName)
                    putString(getString(R.string.homeId_key), member.homeId)
                })
        }

        binding.phoneIcon.setOnClickListener {
            if (checkPhoneCallPermission())
                requestPermission()
            else
                showPhoneCallingDialog(requireActivity(),member.phone,member.name)
        }
    }



    private fun showPhoneCallingDialog(
        activity: Activity,
        phone: String,
        receiverName: String
    ) {
        val dialog = MaterialAlertDialogBuilder(activity)
            .setTitle("اتصال")
            .setMessage(getPhoneCallDialogMessage(receiverName))
            .setIcon(R.drawable.ic_baseline_phone_24)
            .setPositiveButton("نعم") { _, _ ->
                makePhoneCall(activity, phone)
            }.setNegativeButton("لا") { dialogInterface, _ ->
                dialogInterface.cancel()
            }.create()
        dialog.window?.let {
            ViewCompat.setLayoutDirection(it.decorView, ViewCompat.LAYOUT_DIRECTION_RTL)
        }
        dialog.show()
    }


    private fun getPhoneCallDialogMessage(name: String): String {
        return "هل تريد الاتصال ب $name"
    }


    private fun makePhoneCall(activity: Activity, phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${phone}")
        activity.startActivity(intent)
    }

    private fun checkPhoneCallPermission() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CALL_PHONE
    ) != PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CALL_PHONE),
            0
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showPhoneCallingDialog(
                    activity = requireActivity(),
                    phone = member.phone,
                    receiverName = member.name
                )
            }
        }
    }




}