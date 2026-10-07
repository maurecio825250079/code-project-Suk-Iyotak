public class Produk {
    private String namaProduk;
    private float harga;
    private int jumlah;
    private int stok;

    public Produk(String namaProduk, float harga, int jumlah, int stok) {
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.jumlah = jumlah;
        this.stok = stok;
    }

    public Produk(String namaProduk, float harga, int jumlah) {
        this(namaProduk, harga, jumlah, 0);
    }

    public Produk() {}

    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
    public void setHarga(float harga) { this.harga = harga; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setStok(int stok) { this.stok = stok; }

    public String getNamaProduk() { return namaProduk; }
    public float getHarga() { return harga; }
    public int getJumlah() { return jumlah; }
    public int getStok() { return stok; }

    public float hitungSubtotal() {
        return this.harga * this.jumlah;
    }
}   