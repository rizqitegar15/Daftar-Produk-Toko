package kelas.model;

public class Pembelian {

    private final String kode;
    private final Tiket tiket;
    private final String gambarUri;

    public Pembelian(String kode, Tiket tiket, String gambarUri) {
        if (kode == null || kode.isBlank() || tiket == null) {
            throw new IllegalArgumentException("Data pembelian tidak lengkap.");
        }
        this.kode = kode;
        this.tiket = tiket;
        this.gambarUri = gambarUri;
    }

    public String getKode() {
        return kode;
    }

    public Tiket getTiket() {
        return tiket;
    }

    public String getGambarUri() {
        return gambarUri;
    }

    public String getJenis() {
        if (tiket instanceof TiketMahasiswa) {
            return "Mahasiswa";
        } else {
            return "Reguler";
        }
    }

    public String getNamaPembeli() {
        return tiket.getNamaPembeli();
    }

    public int getJumlah() {
        return tiket.getJumlah();
    }

    public long getTotal() {
        return tiket.hitungTotal();
    }
}
