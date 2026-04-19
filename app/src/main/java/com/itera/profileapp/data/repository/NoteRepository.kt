package com.itera.profileapp.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.itera.profileapp.data.local.NoteEntity
import com.itera.profileapp.data.local.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NoteRepository(db: NotesDatabase) {
    private val queries = db.noteQueries

    fun getAllNotes(): Flow<List<NoteEntity>> {
        return queries.getAllNotes().asFlow().mapToList(Dispatchers.IO)
    }

    fun getNoteById(id: Long): NoteEntity? {
        return queries.getNoteById(id).executeAsOneOrNull()
    }

    fun insertNote(title: String, content: String) {
        queries.insertNote(
            id = null, // Auto-increment
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
    }

    fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }

    fun updateNote(id: Long, title: String, content: String) {
        queries.insertNote(
            id = id,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
    }

    // Ini adalah fungsi pencarian yang sebelumnya terlewat!
    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return queries.searchNotes(query).asFlow().mapToList(Dispatchers.IO)
    }
}