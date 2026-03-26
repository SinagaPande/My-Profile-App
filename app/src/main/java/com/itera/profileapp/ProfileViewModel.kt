package com.itera.profileapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// 1. Data Class untuk menyimpan seluruh state layar
data class ProfileUiState(
    val name: String = "Jonathan Sinaga | 123140153",
    val bio: String = "Mahasiswa Informatika ITERA | Mobile Developer",
    val email: String = "jonathan.123140153@student.itera.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Lampung, Indonesia",
    val isDarkMode: Boolean = false
)

// 2. Class ViewModel
class ProfileViewModel : ViewModel() {
    
    // _uiState bersifat "Private" dan "Mutable" (bisa diubah), hanya dikelola di dalam ViewModel.
    private val _uiState = MutableStateFlow(ProfileUiState())
    
    // uiState bersifat "Public" dan "Read-only" (hanya bisa dibaca). UI akan mengobservasi variabel ini.
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // Fungsi yang akan dipanggil saat tombol Save ditekan
    fun updateProfile(newName: String, newBio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = newName,
                bio = newBio
            )
        }
    }

    // Fungsi yang akan dipanggil saat Switch/Toggle ditekan
    fun toggleDarkMode() {
        _uiState.update { currentState ->
            currentState.copy(
                isDarkMode = !currentState.isDarkMode
            )
        }
    }
}