package com.hack.antisurveillance.presentation.screens.pager.settings

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hack.antisurveillance.R
import com.hack.antisurveillance.domain.objects.Languages
import com.hack.antisurveillance.domain.objects.Themes
import com.hack.antisurveillance.domain.objects.getLocale
import com.hack.antisurveillance.presentation.extensions.setSafeClickListener
import com.hack.antisurveillance.presentation.screens.OnLocaleChangeListener
import com.hack.antisurveillance.presentation.screens.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class SettingsFragment : BaseFragment(R.layout.fragment_settings),
    DialogInterface.OnClickListener {

    private var alertDialog: AlertDialog? = null
    private var localeChangeListener: OnLocaleChangeListener? = null
    private val viewModel: SettingsViewModel by lifecycleScope.viewModel(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        containerLanguage.setSafeClickListener {
            viewModel.onOpenLangDialogClicked()
        }
        containerDarkTheme.setSafeClickListener {
            viewModel.onSwitchThemeClicked()
        }
        switchDarkTheme.isEnabled = false

        switchRootAccess.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwitchRootAccessChanged(isChecked)
            renderProgressBar()
        }

        switchSecureStartup.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSwitchSecureStartupChanged(isChecked)
            renderProgressBar()
        }
    }

    private fun renderProgressBar() {
        lifecycle.coroutineScope.launch {
            showProgress()
            delay(600)
            hideProgress()
        }
    }

    override fun onClick(dialog: DialogInterface?, clickId: Int) {
        when (clickId) {
            -1 -> viewModel.onSaveLanguageClicked()
            -2 -> dialog?.dismiss()
            else -> viewModel.onLanguageSelected(clickId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLocaleChangeListener) {
            localeChangeListener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.language.observe(viewLifecycleOwner, { lang ->
            renderLanguageTitle(lang)
        })

        viewModel.openLangDialog.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {
                showChooseLanguageDialog(it)
            }
        })

        viewModel.onLocaleChanged.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled()?.let {
                val locale = it.getLocale()
                localeChangeListener?.onLocaleChanged(locale)
            }
        })

        viewModel.onThemeChanged.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { theme ->
                applyAppTheme(theme)
            }
        })

        viewModel.onSwitchEnabled.observe(viewLifecycleOwner, { event ->
            setSwitchChecked(event)
        })

        viewModel.onSwitchSecureStartup.observe(viewLifecycleOwner, {
            switchSecureStartup.isChecked = it
        })

        viewModel.onSwitchRootAccess.observe(viewLifecycleOwner, {
            switchRootAccess.isChecked = it
        })

        viewModel.appVersion.observe(viewLifecycleOwner, {
            textViewAppVersion.text = it
        })
    }

    private fun applyAppTheme(theme: Themes) {
        when (theme) {
            Themes.NIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setSwitchChecked(true)
            }
            Themes.DAY -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setSwitchChecked(false)
            }
        }
    }

    private fun renderLanguageTitle(lang: Languages) {
        val langTitle = when (lang) {
            Languages.ENGLISH -> getString(R.string.english_lang)
            Languages.RUSSIAN -> getString(R.string.russian_lang)
        }
        textViewLanguage.text = langTitle
    }

    private fun showChooseLanguageDialog(langOrder: Int) {
        val languages = mutableListOf<CharSequence>(
            getString(R.string.russian_lang),
            getString(R.string.english_lang)
        )

        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.language)
            .setSingleChoiceItems(languages.toTypedArray(), langOrder, this)
            .setCancelable(true)
            .setOnCancelListener { it.dismiss() }
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()

        alertDialog?.show()
    }

    private fun setSwitchChecked(isChecked: Boolean) {
        switchDarkTheme.isEnabled = true
        switchDarkTheme.isChecked = isChecked
        switchDarkTheme.isEnabled = false
    }

    private fun showProgress() {
        progressContainer.animate()
            .alpha(1f)
            .setDuration(350)
            .withStartAction { progressContainer.isVisible = true }
            .start()
    }

    private fun hideProgress() {
        progressContainer.animate()
            .alpha(0f)
            .setDuration(350)
            .withEndAction { progressContainer.isVisible = false }
            .start()
    }

    override fun onStop() {
        super.onStop()
        alertDialog?.dismiss()
        alertDialog = null
    }
}