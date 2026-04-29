package com.itera.profileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val name: String = "Jonathan Sinaga | 123140153",
    val bio: String = "Mahasiswa Informatika ITERA | Mobile Developer",
    val email: String = "jonathan.123140153@student.itera.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Lampung, Indonesia",
    val isDarkMode: Boolean = false
)

interface PreferencesRepository {
    val isDarkMode: kotlinx.coroutines.flow.Flow<Boolean>
    suspend fun toggleDarkMode(isDark: Boolean)
}

class ProfileViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.isDarkMode.collect { isDark ->
                _uiState.update { it.copy(isDarkMode = isDark) }
            }
        }
    }

    fun updateProfile(newName: String, newBio: String) {
        _uiState.update { currentState ->
            currentState.copy(name = newName, bio = newBio)
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            val currentMode = _uiState.value.isDarkMode
            preferencesRepository.toggleDarkMode(!currentMode)
        }
    }
}