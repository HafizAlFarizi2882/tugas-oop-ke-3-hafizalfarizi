package com.mycompany.tugas.ke3;

// 5. PEWARISAN (INHERITANCE): Child Class (ProdukFisik) mewarisi sifat Parent Class (Produk) menggunakan 'extends'
public class ProdukFisik extends Produk {
    
    // Attribute spesifik yang hanya dimiliki oleh ProdukFisik
    private int stok;

    public ProdukFisik(String kodeBarcode, String nama, double harga, int stok) {
        // Memanggil constructor dari Parent Class
        super(kodeBarcode, nama, harga); 
        this.stok = stok;
    }
    
    public int getStok() { return this.stok; }

    // Method untuk mengubah stok secara aman dari luar class (bagian dari prinsip Enkapsulasi)
    public void kurangiStok(int jumlah) {
        this.stok -= jumlah;
    }

    // 6. POLIMORFISME (OVERRIDING): Menulis ulang method tampilkanInfo() milik Parent Class agar ikut mencetak jumlah stok
    @Override
    public void tampilkanInfo() {
        System.out.println(getNama() + " (Stok: " + this.stok + ") - Rp" + getHarga());
    }
}