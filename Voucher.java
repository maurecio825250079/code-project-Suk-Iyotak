public class Voucher {
    private String kodeVoucher;
    private String namaVoucher;
    private String produkGratis;

    public Voucher(String kodeVoucher, String namaVoucher, String produkGratis) {
        this.kodeVoucher = kodeVoucher;
        this.namaVoucher = namaVoucher;
        this.produkGratis = produkGratis;
    }

    public Voucher() {}

    public void setKodeVoucher(String kodeVoucher) { this.kodeVoucher = kodeVoucher; }
    public void setNamaVoucher(String namaVoucher) { this.namaVoucher = namaVoucher; }
    public void setProdukGratis(String produkGratis) { this.produkGratis = produkGratis; }

    public String getKodeVoucher() { return kodeVoucher; }
    public String getNamaVoucher() { return namaVoucher; }
    public String getProdukGratis() { return produkGratis; }
}