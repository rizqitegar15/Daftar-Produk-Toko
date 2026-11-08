package kelas.model;
public class TiketReguler extends Tiket {
 public TiketReguler(String nama,int jumlah){super(nama,jumlah);}
 @Override public long hitungTotal(){return getJumlah()*50000L;}
}
