package com.itera.profileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itera.profileapp.data.local.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface NoteRepositoryInterface {
    suspend fun insertNote(title: String, content: String)
    suspend fun deleteNoteById(id: Long)
    suspend fun getNoteById(id: Long): NoteEntity?
    suspend fun updateNote(id: Long, title: String, content: String)
    fun searchNotes(query: String): Flow<List<NoteEntity>>
}

interface SortPreferencesRepository {
    val isSortOrderAsc: Flow<Boolean>
    suspend fun toggleSortOrder(isAsc: Boolean)
}

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty : NotesUiState()
    data class Success(val notes: List<NoteEntity>) : NotesUiState()
    data class Error(val message: String) : NotesUiState()
}

class NoteViewModel(
    private val repository: NoteRepositoryInterface,
    private val preferencesRepository: SortPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Loading)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

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

    fun toggleSortOrder() {
        viewModelScope.launch {
            val currentOrder = isSortAscending.value
            preferencesRepository.toggleSortOrder(!currentOrder)
            loadNotes(_searchQuery.value)
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