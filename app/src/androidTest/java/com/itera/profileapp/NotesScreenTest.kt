package com.itera.profileapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.itera.profileapp.ui.screens.NotesScreen
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: NoteViewModel
    private lateinit var mockRepository: NoteRepositoryInterface
    private lateinit var mockPreferences: SortPreferencesRepository

    private val testNotes = listOf(
        TestNoteEntity(id = 1, title = "Belajar KMP", content = "Testing UI", createdAt = "2024-01-01"),
        TestNoteEntity(id = 2, title = "Shopping List", content = "Beli susu dan telur", createdAt = "2024-01-02"),
        TestNoteEntity(id = 3, title = "Gym Schedule", content = "Push day Senin", createdAt = "2024-01-03")
    )

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        mockPreferences = mockk(relaxed = true)
        
        mockViewModel = spyk(NoteViewModel(mockRepository, mockPreferences))
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `notes list displays all notes when state is Success`() {
        // Arrange
        val successState = NotesUiState.Success(testNotes)
        every { mockViewModel.uiState } returns MutableStateFlow(successState).asStateFlow()
        
        // Act
        composeTestRule.setContent {
            NotesScreen(
                viewModel = mockViewModel,
                onNavigateToDetail = {},
                onNavigateToAdd = {}
            )
        }
        
        // Assert
        composeTestRule.onNodeWithText("Belajar KMP").assertIsDisplayed()
        composeTestRule.onNodeWithText("Shopping List").assertIsDisplayed()
        composeTestRule.onNodeWithText("Gym Schedule").assertIsDisplayed()
    }

    @Test
    fun `empty state shows Catatan tidak ditemukan message`() {
        // Arrange
        val emptyState = NotesUiState.Empty
        every { mockViewModel.uiState } returns MutableStateFlow(emptyState).asStateFlow()
        
        // Act
        composeTestRule.setContent {
            NotesScreen(
                viewModel = mockViewModel,
                onNavigateToDetail = {},
                onNavigateToAdd = {}
            )
        }
        
        // Assert
        composeTestRule.onNodeWithText("Catatan tidak ditemukan.").assertIsDisplayed()
    }

    @Test
    fun `loading state shows CircularProgressIndicator`() {
        // Arrange
        val loadingState = NotesUiState.Loading
        every { mockViewModel.uiState } returns MutableStateFlow(loadingState).asStateFlow()
        
        // Act
        composeTestRule.setContent {
            NotesScreen(
                viewModel = mockViewModel,
                onNavigateToDetail = {},
                onNavigateToAdd = {}
            )
        }
        
        // Assert
        composeTestRule.onNodeWithTag("CircularProgressIndicator").assertExists()
    }

    @Test
    fun `error state shows error message`() {
        // Arrange
        val errorMessage = "Terjadi kesalahan jaringan"
        val errorState = NotesUiState.Error(errorMessage)
        every { mockViewModel.uiState } returns MutableStateFlow(errorState).asStateFlow()
        
        // Act
        composeTestRule.setContent {
            NotesScreen(
                viewModel = mockViewModel,
                onNavigateToDetail = {},
                onNavigateToAdd = {}
            )
        }
        
        // Assert
        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun `click FAB triggers onNavigateToAdd`() {
        // Arrange
        var navigatedToAdd = false
        val successState = NotesUiState.Success(emptyList())
        every { mockViewModel.uiState } returns MutableStateFlow(successState).asStateFlow()
        every { mockViewModel.searchQuery } returns MutableStateFlow("").asStateFlow()
        
        // Act
        composeTestRule.setContent {
            NotesScreen(
                viewModel = mockViewModel,
                onNavigateToDetail = {},
                onNavigateToAdd = { navigatedToAdd = true }
            )
        }
        
        // Assert
        composeTestRule.onNodeWithContentDescription("Add Note").performClick()
        assert(navigatedToAdd)
    }

    @Test
    fun `click note card triggers onNavigateToDetail with correct id`() {
        // Arrange
        var navigatedNoteId: String? = null
        val successState = NotesUiState.Success(testNotes)
        every { mockViewModel.uiState } returns MutableStateFlow(successState).asStateFlow()
        every { mockViewModel.searchQuery } returns MutableStateFlow("").asStateFlow()
        
        // Act
        composeTestRule.setContent {
            NotesScreen(
                viewModel = mockViewModel,
                onNavigateToDetail = { noteId -> navigatedNoteId = noteId },
                onNavigateToAdd = {}
            )
        }
        
        // Assert
        composeTestRule.onNodeWithText("Belajar KMP").performClick()
        assertEquals("1", navigatedNoteId)
    }
}

// Data class untuk testing
data class TestNoteEntity(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: String
)