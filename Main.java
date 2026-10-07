import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final String FILE_MENU = "menu.txt";

    // Struct / Model Data Menu untuk mempermudah penanganan data
    static class ItemMenu {
        String nama;
        float hargaT;
        float hargaM;
        int stok;

        public ItemMenu(String nama, float hargaT, float hargaM, int stok) {
            this.nama = nama;
            this.hargaT = hargaT;
            this.hargaM = hargaM;
            this.stok = stok;
        }
    }

    private static List<ItemMenu> daftarMenu = new ArrayList<>();

    // ---------------------- OPERASI FILE TXT ---------------------- //

    // Membaca data dari tabel di file menu.txt
    private static void muatDataMenu() {
        File file = new File(FILE_MENU);
        if (!file.exists()) {
            buatFileMenuAwal();
        }
        daftarMenu.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_MENU))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Hanya membaca baris tabel yang berisi data (memiliki tanda '|') dan bukan header
                if (line.startsWith("|") && !line.contains("NAMA MINUMAN")) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        String nama = parts[2].trim();
                        float hT = Float.parseFloat(parts[3].trim());
                        float hM = Float.parseFloat(parts[4].trim());
                        int stok = Integer.parseInt(parts[5].trim());
                        daftarMenu.add(new ItemMenu(nama, hT, hM, stok));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("!! Gagal membaca file menu: " + e.getMessage());
        }
    }

    // Membuat file menu.txt berupa tabel rapi jika file belum ada
    private static void buatFileMenuAwal() {
        String[] menuNames = {
            "Darmi Plain", "Darmi Moka", "Darmi Anggur", "Darmi Cokelat", "Darmi Vanilla", "Darmi Taro", "Darmi Greentea",
            "Darmi ChocoOat", "Darmi Regal", "Darmi Cheese", "Darmi Cookies", "Darmi Cadbury",
            "Darmi Melon", "Darmi Pisang", "Darmi Strawberry", "Darmi Lychee", "Darmi Mangga", "Darmi Almond"
        };
        int[] hargaT = {14000, 15000, 15000, 17000, 17000, 19000, 19000, 16000, 17000, 17000, 17000, 22000, 17000, 17000, 19000, 21000, 21000, 22000};
        int[] hargaM = {17000, 18000, 18000, 20000, 20000, 22000, 22000, 19000, 20000, 20000, 20000, 25000, 20000, 20000, 22000, 24000, 24000, 25000};

        daftarMenu.clear();
        for (int i = 0; i < menuNames.length; i++) {
            daftarMenu.add(new ItemMenu(menuNames[i], hargaT[i], hargaM[i], 50));
        }
        simpanDataMenu();
    }

    // Menyimpan daftar menu ke file menu.txt dalam bentuk tabel rapi
    private static void simpanDataMenu() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_MENU))) {
            String border = "+----+----------------------+------------------+---------------------+--------+";
            pw.println(border);
            pw.println(String.format("| %-2s | %-20s | %-16s | %-19s | %-6s |", "NO", "NAMA MINUMAN", "HARGA TINY (T)", "HARGA MONSTER (M)", "STOK"));
            pw.println(border);

            for (int i = 0; i < daftarMenu.size(); i++) {
                ItemMenu item = daftarMenu.get(i);
                pw.println(String.format("| %-2d | %-20s | %-16.0f | %-19.0f | %-6d |",
                        (i + 1), item.nama, item.hargaT, item.hargaM, item.stok));
            }
            pw.println(border);
        } catch (IOException e) {
            System.out.println("!! Gagal menyimpan file menu: " + e.getMessage());
        }
    }

    // ---------------------- HELPER INPUT ---------------------- //

    private static int inputAngka(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("!! Input harus berupa angka !!");
            }
        }
    }

    private static String pilihLevel(Scanner scanner, String jenis) {
        while (true) {
            System.out.println("Pilih Level " + jenis + " :");
            System.out.println("1. Extra    2. Normal    3. Less    4. No");
            int pil = inputAngka(scanner, "Masukkan pilihan (1-4): ");

            switch (pil) {
                case 1: return "Extra";
                case 2: return "Normal";
                case 3: return "Less";
                case 4: return "No";
                default: System.out.println("!! Pilihan tidak valid (harus 1-4) !!\n");
            }
        }
    }

    // ---------------------- MAIN METHOD ---------------------- //

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        muatDataMenu(); // Mengisikan data menu dari file menu.txt saat aplikasi dibuka

        int noUrutGlobal = 1;
        boolean aplikasiBerjalan = true;

        while (aplikasiBerjalan) {
            System.out.println("\n==================================================");
            System.out.println("           PROGRAM KASIR MBOK DARMI");
            System.out.println("==================================================");
            System.out.println("1. Lihat Menu & Stok Minuman");
            System.out.println("2. Tambah Stok Minuman");
            System.out.println("3. Cetak Struk / Buat Transaksi");
            System.out.println("4. Keluar");
            System.out.println("==================================================");

            int pilihanUtama = inputAngka(input, "Pilih menu (1-4): ");

            switch (pilihanUtama) {
                case 1:
                    tampilkanMenuDanStok();
                    break;

                case 2:
                    tambahStokMinuman(input);
                    break;

                case 3:
                    prosesTransaksi(input, noUrutGlobal);
                    noUrutGlobal++;
                    break;

                case 4:
                    aplikasiBerjalan = false;
                    System.out.println("\n[SYSTEM] Terima kasih! Program kasir berhenti.");
                    break;

                default:
                    System.out.println("!! Pilihan tidak valid (harus 1-4) !!");
                    break;
            }
        }

        input.close();
    }

    // ---------------------- FITUR 1: LIHAT MENU & STOK ---------------------- //

    private static void tampilkanMenuDanStok() {
        System.out.println("\n======================================================================");
        System.out.println("                      DAFTAR MENU & STOK TERSEDIA");
        System.out.println("======================================================================");
        System.out.printf("%-4s %-20s %-15s %-15s %-8s\n", "NO", "NAMA MINUMAN", "HARGA (T)", "HARGA (M)", "STOK");
        System.out.println("----------------------------------------------------------------------");
        for (int i = 0; i < daftarMenu.size(); i++) {
            ItemMenu item = daftarMenu.get(i);
            System.out.printf("%2d.  %-20s Rp %,9.0f    Rp %,9.0f    %-8d pcs\n",
                    (i + 1), item.nama, item.hargaT, item.hargaM, item.stok);
        }
        System.out.println("======================================================================");
    }

    // ---------------------- FITUR 2: TAMBAH STOK ---------------------- //

    private static void tambahStokMinuman(Scanner input) {
        tampilkanMenuDanStok();
        System.out.println("\n--- TAMBAH STOK MINUMAN ---");
        int pil = inputAngka(input, "Pilih Nomor Menu yang ingin ditambah stoknya (1-" + daftarMenu.size() + "): ");

        if (pil >= 1 && pil <= daftarMenu.size()) {
            ItemMenu item = daftarMenu.get(pil - 1);
            int tambahan = inputAngka(input, "Masukkan jumlah stok tambahan untuk " + item.nama + ": ");

            if (tambahan > 0) {
                item.stok += tambahan;
                simpanDataMenu(); // Memperbarui file menu.txt dengan format tabel
                System.out.println("\n>> [BERHASIL] Stok " + item.nama + " bertambah! Stok saat ini: " + item.stok + " pcs");
            } else {
                System.out.println("!! Jumlah stok tambahan harus lebih besar dari 0 !!");
            }
        } else {
            System.out.println("!! Nomor menu tidak ditemukan !!");
        }
    }

    // ---------------------- FITUR 3: TRANSAKSI & CETAK STRUK ---------------------- //

    private static void prosesTransaksi(Scanner input, int noUrutGlobal) {
        Penjualan transaksi = new Penjualan();
        transaksi.setNamaToko("Susu Mbok Darmi");
        transaksi.setAlamatToko("Kantin Universitas Tarumanegara");

        LocalDateTime now = LocalDateTime.now();
        String tglLengkap = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String tglKode = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        transaksi.setWaktu(tglLengkap);

        transaksi.setNoUrut(noUrutGlobal);
        String noNotaOtomatis = "MD-" + tglKode + "-" + String.format("%03d", noUrutGlobal);
        transaksi.setNomorNota(noNotaOtomatis);

        System.out.println("\n==================================================");
        System.out.println("                TRANSAKSI BARU");
        System.out.println("==================================================");
        System.out.println("No Nota         : " + noNotaOtomatis);
        System.out.println("No Urut        : " + noUrutGlobal);

        String jenisOrder = "";
        while (jenisOrder.isEmpty()) {
            System.out.println("\nJenis Order:");
            System.out.println("1. Take Away (TA)");
            System.out.println("2. Dine In (DI)");
            int pilJo = inputAngka(input, "Pilih (1/2): ");
            if (pilJo == 1) jenisOrder = "Take Away (TA)";
            else if (pilJo == 2) jenisOrder = "Dine In (DI)";
            else System.out.println("!! Pilihan salah !!\n");
        }
        transaksi.setJenisOrder(jenisOrder);

        boolean tambahPesanan = true;
        while (tambahPesanan) {
            tampilkanMenuDanStok();

            int pilihan = inputAngka(input, "\nPilih Nomor Menu (1-" + daftarMenu.size() + ") : ");

            if (pilihan >= 1 && pilihan <= daftarMenu.size()) {
                ItemMenu item = daftarMenu.get(pilihan - 1);

                System.out.print("Ukuran (T/M)           : ");
                String ukuran = input.nextLine().toUpperCase();
                if (!ukuran.equals("M")) ukuran = "T";
                float hargaFix = ukuran.equals("M") ? item.hargaM : item.hargaT;

                int jumlah = inputAngka(input, "Jumlah Beli            : ");

                if (jumlah > item.stok) {
                    System.out.println("!! Stok tidak mencukupi! Stok " + item.nama + " tersisa: " + item.stok + " pcs !!\n");
                    continue;
                }

                System.out.println();
                String gula = pilihLevel(input, "Gula untuk " + item.nama);
                System.out.println();
                String es = pilihLevel(input, "Es untuk " + item.nama);

                // Potong stok dan perbarui file menu.txt
                item.stok -= jumlah;
                simpanDataMenu();

                Minuman m = new Minuman(item.nama + " " + ukuran, hargaFix, jumlah, ukuran, gula, es);
                m.setStok(item.stok);
                transaksi.addProduk(m);

                System.out.println("\n>> " + item.nama + " berhasil masuk keranjang! (Sisa stok: " + item.stok + ")\n");
            } else {
                System.out.println("!! Nomor menu tidak valid !!\n");
            }

            System.out.println("Ada tambahan pesanan lain?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            int pilTambah = inputAngka(input, "Pilih (1/2): ");
            if (pilTambah == 2) {
                tambahPesanan = false;
            }
        }

        // Voucher Promo
        System.out.println("\n--- VOUCHER PROMO ---");
        System.out.println("Apakah pelanggan menggunakan Voucher Free Darmi Plain?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        int pilVoucher = inputAngka(input, "Pilih (1/2): ");

        if (pilVoucher == 1) {
            System.out.println("\n>> Voucher Diterapkan! Atur rasa untuk item GRATIS:");
            String gulaFree = pilihLevel(input, "Gula (Free Darmi Plain)");
            System.out.println();
            String esFree = pilihLevel(input, "Es (Free Darmi Plain)");

            if (!daftarMenu.isEmpty() && daftarMenu.get(0).stok > 0) {
                daftarMenu.get(0).stok -= 1;
                simpanDataMenu();
            }

            transaksi.addVoucher(new Voucher("FREE-PLAIN", "Promo Gratis Darmi Plain", "Darmi Plain (T)"));
            Minuman freeDrink = new Minuman("Darmi Plain T (FREE)", 0f, 1, "T", gulaFree, esFree);
            transaksi.addProduk(freeDrink);
            System.out.println(">> Item Gratis berhasil ditambahkan!\n");
        }

        // Kalkulasi Pajak & Pembulatan
        float subtotal = transaksi.hitungSubtotal();
        transaksi.setPajak(subtotal * 0.10f);
        transaksi.setPembulatan(0f);
        transaksi.hitungTotal();

        // Metode Pembayaran
        System.out.println("\n--- METODE PEMBAYARAN ---");
        System.out.println("1. QRIS (BCA / GoPay / ShopeePay)");
        System.out.println("2. Debit Card");
        System.out.println("3. Tunai / Cash");
        int pilMetode = inputAngka(input, "Pilih Metode (1-3): ");

        String metode = "";
        String penyedia = "";
        switch (pilMetode) {
            case 1:
                metode = "QRIS";
                penyedia = "Digital Wallet / M-Banking";
                break;
            case 2:
                metode = "Debit";
                penyedia = "Mesin EDC";
                break;
            case 3:
            default:
                metode = "Tunai";
                penyedia = "Cash";
                break;
        }
        transaksi.setPaymentMethod(new PaymentMethod(metode, penyedia));

        // Cetak Struk
        System.out.println("\n===== MENCETAK STRUK =====\n");
        transaksi.cetakStruk();
    }
}