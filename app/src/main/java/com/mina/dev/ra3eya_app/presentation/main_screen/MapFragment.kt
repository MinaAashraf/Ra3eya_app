package com.mina.dev.ra3eya_app.presentation.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentMapBinding
import com.mina.dev.ra3eya_app.domain.model.Church
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment() {

    private var gMap: GoogleMap? = null
    private lateinit var mapView: MapView
    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }
    private val viewModel: MapsViewModel by activityViewModels()
    private lateinit var church: Church

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpMap(savedInstanceState)
        setUpUi()
        getChurchFromPreviousScreensIfExist()
        return binding.root
    }

    private fun setUpUi() {
        setUpFab()
    }

    private fun getChurchFromPreviousScreensIfExist() {
        arguments?.let {
            // if it get from sign in or sign up screens -> get church directly
            viewModel.setChurch(it.getParcelable<Church>("church") as Church)
        } ?: kotlin.run {
            // if it is already authenticated and have church id only -> read church from firebase using church saved id
            viewModel.readChurch(requireContext())
        }
    }

    private fun setUpMap(savedInstanceState: Bundle?) {
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            gMap = it
            gMap!!.setMapStyle(MapStyleOptions(resources.getString(R.string.style_json)))
            observeViewModelData()
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
        addHomesMarkers(church)
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
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(churchMarker.position, 17f))
        }

    }

    private fun addHomesMarkers(church: Church) {
        church.homes.let {
            it.forEach { home ->
                home.location?.let { homeLocation ->
                    val homeMarker = MarkerOptions().position(
                        LatLng(
                            homeLocation.latitude,
                            homeLocation.longitude
                        )
                    )
                        .title(home.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_icon))
                    gMap!!.addMarker(homeMarker)
                }
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

}