package com.itera.profileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itera.profileapp.data.local.DatabaseDriverFactory
import com.itera.profileapp.data.local.NotesDatabase
import com.itera.profileapp.data.repository.NoteRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itera.profileapp.navigation.AppNavigation // Tambahkan import ini
import com.itera.profileapp.ui.theme.ProfileAppTheme
import com.itera.profileapp.data.repository.UserPreferencesRepository

class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inisialisasi Database & Repositori
        val driver = DatabaseDriverFactory(this).createDriver()
        val database = NotesDatabase(driver)
        val noteRepository = NoteRepository(database)
        
        // Inisialisasi Preferences Repository
        val preferencesRepository = UserPreferencesRepository(this)

        setContent {
            // Buat ProfileViewModel menggunakan Factory
            val profileViewModel: ProfileViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ProfileViewModel(preferencesRepository) as T
                    }
                }
            )
            
            val uiState by profileViewModel.uiState.collectAsState()

            // Buat NoteViewModel menggunakan Factory
            val noteViewModel: NoteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        // Tambahkan preferencesRepository di sini
                        return NoteViewModel(noteRepository, preferencesRepository) as T
                    }
                }
            )

            ProfileAppTheme(darkTheme = uiState.isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        profileViewModel = profileViewModel,
                        noteViewModel = noteViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (uiState.isDarkMode) "Dark Mode" else "Light Mode",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode() }
            )
        }

        ProfileHeader(name = uiState.name)

        Text(
            text = uiState.bio,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                InfoItem(icon = "@", label = "Email", value = uiState.email)
                InfoItem(icon = "📞", label = "Phone", value = uiState.phone)
                InfoItem(icon = "📍", label = "Location", value = uiState.location)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Total Projects: 12",
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.White, RoundedCornerShape(4.dp))
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showEditDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Profile")
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            initialName = uiState.name,
            initialBio = uiState.bio,
            onDismiss = { showEditDialog = false },
            onSave = { newName, newBio ->
                viewModel.updateProfile(newName, newBio)
                showEditDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileAppTheme {
        ProfileScreen()
    }
}