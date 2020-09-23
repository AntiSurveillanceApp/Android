package com.hack.antisurveillance.presentation.screens.permissions

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hack.antisurveillance.R
import com.hack.antisurveillance.presentation.extensions.*
import com.hack.antisurveillance.presentation.helpers.getPermissionsDeclinedCantAskMoreDialog
import com.hack.antisurveillance.presentation.helpers.startAppSettings
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import com.hack.antisurveillance.presentation.screens.info.InfoFragment
import kotlinx.android.synthetic.main.fragment_location_permissions.*
import kotlin.RuntimeException

class LocationPermissionsFragment : BaseFragment(R.layout.fragment_location_permissions),
    DialogInterface.OnClickListener {

    private var alertDialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (fineLocation.isGranted(requireContext()) && courseLocation.isGranted(requireContext())) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                if (backgroundLocation.isGranted(requireContext())) {
                    //Permission already granted; open Microphone permissions fragment
                    startMicPermissionsScreen()
                    return
                }
            } else {
                //Permission already granted; open Microphone permissions fragment
                startMicPermissionsScreen()
                return
            }
        }

        buttonAcceptPermission.setSafeClickListener {
            requestForegroundPermissions()
        }

        textViewDetails.setOnClickListener {
            val bundle =
                bundleOf(InfoFragment.BUNDLE_TEXT_KEY to R.string.gps_permission_description)
            findNavController().navigate(
                R.id.action_locationPermissionsFragment_to_infoFragment,
                bundle
            )
        }
    }

    private fun requestForegroundPermissions() {
        requestPermissions(
            arrayOf(fineLocation, courseLocation),
            REQUEST_FOREGROUND_LOCATION_PERMISSIONS
        )
    }

    private fun showPermissionsDeclinedCantAskMoreDialog(@StringRes message: Int) {
        alertDialog = requireContext().getPermissionsDeclinedCantAskMoreDialog(message, this)
        alertDialog?.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_FOREGROUND_LOCATION_PERMISSIONS -> checkForegroundLocationPermissions(
                permissions,
                grantResults
            )
            REQUEST_BACKGROUND_LOCATION_PERMISSIONS -> checkBackgroundLocationPermissions(
                permissions,
                grantResults
            )
        }
    }

    private fun checkBackgroundLocationPermissions(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions.isEmpty()) throw RuntimeException("Not enough permissions on request result")

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startMicPermissionsScreen()
            return
        }
        if (backgroundLocation.shouldShowRationale(requireActivity())) {
            requestBackgroundLocationPermissions()
        } else {
            showPermissionsDeclinedCantAskMoreDialog(R.string.location_permissions_denied_message)
        }
    }

    private fun checkForegroundLocationPermissions(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions.size < 2) throw RuntimeException("Not enough permissions on request result")

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                requestBackgroundLocationPermissions()
            } else {
                startMicPermissionsScreen()
            }
        } else {
            if (fineLocation.shouldShowRationale(requireActivity()) && courseLocation.shouldShowRationale(
                    requireActivity()
                )
            ) {
                //still can request permissions
                requestForegroundPermissions()
            } else {
                //cannot request permissions, show popup and redirect to settings
                showPermissionsDeclinedCantAskMoreDialog(R.string.location_permissions_denied_message)
            }
        }
    }

    private fun startMicPermissionsScreen() {
        findNavController().navigate(R.id.action_locationPermissionsFragment_to_micPermissionsFragment)
    }

    private fun requestBackgroundLocationPermissions() {
        requestPermissions(arrayOf(backgroundLocation), REQUEST_BACKGROUND_LOCATION_PERMISSIONS)
    }

    override fun onStop() {
        super.onStop()
        alertDialog?.dismiss()
        alertDialog = null
    }

    override fun onClick(dialog: DialogInterface?, clickId: Int) {
        when (clickId) {
            -1 -> {
                //open app settings
                requireContext().startAppSettings()
            }
            -2 -> {
                dialog?.dismiss()
            }
        }
    }

    companion object {
        private const val REQUEST_FOREGROUND_LOCATION_PERMISSIONS = 1000
        private const val REQUEST_BACKGROUND_LOCATION_PERMISSIONS = 1001
    }
}