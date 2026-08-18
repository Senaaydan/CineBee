package com.senaaydan.cinebee_.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senaaydan.cinebee_.data.local.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(
        SettingsState()
    )

    val state = _state.asStateFlow()

    init {
        observeTheme()
        observeLanguage()
    }

    private fun observeTheme() {

        viewModelScope.launch {

            settingsDataStore.themePreferenceFlow.collect { themePreference ->

                _state.value = _state.value.copy(
                    themePreference = themePreference
                )
            }
        }
    }

    fun onIntent(intent: SettingsIntent) {

        when(intent) {

            is SettingsIntent.ThemePreferenceChanged -> {

                ThemePreferenceChanged(intent.themePreference)
            }
            is SettingsIntent.LanguagePreferenceChanged -> {
               LanguagePreferenceChanged(intent.languagePreference)
            }
        }
    }
    private fun observeLanguage() {
        viewModelScope.launch {
            settingsDataStore.languagePreferenceFlow.collect { languagePreference ->

                _state.value = _state.value.copy(
                    languagePreference = languagePreference
                )
            }
        }
    }
    private fun ThemePreferenceChanged(themePreference: ThemePreference){
        viewModelScope.launch {

            settingsDataStore.saveTheme(
                themePreference
            )
        }

    }
    private fun LanguagePreferenceChanged(languagePreference: LanguagePreference){
        viewModelScope.launch {
            settingsDataStore.saveLanguage(
                languagePreference
            )
        }

    }
}