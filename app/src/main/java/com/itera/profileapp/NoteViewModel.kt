package com.itera.profileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itera.profileapp.data.local.NoteEntity
import com.itera.profileapp.data.repository.NoteRepository
import com.itera.profileapp.data.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty : NotesUiState()
    data class Success(val notes: List<NoteEntity>) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}

class NoteViewModel(
    private val repository: NoteRepository,
    private val preferencesRepository: UserPreferencesRepository // Parameter baru
) : ViewModel() {
    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // State untuk memantau urutan (Ascending = true berarti Terlama di atas)
    val isSortAscending: StateFlow<Boolean> = preferencesRepository.isSortOrderAsc
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private var searchJob: Job? = null

    init {
        loadNotes("")
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
        loadNotes(newQuery)
    }

    // Fungsi untuk membalikkan urutan saat tombol ditekan
    fun toggleSortOrder() {
        viewModelScope.launch {
            val currentOrder = isSortAscending.value
            preferencesRepository.toggleSortOrder(!currentOrder)
            loadNotes(_searchQuery.value) // Muat ulang catatan dengan urutan baru
        }
    }

    private fun loadNotes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = NotesUiState.Loading
            repository.searchNotes(query)
                .catch { e -> _uiState.value = NotesUiState.Error(e.message ?: "Terjadi kesalahan") }
                .collect { notes ->
                    val isAsc = preferencesRepository.isSortOrderAsc.first()
                    // Urutkan di Kotlin secara dinamis
                    val sortedNotes = if (isAsc) {
                        notes.sortedBy { it.createdAt }
                    } else {
                        notes.sortedByDescending { it.createdAt }
                    }

                    if (sortedNotes.isEmpty()) _uiState.value = NotesUiState.Empty
                    else _uiState.value = NotesUiState.Success(sortedNotes)
                }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) { repository.insertNote(title, content) }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteNoteById(id) }
    }

    suspend fun getNote(id: Long): NoteEntity? {
        return withContext(Dispatchers.IO) { repository.getNoteById(id) }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateNote(id, title, content) }
    }
}