package com.hack.antisurveillance.presentation.screens.pager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hack.antisurveillance.R
import com.hack.antisurveillance.android.recorder.AudioRecorderService
import com.hack.antisurveillance.presentation.extensions.ImageUtils
import com.hack.antisurveillance.presentation.extensions.getBitmapFromVector
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import com.hack.antisurveillance.presentation.widgets.SettingsMenuItemView
import kotlinx.android.synthetic.main.fragment_pager.*


class PagerFragment : BaseFragment(R.layout.fragment_pager) {

    private lateinit var pageTitleProvider: PageTitleProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageTitleProvider = PageTitleProviderImpl(requireContext())
        startRecordMicService()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = ScreensPagerAdapter(
            pageTitleProvider,
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        tabLayout.setupWithViewPager(viewPager, true)
        initializeTabs()
    }

    private fun startRecordMicService() {
        requireContext().run {
            ContextCompat.startForegroundService(
                this,
                Intent(this, AudioRecorderService::class.java)
            )
        }
    }

    private fun initializeTabs() {
        setupHomeTab()
        setupMicTab()
        setupGpsTab()
        setupSettingsItemTab()
    }

    private fun setupSettingsItemTab() {
        val view = SettingsMenuItemView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            updateLayoutParams {
                width = resources.getDimensionPixelOffset(R.dimen.pin_tab_size)
                height = resources.getDimensionPixelOffset(R.dimen.pin_tab_size)
            }
        }
        tabLayout.getTabAt(3)?.customView = view
    }

    private fun setupGpsTab() {
        val icMic = ImageView(requireContext())
        val tmpLocationBitmap = requireContext().getBitmapFromVector(R.drawable.ic_pin)
        val locationBitmap = ImageUtils().addGradient(tmpLocationBitmap) ?: tmpLocationBitmap

        icMic.setImageBitmap(locationBitmap)
        icMic.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        icMic.updateLayoutParams {
            width = resources.getDimensionPixelOffset(R.dimen.pin_tab_size)
            height = resources.getDimensionPixelOffset(R.dimen.pin_tab_size)
        }
        tabLayout.getTabAt(2)?.customView = icMic
    }

    private fun setupMicTab() {
        val icMic = ImageView(requireContext())
        val tmpLocationBitmap = requireContext().getBitmapFromVector(R.drawable.ic_microphone)
        val locationBitmap = ImageUtils().addGradient(tmpLocationBitmap) ?: tmpLocationBitmap

        icMic.setImageBitmap(locationBitmap)
        icMic.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        icMic.updateLayoutParams {
            width = resources.getDimensionPixelOffset(R.dimen.mic_tab_size)
            height = resources.getDimensionPixelOffset(R.dimen.mic_tab_size)
        }
        tabLayout.getTabAt(1)?.customView = icMic
    }

    private fun setupHomeTab() {
        val logo = ImageView(requireContext())
        logo.setImageResource(R.drawable.app_logo)
        logo.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        logo.updateLayoutParams {
            width = resources.getDimensionPixelOffset(R.dimen.home_tab_size)
            height = resources.getDimensionPixelOffset(R.dimen.home_tab_size)
        }
        tabLayout.getTabAt(0)?.customView = logo
    }
}