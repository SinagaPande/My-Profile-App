package com.itera.profileapp.data.repository

import com.itera.profileapp.NoteRepositoryInterface
import com.itera.profileapp.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class NoteRepository : NoteRepositoryInterface {
    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    private val notes = _notes.asStateFlow()
    
    private var nextId = 1L
    
    override suspend fun insertNote(title: String, content: String) {
        val newNote = NoteEntity(
            id = nextId++,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
        _notes.value = _notes.value + newNote
    }
    
    override suspend fun deleteNoteById(id: Long) {
        _notes.value = _notes.value.filter { it.id != id }
    }
    
    override suspend fun getNoteById(id: Long): NoteEntity? {
        return _notes.value.find { it.id == id }
    }
    
    override suspend fun updateNote(id: Long, title: String, content: String) {
        _notes.value = _notes.value.map { note ->
            if (note.id == id) note.copy(title = title, content = content)
            else note
        }
    }
    
    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return notes.map { list ->
            if (query.isBlank()) {
                list
            } else {
                list.filter { note ->
                    note.title.contains(query, ignoreCase = true) ||
                    note.content.contains(query, ignoreCase = true)
                }
            }
        }
    }
    
    suspend fun resetData() {
        _notes.value = emptyList()
        nextId = 1L
    }
}