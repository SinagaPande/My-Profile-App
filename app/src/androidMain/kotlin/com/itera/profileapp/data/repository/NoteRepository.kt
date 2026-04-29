package com.itera.profileapp.data.repository

import com.itera.profileapp.NoteRepositoryInterface
import com.itera.profileapp.data.local.NoteEntity
import com.itera.profileapp.data.local.NotesDatabase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val database: NotesDatabase) : NoteRepositoryInterface {

    override fun searchNotes(query: String): Flow<List<NoteEntity>> {
        return if (query.isBlank()) {
            database.noteQueries.getAllNotes()
                .asFlow()
                .mapToList(Dispatchers.IO)
        } else {
            database.noteQueries.searchNotes("%$query%", "%$query%")
                .asFlow()
                .mapToList(Dispatchers.IO)
        }
    }

    override suspend fun insertNote(title: String, content: String) {
        database.noteQueries.insertNote(
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
    }

    override suspend fun deleteNoteById(id: Long) {
        database.noteQueries.deleteNote(id)
    }

    override suspend fun getNoteById(id: Long): NoteEntity? {
        return database.noteQueries.getNoteById(id).executeAsOneOrNull()
    }

    override suspend fun updateNote(id: Long, title: String, content: String) {
        database.noteQueries.updateNote(title = title, content = content, id = id)
    }
}