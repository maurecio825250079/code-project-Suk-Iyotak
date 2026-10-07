public class Minuman extends Produk {
    private String ukuran;
    private String levelGula;
    private String levelEs;

    public Minuman(String namaProduk, float harga, int jumlah, String ukuran, String levelGula, String levelEs) {
        super(namaProduk, harga, jumlah);
        this.ukuran = ukuran;
        this.levelGula = levelGula;
        this.levelEs = levelEs;
    }

    public Minuman() {
        super();
    }

    public void setUkuran(String ukuran) { this.ukuran = ukuran; }
    public void setLevelGula(String levelGula) { this.levelGula = levelGula; }
    public void setLevelEs(String levelEs) { this.levelEs = levelEs; }

    public String getUkuran() { return ukuran; }
    public String getLevelGula() { return levelGula; }
    public String getLevelEs() { return levelEs; }
}