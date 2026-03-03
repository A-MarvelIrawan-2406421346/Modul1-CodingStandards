# Modul1-CodingStandards
# Reflection 1

Saya sudah membuat github repo baru untuk modul 1 dan membuat branch baru bernama list-product. 
Terdapat beberapa fitur baru yang dibuat seperti edit dan delete product. 
Tahapan pertama adalah membuat model Product yang memiliki atribut id, name, dan quantity. 
Kemudian, membuat Repository bernama ProductRepository untuk me-manage operasi data dari produk.
Tahapan berikutnya adalah membuat Service untuk meng-implementasikan logic-nya.
Setelah itu, saya beralih ke bagian Controller dengan membuat file ProductController.java di dalam folder controller. 
Controller ini berfungsi sebagai jembatan yang menerima permintaan (request) dari klien, lalu memprosesnya melalui layer Service dan mengembalikannya ke tampilan (Templates).

Setelah logika bisnis selesai dibuat, saya menyiapkan tampilan antarmuka (front-end) menggunakan HTML agar hasilnya bisa dilihat oleh pengguna. 
Saya membuat dua halaman utama di direktori templates, yaitu CreateProduct.html untuk form pembuatan produk dan ProductList.html untuk menampilkan daftar produk yang tersimpan.

Setelah memastikan fitur list product berjalan dengan baik, saya menggabungkan (merge) branch list-product ke main. 
Proses pengembangan kemudian dilanjutkan dengan membuat branch baru, yaitu edit-product dan delete-product, untuk mengimplementasikan fitur pengeditan dan penghapusan produk. Terakhir, saya melakukan commit dan push perubahan tersebut ke repository.

# Reflection 2

Setelah menyelesaikan fitur dasar, saya fokus menjamin kualitas kode dengan membuat Unit Test dan Functional Test. Saya membuat branch unit-test untuk menguji Model dan Repository (mencakup skenario positif dan negatif), lalu lanjut ke branch functional-test untuk mensimulasikan interaksi pengguna pada halaman Home dan Create Product menggunakan Selenium.

Menulis Unit Test memberikan rasa percaya diri (confidence) saat melakukan perubahan kode. Menurut saya, jumlah tes dalam satu class tidak memiliki angka baku, yang penting cukup untuk memverifikasi semua kemungkinan perilaku dan edge cases. Terkait Code Coverage, memiliki 100% coverage tidak menjamin kode bebas bug. Angka tersebut hanya berarti seluruh baris kode telah dieksekusi, namun tidak menutup kemungkinan adanya kesalahan logika bisnis atau error pada skenario yang belum terpikirkan.

