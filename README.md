# News Reader App 📰

Aplikasi Android sederhana untuk membaca berita terkini (Top Headlines), dibangun menggunakan **Jetpack Compose** dan arsitektur **MVVM (Model-View-ViewModel)**. Aplikasi ini mendemonstrasikan pengambilan data dari API publik, manajemen *state* UI yang reaktif, dan fitur *offline caching*.

## ✨ Fitur Utama

- **Fetch Berita Terkini:** Mengambil data artikel berita menggunakan NewsAPI.
- **List & Detail Screen:** Menampilkan daftar berita (gambar, judul, deskripsi) dan navigasi ke halaman detail artikel.
- **Pull to Refresh:** Pengguna dapat menarik layar ke bawah untuk memperbarui daftar berita.
- **Reactive UI States:** Menangani dan menampilkan antarmuka yang sesuai untuk kondisi `Loading`, `Success`, dan `Error`.
- **Offline Caching (Bonus):** Berita terakhir yang berhasil dimuat akan disimpan secara lokal. Jika pengguna membuka aplikasi tanpa koneksi internet, aplikasi akan menampilkan data dari *cache*.

## 🛠️ Teknologi yang Digunakan

- **Bahasa:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Arsitektur:** MVVM + Repository Pattern
- **Networking:** [Ktor Client](https://ktor.io/)
- **JSON Parsing:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- **Image Loading:** [Coil Compose](https://coil-kt.github.io/coil/compose/)
- **State Management:** `StateFlow` & `ViewModel`
- **Local Storage:** [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) (Untuk *Offline Caching*)

## 🎥 Video Demo

Lihat bagaimana aplikasi ini bekerja secara langsung:
[Klik di sini untuk menonton Video Demo Aplikasi](https://drive.google.com/file/d/1_j1HbaexvcrnwzF3DcwNI12bye3BNIZy/view?usp=sharing)

## 📸 Screenshot Layar

Berikut adalah tampilan antarmuka dari aplikasi News Reader:

| News List | News List (Scroll) | Detail Berita |
| :---: | :---: | :---: |
| <img src="Screenshoot/Screenshot 2026-04-20 154636.png" width="250"/> | <img src="Screenshoot/Screenshot 2026-04-20 154644.png" width="250"/> | <img src="Screenshoot/Screenshot 2026-04-20 154655.png" width="250"/> |

*(Catatan: Screenshot tambahan `Screenshot 2026-04-20 154705.png` tersedia di folder `/Screenshoot`)*

## 🚀 Cara Menjalankan Project

1. **Clone repositori ini:**
   ```bash
   git clone <link-repo-kamu>
   ```
2. **Buka project di Android Studio** (Disarankan menggunakan versi terbaru yang mendukung Kotlin DSL dan Compose).
3. **Dapatkan API Key:**
   - Kunjungi [NewsAPI.org](https://newsapi.org/) dan buat akun gratis untuk mendapatkan API Key.
4. **Konfigurasi API Key:**
   - Buka file `app/src/main/java/com/itera/newsreader/data/NewsApi.kt`.
   - Ganti teks `"YOUR_API_KEY"` dengan API Key milikmu.
5. **Jalankan Aplikasi:**
   - Tekan tombol **Run** (Shift + F10) pada emulator atau perangkat fisik Android.

## 📂 Struktur Folder Utama

```text
app/src/main/java/com/itera/newsreader/
├── data/           # Layer data (API, Model JSON, Local Cache dengan DataStore)
├── domain/         # Layer bisnis (Repository Interface & Implementasinya)
├── ui/
│   ├── screen/     # UI Jetpack Compose (List Screen & Detail Screen)
│   ├── theme/      # Konfigurasi Tema, Warna, dan Tipografi
│   └── viewmodel/  # ViewModel dan Sealed Class untuk UI State
└── MainActivity.kt # Entry point aplikasi
```
```