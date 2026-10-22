package com.mycompany.tugas.ke3;

// 1. CLASS: Sebagai cetak biru dasar dari sebuah entitas (Produk)
public class Produk {
    
    // 4. ENKAPSULASI: Melindungi data menggunakan access modifier 'private'
    // 2. ATTRIBUTE: Variabel untuk menyimpan karakteristik data
    private String kodeBarcode;
    private String nama;
    private double harga;

    // 3. CONSTRUCTOR: Method khusus untuk memberi nilai awal saat objek diciptakan
    public Produk(String kodeBarcode, String nama, double harga) {
        // 3. KEYWORD 'this': Merujuk pada attribute milik class ini untuk membedakannya dari parameter
        this.kodeBarcode = kodeBarcode;
        this.nama = nama;
        this.harga = harga;
    }

    // 4. ENKAPSULASI: Method 'Getter' untuk mengizinkan akses baca pada data yang private
    public String getKodeBarcode() { return this.kodeBarcode; }
    public String getNama() { return this.nama; }
    public double getHarga() { return this.harga; }

    // 2. METHOD: Mendefinisikan fungsi perilaku dari class
    public void tampilkanInfo() {
        System.out.println(this.nama + " - Rp" + this.harga);
    }
}