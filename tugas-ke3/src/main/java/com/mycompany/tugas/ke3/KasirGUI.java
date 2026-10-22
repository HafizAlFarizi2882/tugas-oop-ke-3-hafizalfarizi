package com.mycompany.tugas.ke3;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CLASS KasirGUI
 * Mengatur antarmuka visual (GUI) untuk sistem kasir.
 * 
 * 5. PEWARISAN (INHERITANCE): 
 * Class ini menggunakan keyword 'extends' untuk mewarisi sifat dari JFrame 
 * (class bawaan Java untuk membuat jendela aplikasi).
 */
public class KasirGUI extends JFrame {

    // ==========================================
    // 2. ATTRIBUTE & 4. ENKAPSULASI
    // ==========================================
    // Menggunakan access modifier 'private' agar komponen GUI dan data 
    // transaksi tidak bisa dimanipulasi dari luar class ini.
    private JTextField inputBarcode;
    private JTextArea areaStruk;
    private JButton btnTambah, btnBayar;
    
    // ArrayList berfungsi sebagai penyimpan data sementara (pengganti database)
    private ArrayList<ProdukFisik> databaseBarang;
    private ArrayList<ProdukFisik> keranjang;
    
    // Memanggil class Transaksi untuk logika perhitungan
    private Transaksi mesinHitung;
    private double totalBelanja = 0;

