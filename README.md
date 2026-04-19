# 📝 Notes App (Offline-First)

Aplikasi pencatatan (Notes App) modern berbasis Android yang dibangun sepenuhnya menggunakan **Jetpack Compose**. Aplikasi ini dirancang dengan prinsip **Offline-First**, memastikan pengguna dapat membuat, membaca, memperbarui, dan menghapus catatan tanpa memerlukan koneksi internet, serta menyimpan preferensi pengguna secara lokal.

👤 **Pengembang:** Jonathan Sinaga (123140153)

---

## 🎥 Video Demo
Klik tautan di bawah ini untuk melihat demonstrasi singkat (45 detik) dari fitur utama aplikasi (CRUD, Search, Sort, dan Dark Mode) dalam keadaan *offline*:

👉 **[Tonton Video Demo Aplikasi](https://drive.google.com/file/d/1dcqfOz8UsCtcZmQPUWGpEzRgRT69UI37/view?usp=sharing)**

---

## ✨ Fitur Utama (Rubrik Penilaian)

* ✅ **SQLDelight Database (20%):** Skema database lokal yang aman dan *type-safe* untuk menyimpan data catatan.
* ✅ **CRUD Operations (25%):** Fungsionalitas penuh untuk Menambah, Membaca, Mengubah, dan Menghapus catatan.
* ✅ **DataStore Settings (15%):** Penyimpanan preferensi pengguna secara persisten menggunakan `Preferences DataStore` (Tema Gelap/Terang & Urutan Catatan).
* ✅ **Search Feature (15%):** Pencarian catatan secara *real-time* menggunakan query SQL `LIKE` langsung dari database.
* ✅ **UI/UX & Sort (15%):** Antarmuka yang bersih menggunakan Material Design 3, *State Management* (Loading, Empty, Success), serta kemampuan mengurutkan catatan (Terbaru/Terlama).
* ✅ **Code Quality (10%):** Arsitektur rapi menggunakan konsep MVVM (Model-View-ViewModel), *Clean Architecture* sederhana, dan *Kotlin Flow/StateFlow* untuk *reactive programming*.

---

## 🛠️ Teknologi & Arsitektur

* **UI Toolkit:** Jetpack Compose (Material 3)
* **Arsitektur:** MVVM (Model-View-ViewModel)
* **Database:** SQLDelight (SQLite lokal)
* **Preferences:** Jetpack DataStore Preferences
* **Asynchronous:** Kotlin Coroutines & Flow
* **Navigasi:** Jetpack Navigation Compose

---

## 📸 Tangkapan Layar (Screenshots)

*(Pastikan folder `Screeshoot` berada di root repository ini agar gambar dapat dimuat)*

| Keadaan Kosong (Empty State) | Daftar Catatan (Notes List) | Detail Catatan & Hapus |
|:---:|:---:|:---:|
| <img src="Screeshoot/Screenshot%202026-04-20%20000808.png" width="250"> | <img src="Screeshoot/Screenshot%202026-04-20%20000903.png" width="250"> | <img src="Screeshoot/Screenshot%202026-04-20%20000933.png" width="250"> |

| Tambah Catatan (Create) | Edit Catatan (Update) | Pencarian & Filter (Search) |
|:---:|:---:|:---:|
| <img src="Screeshoot/Screenshot%202026-04-20%20000856.png" width="250"> | <img src="Screeshoot/Screenshot%202026-04-20%20000945.png" width="250"> | <img src="Screeshoot/Screenshot%202026-04-20%20001033.png" width="250"> |

---

## 📂 Struktur Proyek Utama

```text
app/src/main/
├── java/com/itera/profileapp/
│   ├── data/
│   │   ├── local/DatabaseDriverFactory.kt
│   │   └── repository/
│   │       ├── NoteRepository.kt (Operasi DB SQLDelight)
│   │       └── UserPreferencesRepository.kt (DataStore Preferences)
│   ├── navigation/AppNavigation.kt (Rute Layar)
│   ├── ui/screens/NoteScreens.kt (Komponen Layar Catatan)
│   ├── MainActivity.kt
│   ├── NoteViewModel.kt (Logika Bisnis Catatan)
│   └── ProfileViewModel.kt (Logika Bisnis Profil & Tema)
└── sqldelight/com/itera/profileapp/data/local/
    └── Note.sq (Skema & Query SQLDelight)