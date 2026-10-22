package com.mycompany.tugas.ke3;
import java.util.ArrayList;

public class Struk {
    
    // Method khusus untuk memisahkan urusan tampilan terminal agar file utama tetap rapi
    public void cetakKeTerminal(ArrayList<ProdukFisik> keranjang, double totalBelanja, Transaksi transaksi) {
        System.out.println("\n==================================");
        System.out.println("          STRUK BELANJA           ");
        System.out.println("==================================");
        
        if (keranjang.isEmpty()) {
            System.out.println("Tidak ada transaksi.");
        } else {
            for (ProdukFisik item : keranjang) {
                System.out.println(item.getNama() + " \tRp" + item.getHarga());
            }
            
            System.out.println("----------------------------------");
            System.out.println("Subtotal: \tRp" + totalBelanja);
            
            // Logika penerapan Polimorfisme Overloading
            if (totalBelanja > 10000) {
                System.out.println("Diskon: \t10%");
                // Memanggil method overloading dengan 2 parameter
                double totalAkhir = transaksi.hitungTotal(totalBelanja, 10);
                System.out.println("TOTAL: \t\tRp" + totalAkhir);
            } else {
                // Memanggil method overloading dengan 1 parameter
                double totalAkhir = transaksi.hitungTotal(totalBelanja);
                System.out.println("TOTAL: \t\tRp" + totalAkhir);
            }
        }
        System.out.println("==================================");
        System.out.println("          TERIMA KASIH            ");
    }
}