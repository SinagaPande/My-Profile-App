# Notes App (Android)

Aplikasi pencatatan (Notes App) berbasis Android yang dibangun menggunakan **Jetpack Compose**. Proyek ini merupakan pengembangan dari tugas sebelumnya dengan penambahan sistem navigasi yang lengkap, parameter antarlayar, dan manajemen tema (Dark/Light Mode).

## 🗺️ Alur Navigasi (Navigation Flow)

Diagram di bawah ini menjelaskan bagaimana perpindahan layar (screen) di dalam aplikasi:

```mermaid
graph TD
    subgraph Bottom_Navigation
        Notes[Notes List Screen]
        Fav[Favorites Screen]
        Prof[Profile Screen]
    end

    Notes <--> Fav
    Fav <--> Prof
    Prof <--> Notes

    Notes -->|FAB Click| Add[Add Note Screen]
    Notes -->|Note Click| Detail[Note Detail Screen <br/> argument: noteId]
    
    Detail -->|Edit Click| Edit[Edit Note Screen <br/> argument: noteId]
    
    Add -->|Back| Notes
    Detail -->|Back| Notes
    Edit -->|Back| Detail
````

## 📸 Screenshots

Berikut adalah tampilan antarmuka aplikasi:

### Layar Utama (Bottom Navigation)

| Notes Screen | Favorites Screen | Profile Screen |
| :---: | :---: | :---: |
| ![Notes](Screenshoot/Screenshot%202026-04-14%20170408.png) | ![Favorites](Screenshoot/Screenshot%202026-04-14%20170436.png) | ![Profile](Screenshoot/Screenshot%202026-04-14%20170443.png) |

### Layar Form & Detail

| Add Note Screen | Note Detail Screen |
| :---: | :---: |
| ![Add Note](Screenshoot/Screenshot%202026-04-14%20170414.png) | ![Note Detail](Screenshoot/Screenshot%202026-04-14%20170426.png) |

## ✨ Fitur Utama

  * **Bottom Navigation**: Akses cepat ke tab Notes, Favorites, dan Profile.
  * **Navigation with Arguments**: Mengirimkan data `noteId` dari daftar catatan ke layar detail dan edit.
  * **Floating Action Button (FAB)**: Navigasi langsung ke layar tambah catatan.
  * **Dark Mode Support**: Tema aplikasi yang sinkron dengan pengaturan profil.
  * **Back Stack Management**: Sistem navigasi kembali (back button) yang terstruktur dan benar.

## 🛠️ Teknologi yang Digunakan

  * **Kotlin**: Bahasa pemrograman utama.
  * **Jetpack Compose**: Untuk membangun UI secara deklaratif.
  * **Navigation Compose**: Library untuk menangani perpindahan antar layar.
  * **ViewModel & StateFlow**: Untuk manajemen state dan logika bisnis.
  * **Material Design 3**: Komponen UI standar Google terbaru.

