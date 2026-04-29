package com.itera.profileapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.itera.profileapp.ProfileScreen // Tambahkan import ini
import com.itera.profileapp.ProfileViewModel // Tambahkan import ini
import com.itera.profileapp.ui.screens.*
import com.itera.profileapp.NoteViewModel
import com.itera.profileapp.ui.screens.NotesScreen
import com.itera.profileapp.ui.screens.AddNoteScreen
import com.itera.profileapp.ui.screens.NoteDetailScreen
import com.itera.profileapp.ui.screens.EditNoteScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel,
    noteViewModel: NoteViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(Screen.Notes.route, Screen.Favorites.route, Screen.Profile.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Notes") },
                        label = { Text("Notes") },
                        selected = currentRoute == Screen.Notes.route,
                        onClick = {
                            navController.navigate(Screen.Notes.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                        label = { Text("Favorites") },
                        selected = currentRoute == Screen.Favorites.route,
                        onClick = {
                            navController.navigate(Screen.Favorites.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. Tab Notes
            composable(Screen.Notes.route) {
                // Nanti kita akan update parameter NotesScreen di Step 6
                NotesScreen(
                    viewModel = noteViewModel, // Tambahkan ini
                    onNavigateToDetail = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                    onNavigateToAdd = { navController.navigate(Screen.AddNote.route) }
                )
            }
            
            // 2. Tab Favorites
            composable(Screen.Favorites.route) { FavoritesScreen() }
            
            // 3. Tab Profile (Panggil ProfileScreen di sini)
            composable(Screen.Profile.route) { 
                ProfileScreen(viewModel = profileViewModel) 
            }

            // 4. Detail Note
            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.StringType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
                NoteDetailScreen(
                    noteId = noteId,
                    viewModel = noteViewModel, // Parameter baru
                    onNavigateToEdit = { id -> navController.navigate(Screen.EditNote.createRoute(id)) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // 5. Add Note
            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    onSaveNote = { title, content -> noteViewModel.addNote(title, content) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // 6. Edit Note
            composable(
                route = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.StringType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
                EditNoteScreen(
                    noteId = noteId,
                    viewModel = noteViewModel, // Parameter baru
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}