import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Penjualan implements CetakStruk {
    private String nomorNota;
    private String waktu;
    private int noUrut;
    private String namaToko;
    private String alamatToko;
    private String jenisOrder;
    private float subtotal;
    private float pajak;
    private float pembulatan;
    private float total;

    private ArrayList<Produk> listProduk = new ArrayList<>();
    private PaymentMethod paymentMethod;
    private ArrayList<Voucher> listVoucher = new ArrayList<>();

    public Penjualan() {}

    public void setNomorNota(String nomorNota) { this.nomorNota = nomorNota; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public void setNoUrut(int noUrut) { this.noUrut = noUrut; }
    public void setNamaToko(String namaToko) { this.namaToko = namaToko; }
    public void setAlamatToko(String alamatToko) { this.alamatToko = alamatToko; }
    public void setJenisOrder(String jenisOrder) { this.jenisOrder = jenisOrder; }
    public void setPajak(float pajak) { this.pajak = pajak; }
    public void setPembulatan(float pembulatan) { this.pembulatan = pembulatan; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNomorNota() { return nomorNota; }
    public String getWaktu() { return waktu; }
    public int getNoUrut() { return noUrut; }

    public void addProduk(Produk p) { this.listProduk.add(p); }
    public void addVoucher(Voucher v) { this.listVoucher.add(v); }

    public float hitungSubtotal() {
        float sub = 0;
        for (Produk p : listProduk) {
            sub += p.hitungSubtotal();
        }
        this.subtotal = sub;
        return sub;
    }

    public float hitungTotal() {
        this.total = this.subtotal + this.pajak + this.pembulatan;
        return this.total;
    }

    @Override
    public void cetakStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("                ").append(namaToko.toUpperCase()).append("\n");
        sb.append("        ").append(alamatToko).append("\n");
        sb.append("==================================================\n");
        sb.append("No Nota : ").append(nomorNota).append("\n");
        sb.append("Waktu   : ").append(waktu).append("\n");
        sb.append("No Urut : ").append(noUrut).append("\n");
        sb.append("Order   : ").append(jenisOrder).append("\n");
        sb.append("--------------------------------------------------\n");

        for (Produk p : listProduk) {
            sb.append(String.format("%-30s x%-2d  Rp %,9.0f\n", p.getNamaProduk(), p.getJumlah(), p.hitungSubtotal()));
            if (p instanceof Minuman) {
                Minuman m = (Minuman) p;
                sb.append("   [Ukuran: ").append(m.getUkuran()).append(" | Gula: ").append(m.getLevelGula()).append(" | Es: ").append(m.getLevelEs()).append("]\n");
            }
        }

        if (!listVoucher.isEmpty()) {
            sb.append("--------------------------------------------------\n");
            sb.append("Voucher / Promo Terpasang:\n");
            for (Voucher v : listVoucher) {
                sb.append(" * Kode : ").append(v.getKodeVoucher()).append(" - ").append(v.getNamaVoucher()).append("\n");
                sb.append("   Gratis Produk: ").append(v.getProdukGratis()).append("\n");
            }
        }

        sb.append("--------------------------------------------------\n");
        sb.append(String.format("Subtotal              : Rp %,12.0f\n", subtotal));
        sb.append(String.format("Pajak                 : Rp %,12.0f\n", pajak));
        sb.append(String.format("Pembulatan            : Rp %,12.0f\n", pembulatan));
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("TOTAL BAYAR           : Rp %,12.0f\n", total));

        if (paymentMethod != null) {
            sb.append("Metode Pembayaran     : ").append(paymentMethod.getNamaMetode()).append(" (").append(paymentMethod.getPenyedia()).append(")\n");
        }

        sb.append("==================================================\n");
        sb.append("        Terima Kasih Atas Kunjungan Anda!        \n");
        sb.append("==================================================\n");

        String hasilStruk = sb.toString();

        // Cetak di Console
        System.out.print(hasilStruk);

        // Simpan ke file struk_pembayaran.txt
        try (FileWriter fw = new FileWriter("struk_pembayaran.txt");
             PrintWriter pw = new PrintWriter(fw)) {
            pw.print(hasilStruk);
            System.out.println("\n[SYSTEM] Berhasil menyimpan struk ke file 'struk_pembayaran.txt'");
        } catch (IOException e) {
            System.out.println("\n[SYSTEM ERROR] Gagal menyimpan file: " + e.getMessage());
        }
    }
}