package com.hack.antisurveillance.presentation.screens.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.hack.antisurveillance.R
import com.hack.antisurveillance.presentation.extensions.*
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startShowAnimation {
            startHideAnimation()
        }
    }

    private fun startHideAnimation() {
        try {
            imageViewLogo.fade(0f, 500)
            textViewSplashName.fade(0f, 1000)
            textViewPlatform.fade(0f, 1200) {
                checkPermissions()
            }
        } catch (error: Throwable) {
            error.printStackTrace()
        }
    }

    private fun startShowAnimation(endAnimCallback: () -> Unit) {
        imageViewLogo.fade(1f, 0L)
        textViewSplashName.fade(1f, 700L)
        textViewPlatform.fade(1f, 700L) {
            endAnimCallback()
        }
    }

    private fun View?.fade(alpha: Float, delay: Long, endAction: () -> Unit = {}) {
        if (this == null) return
        animate()
            .alpha(alpha)
            .setDuration(1000)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                endAction()
            }
            .setStartDelay(delay)
            .start()
    }

    private fun checkPermissions() {
        if (fineLocation.isDenied(requireContext())
            || courseLocation.isDenied(requireContext())
            || backgroundLocation.isDenied(requireContext())
        ) {
            findNavController().navigate(R.id.action_splashFragment_to_locationPermissionsFragment)
            return
        }

        if (recordAudio.isDenied(requireContext())) {
            findNavController().navigate(R.id.action_splashFragment_to_micPermissionsFragment)
            return
        }

        findNavController().navigate(R.id.action_splashFragment_to_pagerFragment)
    }
}