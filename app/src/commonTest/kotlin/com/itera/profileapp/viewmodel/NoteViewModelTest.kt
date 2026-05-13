package com.itera.profileapp.viewmodel

import com.itera.profileapp.NoteRepositoryInterface
import com.itera.profileapp.NoteViewModel
import com.itera.profileapp.NotesUiState
import com.itera.profileapp.SortPreferencesRepository
import com.itera.profileapp.data.local.NoteEntity
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    private lateinit var mockRepository: NoteRepositoryInterface
    private lateinit var mockPreferences: SortPreferencesRepository
    private lateinit var viewModel: NoteViewModel

    private val testNotes = listOf(
        NoteEntity(id = 1, title = "Belajar KMP", content = "Testing dengan MockK", createdAt = System.currentTimeMillis()),
        NoteEntity(id = 2, title = "Shopping", content = "Beli susu", createdAt = System.currentTimeMillis())
    )

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        
        mockRepository = mockk(relaxed = true)
        mockPreferences = mockk(relaxed = true)
        
        // Default stubbing
        coEvery { mockRepository.searchNotes(any()) } returns flowOf(emptyList())
        every { mockPreferences.isSortOrderAsc } returns flowOf(true)
        
        viewModel = NoteViewModel(mockRepository, mockPreferences)
    }

    @AfterTest
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `addNote calls repository insertNote`() = runTest {
        val title = "New Note"
        val content = "New Content"

        viewModel.addNote(title, content)

        coVerify { mockRepository.insertNote(title, content) }
    }

    @Test
    fun `deleteNote calls repository deleteNoteById with correct id`() = runTest {
        val noteId = 1L

        viewModel.deleteNote(noteId)

        coVerify { mockRepository.deleteNoteById(noteId) }
    }

    @Test
    fun `getNote returns correct note from repository`() = runTest {
        val expectedNote = NoteEntity(id = 1, title = "Test", content = "Content", createdAt = System.currentTimeMillis())
        coEvery { mockRepository.getNoteById(1L) } returns expectedNote

        val result = viewModel.getNote(1L)

        coVerify { mockRepository.getNoteById(1L) }
        assertEquals(expectedNote.title, result?.title)
    }

    @Test
    fun `updateNote calls repository updateNote`() = runTest {
        val id = 1L
        val newTitle = "Updated Title"
        val newContent = "Updated Content"

        viewModel.updateNote(id, newTitle, newContent)

        coVerify { mockRepository.updateNote(id, newTitle, newContent) }
    }

    @Test
    fun `onSearchQueryChange updates searchQuery state`() = runTest {
        val query = "Belajar"
        
        viewModel.onSearchQueryChange(query)
        
        assertEquals(query, viewModel.searchQuery.value)
    }
}