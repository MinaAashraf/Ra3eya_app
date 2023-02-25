package com.mina.dev.ra3eya_app.presentation.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentMapBinding
import com.mina.dev.ra3eya_app.domain.model.Church
import com.mina.dev.ra3eya_app.domain.model.Family
import com.mina.dev.ra3eya_app.domain.model.Home
import com.mina.dev.ra3eya_app.domain.model.Member
import com.mina.dev.ra3eya_app.presentation.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private var gMap: GoogleMap? = null
    private lateinit var mapView: MapView
    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }
    private val viewModel: MapsViewModel by activityViewModels()
    private lateinit var church: Church
    private val homes = mutableListOf<Home>()
    private lateinit var members: List<Member>
    private lateinit var families: List<Family>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpMap(savedInstanceState)
        setUpUi()
        readChurch()
        refreshHomes()
        refreshFamilies()
        refreshMembers()
        observeHomesLiveData()
        setUpAutoCompleteSearchView()
        setUpSearchBtn()
        setOnAutoCompleteTextItemClick()
        return binding.root
    }


    private fun setUpUi() {
        setUpFab()
    }

    private fun readChurch() {
        arguments?.let {
            // if it get from sign in or sign up screens -> get church directly
            viewModel.setChurch(it.getParcelable<Church>("church") as Church)
        } ?: kotlin.run {
            // if it is already authenticated and have church id only -> read church from firebase using church saved id
            viewModel.readChurch(requireContext())
        }
    }


    private fun refreshHomes() {
        viewModel.refreshHomes(requireContext())
    }

    private fun refreshMembers() {
        viewModel.refreshMembers(requireContext())
    }

    private fun refreshFamilies() {
        viewModel.refreshFamilies(requireContext())
    }


    private fun observeHomesLiveData() {
        viewModel.homes.observe(viewLifecycleOwner) {
            homes.clear()
            homes.addAll(it)
            gMap?.let {
                addHomesMarkers()
            }
        }
    }

    private fun setUpMap(savedInstanceState: Bundle?) {
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            gMap = it
            gMap!!.setMapStyle(MapStyleOptions(resources.getString(R.string.style_json)))
            observeViewModelData()
            handleSoftKeyboard()
        }
    }

    private fun observeViewModelData() {
        viewModel.churchData.observe(viewLifecycleOwner) {
            addMarkers(it)
            church = it
        }
    }

    private fun addMarkers(church: Church) {
        addChurchMarker(church)
    }

    private fun addChurchMarker(church: Church) {
        val churchMarker = MarkerOptions().position(
            LatLng(
                church.location!!.latitude,
                church.location!!.longitude
            )
        )
            .title(church.name)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.church_marker))
        gMap?.let {
            it.addMarker(churchMarker)
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(churchMarker.position, 14f))
        }

    }


    private var markers: MutableList<Marker> = mutableListOf()
    private fun addHomesMarkers() {
        homes.forEachIndexed { index: Int, home: Home ->
            home.location?.let { homeLocation ->
                val homeMarker = MarkerOptions().position(
                    LatLng(
                        homeLocation.latitude,
                        homeLocation.longitude
                    )
                )
                    .title(home.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_marker))
                markers.add(gMap?.addMarker(homeMarker)!!)
            }
        }

        gMap?.setOnMarkerClickListener(this)

    }

    private var selectedItemPosition: Int = -1
    private var selectedItem: String = ""
    private fun setOnAutoCompleteTextItemClick() {
        binding.autoCompleteSearchView.setOnItemClickListener { adapterView, view, position: Int, l ->
            selectedItemPosition = position
        }
    }

    private fun setUpSearchBtn() {
        binding.searchIcon.setOnClickListener {
            val searchWord = binding.autoCompleteSearchView.text.toString()
            Snackbar.make(binding.root, searchWord, Snackbar.LENGTH_SHORT)
                .show()
            if (!namesInAutoCompleteList.contains(searchWord)) {
                binding.autoCompleteSearchView.error = getString(R.string.search_err_message)
                return@setOnClickListener
            }

            if (searchWord.startsWith(getString(R.string.family_label))||searchWord.startsWith(getString(R.string.family_label2)))
                findNavController().navigate(
                    R.id.action_mapFragment_to_familyDetailsFragment2,
                    Bundle().apply {
                        putParcelable(
                            getString(R.string.family_key),
                            families.filter { it.familyName == searchWord }[0]
                        )
                    })
            else if (searchWord.startsWith(getString(R.string.home_label)))
                findNavController().navigate(
                    R.id.action_mapFragment_to_homeDetailsFragment,
                    Bundle().apply {
                        putParcelable(
                            getString(R.string.home_key),
                            homes.filter { it.name == searchWord }[0]
                        )
                    })
            else {
                findNavController().navigate(
                    R.id.action_mapFragment_to_memberDetailsFragment,
                    Bundle().apply {
                        putParcelable(
                            getString(R.string.member_key),
                            members.filter { it.name == searchWord }[0]
                        )
                    })
            }

        }
    }

    private fun setUpFab() {
        binding.addingHomeFab.setOnClickListener {
            findNavController().navigate(
                R.id.action_mapFragment_to_homeFormFragment,
                Bundle().apply {
                    putString(getString(R.string.church_id_key), church.id)
                    putString(getString(R.string.church_address_line_key), church.addressLine)
                })
        }
    }

    private var namesInAutoCompleteList: MutableList<String> = mutableListOf()
    private lateinit var autoCompleteAdapter: ArrayAdapter<String>
    private var dataRepeating = mutableListOf(false, false, false)


    private fun setUpAutoCompleteSearchView() {
        autoCompleteAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            namesInAutoCompleteList
        )
        viewModel.members.observe(viewLifecycleOwner) {
            if (!dataRepeating[0]) {
                namesInAutoCompleteList.addAll(it.map { it.name })
                members = it
                autoCompleteAdapter.notifyDataSetChanged()
                dataRepeating[0] = true
            }
        }
        viewModel.families.observe(viewLifecycleOwner) {
            if (!dataRepeating[1]) {
                namesInAutoCompleteList.addAll(it.map { it.familyName })
                families = it
                autoCompleteAdapter.notifyDataSetChanged()
                dataRepeating[1] = true
            }
        }
        viewModel.homes.observe(viewLifecycleOwner) {
            if (!dataRepeating[2]) {
                namesInAutoCompleteList.addAll(it.map { it.name!! })
                autoCompleteAdapter.notifyDataSetChanged()
                dataRepeating[2] = true
            }
        }
        binding.autoCompleteSearchView.setAdapter(autoCompleteAdapter)
    }

    // the following callbacks implementations to handle the map view life cycle manually:
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }


    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (marker.title == church.name)
            return false
        val clickedHome = homes.filter { it.name == marker.title }[0]
        findNavController().navigate(
            R.id.action_mapFragment_to_homeDetailsFragment,
            Bundle().apply {
                putParcelable(getString(R.string.home_key), clickedHome)
            })
        return false
    }

    private fun handleSoftKeyboard() {
        gMap?.setOnMapClickListener {
            binding.autoCompleteSearchView.hideKeyboard()
        }
        gMap?.setOnCameraMoveListener {
            binding.autoCompleteSearchView.hideKeyboard()
        }
    }

}