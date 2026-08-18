package com.senaaydan.cinebee_.presentation.settings

sealed class SettingsIntent{
    data class ThemePreferenceChanged(val themePreference: ThemePreference): SettingsIntent()
    data class LanguagePreferenceChanged(val languagePreference: LanguagePreference) : SettingsIntent()
}