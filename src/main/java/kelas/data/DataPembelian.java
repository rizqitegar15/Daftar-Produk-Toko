package kelas.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.model.Pembelian;
import kelas.model.Tiket;

public class DataPembelian {

    private final ObservableList<Pembelian> daftar = FXCollections.observableArrayList();
    private int urutan = 1;

    public ObservableList<Pembelian> getDaftar() {
        return FXCollections.unmodifiableObservableList(daftar);
    }

    public Pembelian tambah(Tiket tiket, String uri) {
        //TR%03d  Bagian ini digunakan untuk membuat kode pembelian, misalnya TR001, TR002, dan seterusnya.
        Pembelian p = new Pembelian(String.format("TR%03d", urutan), tiket, uri);
        daftar.add(p);
        urutan++;
        return p;
    }

    public void hapus(Pembelian p) {
        daftar.remove(p);
    }
}
