package com.sampleweather.ui.setting

import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceClickListener
import androidx.preference.PreferenceFragmentCompat
import com.sampleweather.R
import com.sampleweather.ui.home.HomeViewModel
import com.sampleweather.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val button: Preference =
            preferenceManager.findPreference(requireActivity().getString(R.string.clear))!!
        button.onPreferenceClickListener = OnPreferenceClickListener {
            viewModel.clearAllData()
            requireActivity().toast("Clear bookmark.")
            true
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}