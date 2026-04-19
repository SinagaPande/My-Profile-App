package com.itera.profileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itera.profileapp.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val name: String = "Jonathan Sinaga | 123140153", // Sesuaikan dengan data Anda
    val bio: String = "Mahasiswa Informatika ITERA | Mobile Developer",
    val email: String = "jonathan.123140153@student.itera.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Lampung, Indonesia",
    val isDarkMode: Boolean = false
)

class ProfileViewModel(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        // Membaca status Dark Mode dari DataStore saat ViewModel dibuat
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
            // Simpan status baru ke DataStore (nilai UI akan otomatis ter-update lewat collect di init)
            val currentMode = _uiState.value.isDarkMode
            preferencesRepository.toggleDarkMode(!currentMode)
        }
    }
}