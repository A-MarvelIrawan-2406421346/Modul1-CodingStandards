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
