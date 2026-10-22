package com.mycompany.tugas.ke3;

public class Transaksi {
    
    // 6. POLIMORFISME (OVERLOADING): Method pertama (hanya 1 parameter) untuk menghitung total harga normal
    public double hitungTotal(double totalBelanja) {
        return totalBelanja;
    }

    // 6. POLIMORFISME (OVERLOADING): Method kedua (nama persis sama, tapi 2 parameter) untuk menghitung dengan potongan diskon
    public double hitungTotal(double totalBelanja, double diskonPersen) {
        double potongan = totalBelanja * (diskonPersen / 100);
        return totalBelanja - potongan;
    }
}