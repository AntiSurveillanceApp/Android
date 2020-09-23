package com.hack.antisurveillance.presentation.screens.pager.map

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.hack.antisurveillance.R
import com.hack.antisurveillance.android.Logger
import com.hack.antisurveillance.presentation.extensions.isLocationPermissionsDenied
import com.hack.antisurveillance.presentation.extensions.setSafeClickListener
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MapFragment : BaseFragment(R.layout.fragment_map), OnMapReadyCallback {

    private val viewModel: MapViewModel by lifecycleScope.viewModel(this)

    private lateinit var googleMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = childFragmentManager.findFragmentById(R.id.map)
        (fragment as? SupportMapFragment)?.getMapAsync(this)

        buttonMockLocation.setSafeClickListener {
            if (::googleMap.isInitialized) {
                viewModel.onButtonMockLocationClicked(
                    googleMap.cameraPosition.target,
                    googleMap.cameraPosition.zoom
                )
            }
        }
    }

    private fun showSuccessToast(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), getString(stringRes), Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.mockedLocation.observe(viewLifecycleOwner, {
            if (it == null) {
                buttonMockLocation.text = getString(R.string.mock_location)
            } else {
                buttonMockLocation.text = getString(R.string.clear_mock_location)
            }
            it?.let { coordinate ->
                if (::googleMap.isInitialized) {
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(coordinate.latitude, coordinate.longitude),
                            coordinate.zoom
                        )
                    )
                }
            }
        })

        viewModel.locationMockedEvent.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let {
                lifecycle.coroutineScope.launch {
                    buttonMockLocation.isEnabled = false
                    progressBar.isVisible = true
                    delay(TIME_MOCK_DELAY)
                    buttonMockLocation.isEnabled = true
                    progressBar.isVisible = false
                    showSuccessToast(R.string.location_mocked)
                    buttonMockLocation.text = getString(R.string.clear_mock_location)
                }
            }
        })

        viewModel.mockClearedEvent.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let {
                lifecycle.coroutineScope.launch {
                    buttonMockLocation.isEnabled = false
                    progressBar.isVisible = true
                    delay(TIME_MOCK_DELAY)
                    buttonMockLocation.isEnabled = true
                    progressBar.isVisible = false
                    showSuccessToast(R.string.location_mock_cleared)
                    buttonMockLocation.text = getString(R.string.mock_location)
                }
            }
        })
        viewModel.currentLocation.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { coordinate ->
                if (::googleMap.isInitialized) {
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(coordinate.latitude, coordinate.longitude),
                            18f
                        )
                    )
                }
            }
        })
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap?) {
        if (map == null) return
        googleMap = map
        viewModel.onMapInitialized()

        val context = requireContext()

        val fusedLocation = LocationServices.getFusedLocationProviderClient(context)
        if (context.isLocationPermissionsDenied()) {
            //ignore
        } else {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings?.isMyLocationButtonEnabled = true
            requestCurrentLocation(fusedLocation)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation(fusedLocation: FusedLocationProviderClient) {
        fusedLocation.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLocation = LatLng(it.latitude, it.longitude)
                    viewModel.onCurrentLocationFound(currentLocation)
                }
            }
    }

    companion object {
        private const val TIME_MOCK_DELAY = 2000L
    }
}