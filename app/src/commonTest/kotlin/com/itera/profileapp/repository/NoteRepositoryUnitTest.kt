package com.itera.profileapp.repository

import com.itera.profileapp.data.repository.NoteRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class NoteRepositoryUnitTest {

    private lateinit var repository: NoteRepository

    @BeforeTest
    fun setup() {
        repository = NoteRepository()
    }

    @AfterTest
    fun tearDown() = runTest {
        repository.resetData()
    }

    @Test
    fun `test insertNote adds note successfully`() = runTest {
        repository.insertNote("Shopping List", "Buy milk and eggs")
        val notes = repository.searchNotes("").first()

        assertEquals(1, notes.size)
        assertEquals("Shopping List", notes[0].title)
    }

    @Test
    fun `test deleteNoteById removes correct note`() = runTest {
        repository.insertNote("Note 1", "Content 1")
        repository.insertNote("Note 2", "Content 2")
        val notesBefore = repository.searchNotes("").first()
        val noteToDelete = notesBefore.find { it.title == "Note 1" }

        repository.deleteNoteById(noteToDelete!!.id)
        val notesAfter = repository.searchNotes("").first()

        assertEquals(2, notesBefore.size)
        assertEquals(1, notesAfter.size)
        assertNull(notesAfter.find { it.id == noteToDelete.id })
    }

    @Test
    fun `test getNoteById returns correct note`() = runTest {
        repository.insertNote("Test Title", "Test Content")
        val notes = repository.searchNotes("").first()
        val insertedNote = notes[0]

        val retrievedNote = repository.getNoteById(insertedNote.id)

        assertNotNull(retrievedNote)
        assertEquals(insertedNote.id, retrievedNote.id)
        assertEquals("Test Title", retrievedNote.title)
    }

    @Test
    fun `test getNoteById returns null for non-existent id`() = runTest {
        val result = repository.getNoteById(999L)
        assertNull(result)
    }

    @Test
    fun `test updateNote modifies existing note`() = runTest {
        repository.insertNote("Original Title", "Original Content")
        val notes = repository.searchNotes("").first()
        val noteId = notes[0].id

        repository.updateNote(noteId, "Updated Title", "Updated Content")
        val updatedNote = repository.getNoteById(noteId)

        assertNotNull(updatedNote)
        assertEquals("Updated Title", updatedNote.title)
        assertEquals("Updated Content", updatedNote.content)
    }

    @Test
    fun `test searchNotes filters by title`() = runTest {
        repository.insertNote("Grocery Shopping", "Buy vegetables")
        repository.insertNote("Work Meeting", "Prepare slides")

        val result = repository.searchNotes("Grocery").first()

        assertEquals(1, result.size)
        assertEquals("Grocery Shopping", result[0].title)
    }
}