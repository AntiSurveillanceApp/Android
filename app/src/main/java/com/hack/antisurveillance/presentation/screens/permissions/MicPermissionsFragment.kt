package com.hack.antisurveillance.presentation.screens.permissions

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.hack.antisurveillance.R
import com.hack.antisurveillance.presentation.extensions.isGranted
import com.hack.antisurveillance.presentation.extensions.recordAudio
import com.hack.antisurveillance.presentation.extensions.setSafeClickListener
import com.hack.antisurveillance.presentation.extensions.shouldShowRationale
import com.hack.antisurveillance.presentation.helpers.getPermissionsDeclinedCantAskMoreDialog
import com.hack.antisurveillance.presentation.helpers.startAppSettings
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import com.hack.antisurveillance.presentation.screens.info.InfoFragment
import kotlinx.android.synthetic.main.fragment_location_permissions.*

class MicPermissionsFragment : BaseFragment(R.layout.fragment_read_mic_permissions),
    DialogInterface.OnClickListener {

    private var alertDialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (recordAudio.isGranted(requireContext())) {
            //Permission already granted; open pager screen
            startPagerScreen()
            return
        }

        buttonAcceptPermission.setSafeClickListener {
            requestRecordAudioPermission()
        }

        textViewDetails.setSafeClickListener {
            val bundle =
                bundleOf(InfoFragment.BUNDLE_TEXT_KEY to R.string.mic_permission_description)
            findNavController().navigate(R.id.action_micPermissionsFragment_to_infoFragment, bundle)
        }
    }

    private fun requestRecordAudioPermission() {
        requestPermissions(arrayOf(recordAudio), REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            checkRecordAudioPermissionStatus(permissions, grantResults)
        }
    }

    private fun checkRecordAudioPermissionStatus(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions.isEmpty()) throw RuntimeException("Not enough permissions in the array")

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startPagerScreen()
            return
        }
        if (recordAudio.shouldShowRationale(requireActivity())) {
            requestRecordAudioPermission()
        } else {
            showPermissionsDeclinedCantAskMoreDialog()
        }
    }

    private fun showPermissionsDeclinedCantAskMoreDialog() {
        alertDialog = requireContext().getPermissionsDeclinedCantAskMoreDialog(
            R.string.record_audio_permissions_denied_message,
            this
        )
        alertDialog?.show()
    }

    private fun startPagerScreen() {
        findNavController().navigate(R.id.action_micPermissionsFragment_to_pagerFragment)
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

    override fun onStop() {
        super.onStop()
        alertDialog?.dismiss()
        alertDialog = null
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1002
    }
}