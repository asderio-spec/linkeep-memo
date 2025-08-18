package com.linkeep.memo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkeep.memo.data.AIProvider
import com.linkeep.memo.data.SettingsRepository
import com.linkeep.memo.data.ThemeMode
import com.linkeep.memo.data.ViewMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val viewMode: StateFlow<ViewMode> = settingsRepository.viewModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewMode.CARD)

    val themeMode: StateFlow<ThemeMode> = settingsRepository.themeModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeMode.SYSTEM)

    val language: StateFlow<String> = settingsRepository.languageFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "ko")

    val aiProvider: StateFlow<AIProvider> = settingsRepository.aiProviderFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AIProvider.LOCAL)

    val aiApiKey: StateFlow<String> = settingsRepository.aiApiKeyFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun setViewMode(mode: ViewMode) = viewModelScope.launch {
        settingsRepository.setViewMode(mode)
    }

    fun setThemeMode(mode: ThemeMode) = viewModelScope.launch {
        settingsRepository.setThemeMode(mode)
    }

    fun setLanguage(lang: String) = viewModelScope.launch {
        settingsRepository.setLanguage(lang)
    }

    fun setAIProvider(provider: AIProvider) = viewModelScope.launch {
        settingsRepository.setAIProvider(provider)
    }

    fun setAIApiKey(key: String) = viewModelScope.launch {
        settingsRepository.setAIApiKey(key)
    }
}
