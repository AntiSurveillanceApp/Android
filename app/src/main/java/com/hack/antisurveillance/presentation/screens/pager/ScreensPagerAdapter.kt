package com.hack.antisurveillance.presentation.screens.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hack.antisurveillance.presentation.screens.pager.home.HomeFragment
import com.hack.antisurveillance.presentation.screens.pager.map.MapFragment
import com.hack.antisurveillance.presentation.screens.pager.mic.MicrophoneFragment
import com.hack.antisurveillance.presentation.screens.pager.settings.SettingsFragment

class ScreensPagerAdapter(
    private val pageTitleProvider: PageTitleProvider,
    fm: FragmentManager,
    behavior: Int
) :
    FragmentStatePagerAdapter(fm, behavior) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> MicrophoneFragment()
            2 -> MapFragment()
            3 -> SettingsFragment()
            else -> {
                throw IllegalStateException("Position $position does not have linked fragment")
            }
        }
    }
}