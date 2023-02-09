package com.mina.dev.ra3eya_app.presentation.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.mina.dev.ra3eya_app.R
import com.mina.dev.ra3eya_app.databinding.FragmentMapWithSearchBinding
import com.mina.dev.ra3eya_app.domain.model.MyLocation
import com.mina.dev.ra3eya_app.presentation.homeform.HomeViewModel
import com.mina.dev.ra3eya_app.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapWithSearchFragment : Fragment() {
    private val binding by lazy { FragmentMapWithSearchBinding.inflate(layoutInflater) }
    private var gMap: GoogleMap? = null
    lateinit var mapView: MapView
    private val signUpViewModel: SignUpViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val viewModel: MapsViewModel by viewModels()
    private var position: LatLng? = null
    private lateinit var markerTitle: String
    private var markerOptions: MarkerOptions? = null
    private var addressLine: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            fromWhichScreen = it.getInt(getString(R.string.from_which_key))
            markerTitle = it.getString(
                if (fromWhichScreen == 0) getString(R.string.church_name_key) else getString(
                    R.string.home_name_key
                ), ""
            )
        }
        setUpUi(savedInstanceState)
        return binding.root
    }

    private fun setUpUi(savedInstanceState: Bundle?) {
        setUpMap(savedInstanceState)
        setUpSearchView()
        setUpMyCurrentLocationBtn()
        setUpSaveBtn()
    }

    private fun setUpSaveBtn() {
        binding.saveLocationBtn.setOnClickListener {
            markerOptions?.let {
                val markerPosition = MyLocation(it.position.latitude, it.position.longitude)
                if (fromWhichScreen == 0)
                    signUpViewModel.church.apply {
                        location = MyLocation(finalPosition.latitude,finalPosition.longitude)
                        addressLine = viewModel.getAddressFromLatLng(
                            requireContext(),
                            LatLng(markerPosition.latitude, markerPosition.longitude)
                        )
                    }
                else {
                    homeViewModel.home.apply {
                        location = MyLocation(finalPosition.latitude,finalPosition.longitude)
                        try {
                            addressLine = viewModel.getAddressFromLatLng(
                                requireContext(),
                                LatLng(markerPosition.latitude, markerPosition.longitude)
                            )
                        }catch (e:Exception){ }
                    }
                }
                /*findNavController().navigate(
                    if (fromWhichScreen == 0) R.id.action_mapWithSearchFragment_to_signUpFragment else R.id.action_mapWithSearchFragment_to_homeFormFragment,
                    Bundle().apply {
                        putString(
                            if (fromWhichScreen == 0) getString(R.string.church_address_line_key) else getString(
                                R.string.home_address_line_key
                            ), addressLine)
                        putDouble("lat",finalPosition.latitude)
                        putDouble("long",finalPosition.longitude)
                    })*/

                findNavController().popBackStack()
            } ?: kotlin.run {
                Snackbar.make(
                    binding.root,
                    getString(R.string.empty_location_err),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUpMyCurrentLocationBtn() {
        binding.myCurrentLocationBtn.setOnClickListener {
            getCurrentLocation()
        }
    }

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private fun getCurrentLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                finalPosition = LatLng(it.latitude,it.longitude)
                lastMarker?.let { marker -> marker.remove() }
                addMapMarkerAtSelectedPosition(LatLng(it.latitude, it.longitude))
            }
        }.addOnFailureListener {
            Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpMap(savedInstanceState: Bundle?) {
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            gMap = it
            it.setMapStyle(MapStyleOptions(resources.getString(R.string.style_json)))
            it.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(30.0444, 31.2357), 8f))
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isMyLocationButtonEnabled = true
        }
    }


    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(input: String?): Boolean {
                input?.let { it ->
                    if (it.isNotEmpty()) {
                        position = viewModel.getLatLongFromAddress(requireContext(), it)
                        lastMarker?.let { it.remove() }
                        position?.let {
                            finalPosition = it
                            addMapMarkerAtSelectedPosition(it)
                        }

                    }
                }
                binding.searchView.hideKeyboard()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    private var lastMarker: Marker? = null
    private var fromWhichScreen: Int = 0
    private fun addMapMarkerAtSelectedPosition(selectedPosition: LatLng) {
        markerOptions = MarkerOptions().position(selectedPosition).title("").draggable(true)
            .icon(BitmapDescriptorFactory.fromResource(if (fromWhichScreen == 0) R.drawable.church_marker else R.drawable.home_marker))
        gMap?.let { googleMap ->
            lastMarker = googleMap.addMarker(
                markerOptions!!
            )
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    selectedPosition,
                    20f
                )
            )
            listenOnMarkerDrag()
        }
    }

    private lateinit var finalPosition: LatLng
    private fun listenOnMarkerDrag() {
        gMap?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(marker: Marker) {}
            override fun onMarkerDragEnd(marker: Marker) {
                finalPosition = marker.position
                try {
                    addressLine = viewModel.getAddressFromLatLng(requireContext(), marker.position)
                    binding.searchView.setQuery(addressLine, false)
                } catch (e: Exception) {
                }
            }

            override fun onMarkerDragStart(marker: Marker) {}

        })
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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

    override fun onStart() {
        super.onStart()
        mapView.onStart()
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