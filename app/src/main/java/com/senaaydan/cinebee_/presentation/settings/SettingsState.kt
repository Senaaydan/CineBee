package com.senaaydan.cinebee_.presentation.settings

data class SettingsState(
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val languagePreference: LanguagePreference = LanguagePreference.ENGLISH
)