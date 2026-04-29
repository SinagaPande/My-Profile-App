package com.itera.profileapp.di

import com.itera.profileapp.BatteryInfo
import com.itera.profileapp.DeviceInfo
import com.itera.profileapp.NetworkMonitor
import com.itera.profileapp.NoteRepositoryInterface
import com.itera.profileapp.NoteViewModel
import com.itera.profileapp.PreferencesRepository
import com.itera.profileapp.ProfileViewModel
import com.itera.profileapp.SortPreferencesRepository
import com.itera.profileapp.data.local.DatabaseDriverFactory
import com.itera.profileapp.data.local.NotesDatabase
import com.itera.profileapp.data.repository.NoteRepository
import com.itera.profileapp.data.repository.UserPreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DeviceInfo() }
    single { NetworkMonitor(androidContext()) }
    single { BatteryInfo(androidContext()) }
    single { DatabaseDriverFactory(androidContext()).createDriver() }
    single { NotesDatabase(get()) }
    
    single<NoteRepositoryInterface> { NoteRepository(get()) }
    single { UserPreferencesRepository(androidContext()) }
    single<PreferencesRepository> { get<UserPreferencesRepository>() }
    single<SortPreferencesRepository> { get<UserPreferencesRepository>() }
    
    viewModel { ProfileViewModel(get()) }
    viewModel { NoteViewModel(get(), get()) }
}