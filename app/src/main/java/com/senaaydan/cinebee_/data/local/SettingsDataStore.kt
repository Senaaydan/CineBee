package com.senaaydan.cinebee_.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.senaaydan.cinebee_.presentation.settings.LanguagePreference
import com.senaaydan.cinebee_.presentation.settings.ThemePreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class SettingsDataStore @Inject constructor( @ApplicationContext
    private val context: Context
) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
    }
    suspend fun saveTheme(themePreference: ThemePreference) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = themePreference.name
        }
    }

    val themePreferenceFlow: Flow<ThemePreference> =
        context.dataStore.data.map { preferences ->

            val themeName = preferences[THEME_KEY]

            when (themeName) {
                ThemePreference.LIGHT.name -> ThemePreference.LIGHT
                ThemePreference.DARK.name -> ThemePreference.DARK
                else -> ThemePreference.SYSTEM
            }
        }
    suspend fun saveLanguage(languagePreference: LanguagePreference) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = languagePreference.name
        }
    }

    val languagePreferenceFlow: Flow<LanguagePreference> =
        context.dataStore.data.map { preferences ->

            when (preferences[LANGUAGE_KEY]) {

                LanguagePreference.TURKISH.name -> LanguagePreference.TURKISH

                LanguagePreference.ENGLISH.name -> LanguagePreference.ENGLISH

                else -> LanguagePreference.ENGLISH
            }
        }


}
