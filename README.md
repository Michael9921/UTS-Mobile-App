# UTS-Mobile-App

UMN Life 
merupakan aplikasi simulasi permainan kehidupan yang dapat dimainkan pada perangkat mobile, dan aplikasi ini berbasis offline.
Pemain pada game ini harus bertahan hidup hingga menyelesaikan perkuliahan hingga semester 8.

Nama Kelompok : Gas aj dulu
Nama anggota:
1. 00000042678 - Michael Christian Dharmawan
2. 00000044537- Kevin Fernando Wijaya Sumargo
3. 00000044665 - Farren Yazid Pasha
4. 000000055136 - Koong Hiap

FITUR TAMBAHAN
- Quiz
- Save Data
- Alarm
- Background activity
- Lagu
- Selengkapnya dari fitur ada diketerangan dibawah




![Screenshot 2023-04-02 160201](https://user-images.githubusercontent.com/98584656/229343169-9b403976-0445-4402-8c9f-38e751bd1479.png)
Peraturan: 
- Pada awal permainan pemain harus memilih salah satu karakter yang disediakan, dan harus mengisi nama pemain.
- Pemain dapat melakukan 4 aktifitas, yaitu: bermain, makan, tidur, dan belajar.
- Pada awal permainan status senang, makan, dan tidur pemain akan dimulai dari 50%. Sedangkan, status belajar akan dimulai pada 0%
- Untuk menang pemain harus belajar sampai menyelesaikan semester 8.
- Jika pemain tidak melakukan aktifitas maka status akan terus berkurang dan pemain akan mati
![image](https://user-images.githubusercontent.com/98584656/229343614-66a1e5ac-4143-4496-8040-618e863d4cc6.png)




Status: 
- Pada awal permainan status senang, makan, dan tidur pemain akan dimulai dari 50%. Sedangkan, status belajar akan dimulai pada 0%
- Ketika idle status pemain akan berkurang
- Ketika pemain melakukan aktifitas main, maka akan mengurangi status makan karena untuk main kita butuh tenaga
- Ketika pemain melakukan aktifitas makan, maka status senang akan naik karena karakter senang makan.
- Ketika pemain melakukan aktifitas belajar, maka status senang dan makan akan berkurang karena belajar membutuhkan tenaga, dan membuat pemain capek.
![Screenshot 2023-04-02 160234](https://user-images.githubusercontent.com/98584656/229343160-5940f3f6-575e-4f27-9da5-47bd97891b31.png)

- Ketika status berkurang sampai 20% maka akan ditampilkan alert yang menyatakan kondisi pemain
- Jika pemain tidak melakukan belajar selama 30 detik maka akan ditampilkan alert bahwa kalau tidak belajar maka akan di DO
- Jika pemain tidak belajar sampai 60 detik maka pemain akan di DO dan pemain akan kalah
![Screenshot 2023-04-02 161431](https://user-images.githubusercontent.com/98584656/229343930-06bcd9cf-bc0a-499f-9a8e-41c8fb1398dc.png)
![image](https://user-images.githubusercontent.com/98584656/229344343-67d9447b-94fb-49d3-b8b8-53583e4a062e.png)





Belajar:
- ketika pemain telah belajar sampai bar status belajar penuh maka akan diberikan quiz untuk dapat naik pada semester berikutnya
- jika jawaban quiz salah maka status belajar akan kembali 0 dan semester tidak akan bertambah
![image](https://user-images.githubusercontent.com/98584656/229343577-1a1de985-8ca6-42d3-921c-3b1c25966388.png)





Tidur:
- jika pemain ingin tidur maka akan diberikan popup seperti gambar dibawah
- jika popup muncul status dan jam akan diam, jika popup tidak diisi jam nya maka akan dibatalkan perintah tidur
![image](https://user-images.githubusercontent.com/98584656/229343853-eeec42e4-fce8-452f-b1e7-d9c2311f701a.png)






Save Data:
- Ketika pemain keluar dari game maka game akan secara otomatis save semua data yang telah dimainkan
- Jika pemain ingin menghapus data permainan yang telah dimainkan maka pemain dapat melakukannya dengan mati atau masuk ke activity info
![Screenshot 2023-04-02 161544](https://user-images.githubusercontent.com/98584656/229344636-0b071122-f4ab-4729-b706-fc3a66e16d28.png)






Background dan lagu:
- Background akan berubah jika jam telah menunjukkan jam tertentu, selengkapnya seperti berikut:

- Pagi: 4-10
- Siang: 10-14
- Sore: 14-16
- Malam: 16-4

- Selain dari jam background akan berganti terhadap kegiatan yang ada seperti ketika main, belajar, tidur, dan makan. 
- Tetapi background yang menyesuaikan aktifitas hanya akan bergati 1 jam sekali atau berganti page activity dan kembali kedalam game.
- Background aktifitas juga akan bergantung pada jam jadi total pada game ini akan ada 16 background.
- lagu akan berputas mengikuti waktu yang ada diatas





