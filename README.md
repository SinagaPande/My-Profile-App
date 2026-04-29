# Profile App - Tugas Praktikum Minggu 8 📱

[cite_start]Aplikasi **Profile App** ini dikembangkan menggunakan **Kotlin Multiplatform (KMP)** dan **Compose Multiplatform** untuk mendemonstrasikan implementasi fitur spesifik platform menggunakan pola `expect/actual` dan manajemen dependensi dengan **Koin DI**[cite: 9, 502].

## 👤 Informasi Mahasiswa
* **Nama:** Jonathan Sinaga
* **NIM:** 123140153
* [cite_start]**Program Studi:** Teknik Informatika - ITERA [cite: 6, 7]

## [cite_start]🎯 Pemenuhan Tugas & Rubrik [cite: 514, 515]
* [cite_start]**Koin Dependency Injection (25%)**: Implementasi Koin untuk menyuntikkan *dependencies* seperti `NoteRepository`, `DeviceInfo`, dan `NetworkMonitor` secara otomatis[cite: 503, 508].
* [cite_start]**Expect/Actual Pattern (25%)**: Penggunaan pola `expect/actual` untuk mengakses API native pada Android dan iOS[cite: 504, 505].
* [cite_start]**UI Integration (20%)**: Menampilkan informasi perangkat di layar *Settings* dan indikator status jaringan *real-time* di layar utama[cite: 506, 507].
* **Architecture (20%)**: Struktur kode yang bersih dengan pemisahan antara `commonMain` untuk logika bisnis dan `androidMain` untuk implementasi platform.
* [cite_start]**Bonus (10%)**: Implementasi `BatteryInfo` menggunakan pola `expect/actual` untuk memantau status baterai perangkat[cite: 516, 517].

## [cite_start]🏗️ Diagram Arsitektur [cite: 511]
Aplikasi ini mengikuti arsitektur **MVVM (Model-View-ViewModel)** yang terintegrasi dengan KMP:
1. **UI Layer**: Menggunakan Compose Multiplatform.
2. **Logic Layer**: ViewModel di `commonMain`.
3. **Platform Layer**: Implementasi native (`androidMain`) melalui pola `expect/actual`.
4. **DI Layer**: Koin sebagai pengatur *lifecycle* objek.

## [cite_start]🖼️ Screenshots [cite: 511]

| Main Screen (Network Indicator) | Settings Screen (Device Info) |
| :---: | :---: |
| <img src="Screeshoot/Screenshot%202026-04-29%20225643.png" width="300"/> | <img src="Screeshoot/Screenshot%202026-04-29%20225705.png" width="300"/> |

## [cite_start]🎥 Video Demo [cite: 511]
Kamu dapat mengakses video demonstrasi aplikasi (menampilkan Koin DI, Device Info, dan Network Status) melalui tautan di bawah ini:

👉 **[Link Video Demo Praktikum Minggu 8](https://drive.google.com/file/d/1nERRGJz1Q7W_Fk3doyKGGrMA-UbCvBLA/view?usp=sharing)**

## 🛠️ Cara Menjalankan
1. [cite_start]Pastikan branch berada di `week-8`[cite: 510].
2. Buka proyek di Android Studio.
3. Jalankan `gradle sync`.
4. Run aplikasi di emulator atau perangkat Android.

