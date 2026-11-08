package kelas.model;
public class TiketMahasiswa extends Tiket {
 public TiketMahasiswa(String nama,int jumlah){super(nama,jumlah);}
 @Override public long hitungTotal(){return getJumlah()*35000L;}
}
