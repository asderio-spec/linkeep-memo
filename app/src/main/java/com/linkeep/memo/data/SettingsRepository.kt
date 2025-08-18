package com.linkeep.memo.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class ViewMode { LIST, CARD }
enum class ThemeMode { SYSTEM, LIGHT, DARK }
enum class AIProvider { LOCAL, OPENAI, GEMINI, CLAUDE }

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    private object Keys {
        val VIEW_MODE = intPreferencesKey("view_mode")
        val THEME_MODE = intPreferencesKey("theme_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val AI_PROVIDER = intPreferencesKey("ai_provider")
        val AI_API_KEY = stringPreferencesKey("ai_api_key")
    }

    val viewModeFlow: Flow<ViewMode> = appContext.settingsDataStore.data.map { pref ->
        ViewMode.values().getOrElse(pref[Keys.VIEW_MODE] ?: ViewMode.CARD.ordinal) { ViewMode.CARD }
    }

    val themeModeFlow: Flow<ThemeMode> = appContext.settingsDataStore.data.map { pref ->
        ThemeMode.values().getOrElse(pref[Keys.THEME_MODE] ?: ThemeMode.SYSTEM.ordinal) { ThemeMode.SYSTEM }
    }

    val languageFlow: Flow<String> = appContext.settingsDataStore.data.map { pref ->
        pref[Keys.LANGUAGE] ?: "ko"
    }

    val aiProviderFlow: Flow<AIProvider> = appContext.settingsDataStore.data.map { pref ->
        AIProvider.values().getOrElse(pref[Keys.AI_PROVIDER] ?: AIProvider.LOCAL.ordinal) { AIProvider.LOCAL }
    }

    val aiApiKeyFlow: Flow<String> = appContext.settingsDataStore.data.map { pref ->
        pref[Keys.AI_API_KEY] ?: ""
    }

    suspend fun setViewMode(mode: ViewMode) {
        appContext.settingsDataStore.edit { it[Keys.VIEW_MODE] = mode.ordinal }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        appContext.settingsDataStore.edit { it[Keys.THEME_MODE] = mode.ordinal }
    }

    suspend fun setLanguage(lang: String) {
        appContext.settingsDataStore.edit { it[Keys.LANGUAGE] = lang }
    }

    suspend fun setAIProvider(provider: AIProvider) {
        appContext.settingsDataStore.edit { it[Keys.AI_PROVIDER] = provider.ordinal }
    }

    suspend fun setAIApiKey(key: String) {
        appContext.settingsDataStore.edit { it[Keys.AI_API_KEY] = key }
    }
}