    // ==========================================
    // 3. CONSTRUCTOR
    // ==========================================
    /**
     * Constructor KasirGUI akan otomatis dijalankan saat jendela aplikasi dibuka.
     * Fungsinya untuk mengatur ukuran layar, tombol, dan data awal barang.
     */
    public KasirGUI() {
        // Keyword 'this' merujuk pada JFrame (jendela aplikasi ini)
        this.setTitle("Aplikasi Kasir Sederhana - OOP");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Program berhenti saat disilang
        this.setLayout(null); // Mematikan layout otomatis agar posisi bisa diatur manual dengan setBounds
        this.setLocationRelativeTo(null); // Jendela muncul tepat di tengah layar

        // 1. OBJECT: Membuat wujud nyata dari class pendukung
        mesinHitung = new Transaksi();
        databaseBarang = new ArrayList<>();
        keranjang = new ArrayList<>();
        
        // Memasukkan data awal ke dalam "database" barang
        databaseBarang.add(new ProdukFisik("1", "Indomie Goreng", 3500, 50));
        databaseBarang.add(new ProdukFisik("2", "Teh Pucuk", 4000, 30));
        databaseBarang.add(new ProdukFisik("3", "Roti Aoka", 2500, 20));
        databaseBarang.add(new ProdukFisik("4", "Roti sobek", 3000, 20));

        // --- PENGATURAN KOMPONEN VISUAL (LABEL, TEXTFIELD, BUTTON) ---
        
        JLabel labelJudul = new JLabel("Masukkan Barcode (1/2/3/4):");
        labelJudul.setBounds(20, 20, 250, 20); // Posisi: (x, y, lebar, tinggi)
        this.add(labelJudul);

        inputBarcode = new JTextField();
        inputBarcode.setBounds(20, 45, 150, 30);
        this.add(inputBarcode);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(180, 45, 90, 30);
        this.add(btnTambah);

        btnBayar = new JButton("Bayar");
        btnBayar.setBounds(280, 45, 80, 30);
        this.add(btnBayar);

        // Area teks putih besar untuk menampilkan struk belanja
        areaStruk = new JTextArea();
        areaStruk.setEditable(false); // Mematikan fitur ketik agar area struk tidak bisa diedit user
        areaStruk.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        
        // JScrollPane digunakan agar area struk bisa di-scroll ke bawah jika isinya panjang
        JScrollPane scrollPane = new JScrollPane(areaStruk);
        scrollPane.setBounds(20, 90, 340, 340);
        this.add(scrollPane);

        // Teks awalan pada struk
        areaStruk.setText("=== SELAMAT DATANG ===\nSilakan scan barang...\n");

        // --- PENGATURAN EVENT LISTENER (FUNGSI SAAT TOMBOL DIKLIK) ---

        // Fungsi ketika tombol "Tambah" diklik
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahBarang(); // Memanggil method tambahBarang()
            }
        });

        // Fungsi ketika tombol "Bayar" diklik
        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesPembayaran(); // Memanggil method prosesPembayaran()
            }
        });
    }

    // ==========================================
    // 2. METHOD LOGIKA APLIKASI
    // ==========================================
    
    /**
     * Method ini bertugas mengecek inputan barcode, 
     * mencari barang di database, dan memotong stok.
     */
    private void tambahBarang() {
        String scanCode = inputBarcode.getText(); // Mengambil teks dari kolom input
        boolean ditemukan = false;

        // Mencari barang yang barcodenya sama dengan yang diketik user
        for (ProdukFisik barang : databaseBarang) {
            if (barang.getKodeBarcode().equals(scanCode)) {
                ditemukan = true;
                
                // Cek apakah stok masih ada
                if (barang.getStok() > 0) {
                    keranjang.add(barang); // Masukkan ke keranjang
                    barang.kurangiStok(1); // Kurangi stok (Enkapsulasi terapan)
                    totalBelanja += barang.getHarga();
                    
                    // Cetak ke layar GUI
                    areaStruk.append("\n+ " + barang.getNama() + " \tRp" + barang.getHarga());
                } else {
                    // Menampilkan pop-up error jika stok habis
                    JOptionPane.showMessageDialog(this, "Stok " + barang.getNama() + " habis!");
                }
                break; // Berhenti mencari jika barang sudah ketemu
            }
        }

        if (!ditemukan) {
            // Menampilkan pop-up error jika barcode salah
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!");
        }
        
        // Membersihkan kolom teks otomatis dan mengembalikan kursor ke situ
        inputBarcode.setText(""); 
        inputBarcode.requestFocus(); 
    }

    /**
     * Method ini bertugas menghitung total akhir dan menerapkan diskon.
     */
    private void prosesPembayaran() {
        // Cek jika user klik bayar tapi belum scan barang apapun
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong!");
            return; // Hentikan proses
        }

        areaStruk.append("\n\n==================================");
        areaStruk.append("\nSubtotal: \tRp" + totalBelanja);

        // 6. POLIMORFISME (OVERLOADING)
        // Logika: Jika total belanja > 10.000, panggil method hitungTotal yang ada diskonnya
        if (totalBelanja > 10000) {
            areaStruk.append("\nDiskon: \t10%");
            // Memanggil method dengan 2 parameter: hitungTotal(uang, diskon)
            double totalAkhir = mesinHitung.hitungTotal(totalBelanja, 10);
            areaStruk.append("\nTOTAL: \t\tRp" + totalAkhir);
        } else {
            // Memanggil method dengan 1 parameter: hitungTotal(uang)
            double totalAkhir = mesinHitung.hitungTotal(totalBelanja);
            areaStruk.append("\nTOTAL: \t\tRp" + totalAkhir);
        }
        
        areaStruk.append("\n==================================");
        areaStruk.append("\n          TERIMA KASIH            ");
        
        // Mematikan tombol agar transaksi tidak bisa ditambah lagi setelah bayar
        btnTambah.setEnabled(false);
        btnBayar.setEnabled(false);
    }

    // ==========================================
    // MAIN METHOD (PINTU MASUK PROGRAM)
    // ==========================================
    public static void main(String[] args) {
        // 1. OBJECT: Membuat wujud nyata dari jendela aplikasi
        KasirGUI aplikasiKasir = new KasirGUI();
        
        // Menampilkan jendela ke layar laptop
        aplikasiKasir.setVisible(true);
    }
}