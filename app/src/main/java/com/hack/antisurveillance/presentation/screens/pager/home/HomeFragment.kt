package com.hack.antisurveillance.presentation.screens.pager.home

import android.os.Bundle
import com.hack.antisurveillance.R
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by lifecycleScope.viewModel(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.micState.observe(viewLifecycleOwner, {
            textViewMicStatus.text = it.getStatus()
        })

        viewModel.locationState.observe(viewLifecycleOwner, {
            textViewGpsStatus.text = it.getStatus()
        })
    }

    private fun Boolean.getStatus(): String {
        return if (this) {
            getString(R.string.enabled)
        } else {
            getString(R.string.disabled)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkStates()
    }
}