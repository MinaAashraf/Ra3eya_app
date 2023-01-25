package com.mina.dev.ra3eya_app.presentation.familyform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentFamilyBinding
import com.mina.dev.ra3eya_app.domain.model.HomesList


class FamilyFragment : Fragment() {

    private val binding by lazy { FragmentFamilyBinding.inflate(layoutInflater) }
    lateinit var homesList: HomesList
    var homeIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getHomeData()
        return binding.root
    }

    private fun getHomeData() {
        arguments?.let {
            homesList =
                it.getParcelableArrayList<HomesList>(getString(R.string.homes_list_key)) as HomesList
            homeIndex = it.getInt(getString(R.string.home_index_key))
        }
    }


}