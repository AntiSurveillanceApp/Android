package com.hack.antisurveillance.presentation.screens.pager.mic

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hack.antisurveillance.R
import com.hack.antisurveillance.presentation.extensions.applyTint
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_microphone.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MicrophoneFragment : BaseFragment(R.layout.fragment_microphone) {

    private val viewModel: MicViewModel by lifecycleScope.viewModel(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchMicrophone.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onMicrophoneToggled(isChecked)
            renderImageColorDependsOnMicState(isChecked)
            renderMicStateMessage(isChecked)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.isMicBlocked.observe(viewLifecycleOwner, {
            switchMicrophone.isChecked = it
            renderImageColorDependsOnMicState(it)
        })
    }

    private fun renderImageColorDependsOnMicState(isChecked: Boolean) {
        val color = if (isChecked) R.color.colorAccent else R.color.white
        imageViewMicrophone.applyTint(color)
    }

    private fun renderMicStateMessage(isChecked: Boolean) {
        val msgRes = if (isChecked) R.string.mic_state_locked else R.string.mic_state_unlocked
        Toast.makeText(requireContext(), msgRes, Toast.LENGTH_SHORT).show()
    }
}