Mengenai Functional Test, jika saya membuat class baru dengan prosedur setup dan variabel yang sama persis (copy-paste) dari tes sebelumnya, hal itu akan menurunkan kualitas kode. Isu utamanya adalah Code Duplication yang melanggar prinsip DRY (Don't Repeat Yourself), membuat kode sulit dipelihara (maintain). Solusi perbaikannya adalah menerapkan Inheritance atau membuat Base Test Class untuk menangani konfigurasi umum (setup WebDriver & Port), sehingga kelas tes lain cukup melakukan extends tanpa perlu menulis ulang kode yang sama.

---

# Modul2 - CI/CD & DevOps
## 1. Code Quality Issues & Fixing Strategy

Berdasarkan analisis statis menggunakan PMD, saya menemukan dan memperbaiki dua isu kualitas kode berikut:

* **Isu 1: Pemanggilan `String.toLowerCase()` tanpa menyertakan `Locale`.**
* **Lokasi:** `CreateProductFunctionalTest.java` (Baris 118).
* **Strategi Perbaikan:** Mengandalkan *default locale* dari sistem operasi dapat menyebabkan hasil yang tidak konsisten saat dijalankan di komputer atau server yang berbeda (misalnya masalah konversi huruf 'I' pada bahasa Turki). Strategi saya adalah secara eksplisit menambahkan parameter `Locale`, seperti mengubahnya menjadi `.toLowerCase(Locale.ROOT)` atau `.toLowerCase(Locale.ENGLISH)`. Ini memastikan proses *string manipulation* selalu konsisten terlepas dari di mana kode (*runner* CI/CD) dijalankan.


* **Isu 2: Penggunaan `setAccessible()` untuk memodifikasi visibilitas *constructor/method/field*.**
* **Lokasi:** `ProductServiceImplTest.java` (Baris 24).
* **Strategi Perbaikan:** Menggunakan fitur *Reflection* Java seperti `setAccessible(true)` untuk mengakses *private member* adalah praktik yang buruk karena merusak prinsip Enkapsulasi dan membuat tes menjadi sangat rapuh (mudah rusak jika struktur internal kelas berubah). Strategi perbaikannya adalah menghindari pengujian *private method/field* secara langsung. Saya beralih menguji *behavior* melalui *public methods*-nya. Jika pemanggilan ini sebelumnya digunakan untuk *mocking*, saya memperbaikinya menggunakan mekanisme standar yang lebih aman, seperti *constructor injection* atau menggunakan anotasi `@InjectMocks` dari Mockito.


## 2. Evaluasi CI/CD Workflow

Ya, saya yakin implementasi saat ini telah memenuhi definisi dari *Continuous Integration* (CI) dan *Continuous Deployment* (CD).

Dari sisi *Continuous Integration*, setiap kali ada kode yang di-*push* atau digabungkan (*merge*) ke dalam repositori, *workflow* akan secara otomatis menjalankan proses *build*, menjalankan rangkaian *automated tests*, dan mengeksekusi analisis kualitas kode (PMD/JaCoCo). Hal ini memastikan bahwa setiap perubahan kode diintegrasikan dengan mulus dan mendeteksi *error* sedini mungkin sebelum mencapai tahap produksi. Dari sisi *Continuous Deployment*, *pipeline* telah dikonfigurasi untuk langsung me-*deploy* aplikasi ke PaaS (seperti Zeabur) secara otomatis segera setelah tahap integrasi dan *testing* berhasil dilewati. Rangkaian ini menghilangkan kebutuhan *deployment* manual dan memastikan bahwa versi perangkat lunak yang paling mutakhir dan stabil selalu tersedia bagi pengguna akhir secara *real-time*.

---
# Modul 3 - SOLID Principle
## 1. Explain what principles you apply to your project!

Dalam proyek ini, saya telah menerapkan kelima prinsip **SOLID** untuk membersihkan dan merestrukturisasi kode, khususnya pada modul `Car`. Berikut adalah detail penerapannya:

* **Single Responsibility Principle (SRP):** Saya memisahkan `CarController` agar tidak lagi melakukan *extends* terhadap `ProductController`. Kini, `CarController` hanya memiliki satu tanggung jawab, yaitu menangani *request* HTTP yang berkaitan dengan `Car`, terpisah dari logika `Product`.
* **Open-Closed Principle (OCP):** Saya memperkenalkan *Interface* untuk `CarService` dan `CarRepository`. Dengan demikian, modul ini "terbuka untuk ekstensi" (misalnya menambahkan `DatabaseCarRepository` di masa depan) namun "tertutup untuk modifikasi" (tidak perlu mengubah isi `CarServiceImpl` jika ada perubahan tipe penyimpanan).
* **Liskov Substitution Principle (LSP):** Saya menghilangkan hubungan *inheritance* (`extends ProductController`) pada `CarController`. Sebelumnya, struktur ini melanggar LSP karena `CarController` memiliki URL routing (`/car`) dan model (`Car`) yang berbeda, sehingga tidak bisa menggantikan `ProductController` secara utuh tanpa merusak alur program.
* **Interface Segregation Principle (ISP):** Dengan memisahkan `CarController` dari `ProductController`, `CarController` tidak lagi dipaksa untuk mewarisi dan bergantung pada metode-metode milik `ProductController` yang tidak ia butuhkan (seperti `createProductPage`).
* **Dependency Inversion Principle (DIP):** Saya mengubah anotasi `@Autowired` pada `CarController` dan `CarServiceImpl` agar bergantung pada **Abstraksi (Interface)**, bukan pada **Implementasi (Kelas konkret)**. Contohnya, `CarController` sekarang memanggil `CarService` (Interface), bukan `CarServiceImpl`.


## 2. Explain the advantages of applying SOLID principles to your project with examples.

Menerapkan prinsip SOLID memberikan banyak keuntungan, terutama dalam hal skalabilitas, pemeliharaan (maintenance), dan kemudahan pengujian (testing):

* **Skalabilitas & Fleksibilitas Tinggi (OCP & DIP):**
* *Contoh:* Saat ini aplikasi menyimpan data mobil di dalam *memory* (`ArrayList`). Jika besok saya diminta untuk mengubah penyimpanannya menggunakan database PostgreSQL, saya hanya perlu membuat kelas baru (misal: `PostgresCarRepository implements CarRepository`). Saya tidak perlu menyentuh atau mengubah satu baris kode pun di dalam `CarServiceImpl`. Sistem otomatis beradaptasi tanpa risiko merusak fitur yang sudah berjalan.


* **Keamanan Modifikasi (SRP):**
* *Contoh:* Karena `CarController` dan `ProductController` sekarang berdiri sendiri, jika ada anggota tim lain yang merombak ulang tampilan dan logika rute `/product`, saya tidak perlu khawatir rute `/car` saya akan ikut *error* atau berubah perilakunya secara tiba-tiba.


* **Mudah Diuji (Unit Testing):**
* *Contoh:* Karena `CarController` bergantung pada *Interface* `CarService`, saya bisa dengan sangat mudah membuat *Mocking* (objek tiruan) dari `CarService` menggunakan framework seperti Mockito untuk menguji *Controller* secara terisolasi.


## 3. Explain the disadvantages of not applying SOLID principles to your project with examples.

Jika prinsip SOLID diabaikan, kode akan menjadi rentan (*fragile*), kaku (*rigid*), dan sulit dipahami (*spaghetti code*). Berikut adalah kerugiannya berdasarkan kode sebelum di-*refactor*:

* **Sistem Rentan Terhadap *Bug* Berantai (Melanggar SRP & LSP):**
* *Contoh:* Pada kode awal, `CarController` mewarisi `ProductController`. Jika ada perubahan pada struktur atau parameter di super-class (`ProductController`), hal itu bisa secara tidak sengaja mematahkan fungsi di `CarController`. Selain itu, jika sistem mengekspektasikan pengolahan *Product* tapi yang lewat adalah objek *Car*, program akan mengalami *runtime error* karena ketidakcocokan tipe model.


* **Kode Kaku dan Sulit Dikembangkan (Melanggar OCP & DIP):**
* *Contoh:* Pada kode awal, `CarServiceImpl` menggunakan `@Autowired` langsung ke kelas konkret `CarRepository`. Jika kita ingin mengganti metode penyimpanan ke database (SQL), kita terpaksa harus memodifikasi langsung isi dari `CarServiceImpl`. Ini sangat berisiko memunculkan *bug* baru pada logika bisnis yang sebelumnya sudah berjalan normal.


* **Ketergantungan yang Tidak Masuk Akal (Melanggar ISP):**
* *Contoh:* Jika kita terus memaksa `CarController extends ProductController`, `CarController` akan secara otomatis memiliki *endpoint* atau metode terkait *Product* yang tidak relevan. Ini membuat API menjadi berantakan, membingungkan *developer* lain, dan berpotensi menimbulkan celah keamanan.


---

