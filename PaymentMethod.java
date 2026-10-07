public class PaymentMethod {
    private String namaMetode;
    private String penyedia;

    public PaymentMethod(String namaMetode, String penyedia) {
        this.namaMetode = namaMetode;
        this.penyedia = penyedia;
    }

    public PaymentMethod() {}

    public void setNamaMetode(String namaMetode) { this.namaMetode = namaMetode; }
    public void setPenyedia(String penyedia) { this.penyedia = penyedia; }

    public String getNamaMetode() { return namaMetode; }
    public String getPenyedia() { return penyedia; }
}