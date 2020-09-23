package com.hack.antisurveillance.presentation.screens.pager

interface PageTitleProvider {
    fun getTitle(position: Int): String
}