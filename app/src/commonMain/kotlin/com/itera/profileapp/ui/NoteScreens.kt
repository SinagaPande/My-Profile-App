package com.itera.profileapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itera.profileapp.NoteViewModel
import com.itera.profileapp.NotesUiState
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

// ... (biarkan NoteDetailScreen dan EditNoteScreen yang sudah ada)

@Composable
fun NotesScreen(
    viewModel: NoteViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToAdd: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSortAscending by viewModel.isSortAscending.collectAsState() // Observe status urutan

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Ubah bagian ini menggunakan Row agar tombol bersebelahan dengan Search
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    label = { Text("Cari catatan...") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { viewModel.toggleSortOrder() }) {
                    Icon(
                        imageVector = if (isSortAscending) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Sort Notes"
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.Center) {
                when (val state = uiState) {
                    is NotesUiState.Loading -> CircularProgressIndicator()
                    is NotesUiState.Empty -> Text("Catatan tidak ditemukan.")
                    is NotesUiState.Error -> Text("Error: ${state.message}")
                    is NotesUiState.Success -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.notes) { note ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .clickable { onNavigateToDetail(note.id.toString()) }
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                                        Text(text = note.content, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddNoteScreen(
    onSaveNote: (String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan") },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    onSaveNote(title, content)
                    onNavigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

// Tambahkan juga ini sementara agar error FavoritesScreen di AppNavigation hilang
@Composable
fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Halaman Favorites Belum Tersedia")
    }
}

@Composable
fun NoteDetailScreen(
    noteId: String,
    viewModel: NoteViewModel,
    onNavigateToEdit: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var note by remember { mutableStateOf<com.itera.profileapp.data.local.NoteEntity?>(null) }
    
    LaunchedEffect(noteId) {
        note = viewModel.getNote(noteId.toLongOrNull() ?: 0L)
    }

    note?.let { currentNote ->
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigateToEdit(noteId) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text(text = currentNote.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = currentNote.content, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { 
                        viewModel.deleteNote(currentNote.id)
                        onNavigateBack() 
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                    Spacer(Modifier.width(8.dp))
                    Text("Hapus Catatan")
                }
            }
        }
    }
}

@Composable
fun EditNoteScreen(
    noteId: String,
    viewModel: NoteViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(noteId) {
        viewModel.getNote(noteId.toLongOrNull() ?: 0L)?.let {
            title = it.title
            content = it.content
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan") },
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.updateNote(noteId.toLong(), title, content)
                onNavigateBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Catatan")
        }
    }
}