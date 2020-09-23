package com.hack.antisurveillance.presentation.screens.info

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.hack.antisurveillance.R
import com.hack.antisurveillance.presentation.extensions.setSafeClickListener
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : BaseFragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stringId = arguments?.getInt(BUNDLE_TEXT_KEY, 0) ?: 0
        if (stringId != 0) {
            textViewBody.text = getString(stringId)
        }

        imageViewClose.setSafeClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        const val BUNDLE_TEXT_KEY = "text_key"
    }
}