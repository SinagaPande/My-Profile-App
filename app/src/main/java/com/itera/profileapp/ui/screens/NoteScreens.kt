package com.itera.profileapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NotesScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToAdd: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Notes List Screen")
            Button(onClick = { onNavigateToDetail("123") }) { // Contoh ID "123"
                Text("Buka Detail Note 123")
            }
        }
    }
}

@Composable
fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Favorites Screen")
    }
}

@Composable
fun NoteDetailScreen(noteId: String, onNavigateToEdit: (String) -> Unit, onNavigateBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Detail Note ID: $noteId")
        Button(onClick = { onNavigateToEdit(noteId) }) { Text("Edit Note") }
        Button(onClick = onNavigateBack) { Text("Kembali") }
    }
}

@Composable
fun AddNoteScreen(onNavigateBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Add Note Screen")
        Button(onClick = onNavigateBack) { Text("Simpan & Kembali") }
    }
}

@Composable
fun EditNoteScreen(noteId: String, onNavigateBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Edit Note ID: $noteId")
        Button(onClick = onNavigateBack) { Text("Simpan Perubahan & Kembali") }
    }
}