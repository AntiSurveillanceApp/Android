package com.hack.antisurveillance.presentation.screens.pager

import android.content.Context
import com.hack.antisurveillance.R

class PageTitleProviderImpl(private val context: Context) : PageTitleProvider {

    override fun getTitle(position: Int): String {
        val stringResource = when (position) {
            0 -> R.string.home_tab
            1 -> R.string.mic_tab
            2 -> R.string.geo_tab
            3 -> R.string.settings_tab
            else -> throw IllegalArgumentException(
                String.format(
                    UNKNOWN_POSITION_EXCEPTION_MSG,
                    position
                )
            )
        }
        return context.getString(stringResource)
    }

    companion object {
        private const val UNKNOWN_POSITION_EXCEPTION_MSG = "Unknown position %d"
    }
}