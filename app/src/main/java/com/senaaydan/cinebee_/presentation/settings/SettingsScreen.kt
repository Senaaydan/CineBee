package com.senaaydan.cinebee_.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun SettingsScreen(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    strings: AppStrings
) {

    var themeExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = strings.settings,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = strings.appearance,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Box {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        themeExpanded = true
                    }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.DarkMode,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(16.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = strings.theme,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = when (state.themePreference) {
                            ThemePreference.SYSTEM -> strings.system
                            ThemePreference.LIGHT -> strings.light
                            ThemePreference.DARK -> strings.dark
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = strings.chooseTheme
                )
            }

            DropdownMenu(
                expanded = themeExpanded,
                onDismissRequest = {
                    themeExpanded = false
                }
            ) {

                DropdownMenuItem(
                    text = {
                        Text(strings.system)
                    },
                    onClick = {
                        onIntent(
                            SettingsIntent.ThemePreferenceChanged(
                                ThemePreference.SYSTEM
                            )
                        )

                        themeExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(strings.light)
                    },
                    onClick = {
                        onIntent(
                            SettingsIntent.ThemePreferenceChanged(
                                ThemePreference.LIGHT
                            )
                        )

                        themeExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(strings.dark)
                    },
                    onClick = {
                        onIntent(
                            SettingsIntent.ThemePreferenceChanged(
                                ThemePreference.DARK
                            )
                        )

                        themeExpanded = false
                    }
                )
            }
        }

        HorizontalDivider()
        var languageExpanded by remember {
            mutableStateOf(false)
        }

        Box {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        languageExpanded = true
                    }
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(16.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = strings.language,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = when (state.languagePreference) {
                            LanguagePreference.TURKISH -> strings.turkish
                            LanguagePreference.ENGLISH -> strings.english
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = strings.languagePreference
                )
            }

            DropdownMenu(
                expanded = languageExpanded,
                onDismissRequest = {
                    languageExpanded = false
                }
            ) {

                DropdownMenuItem(
                    text = {
                        Text(strings.turkish)
                    },
                    onClick = {

                        onIntent(
                            SettingsIntent.LanguagePreferenceChanged(
                                LanguagePreference.TURKISH
                            )
                        )

                        languageExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(strings.english)
                    },
                    onClick = {

                        onIntent(
                            SettingsIntent.LanguagePreferenceChanged(
                                LanguagePreference.ENGLISH
                            )
                        )

                        languageExpanded = false
                    }
                )
            }
        }
    }

}
