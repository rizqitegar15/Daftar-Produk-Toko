package kelas.controller;

import java.io.IOException;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataPembelian;
import kelas.model.Pembelian;

public class DaftarController {

    @FXML
    private TableView<Pembelian> tabel;
    @FXML
    private TableColumn<Pembelian, String> kodeCol;
    @FXML
    private TableColumn<Pembelian, String> namaCol;
    @FXML
    private TableColumn<Pembelian, String> jenisCol;
    @FXML
    private TableColumn<Pembelian, Integer> jumlahCol;
    @FXML
    private TableColumn<Pembelian, Long> totalCol;
    @FXML
    private TextField cariField;
    @FXML
    private ComboBox<String> filterJenisBox;
    @FXML
    private CheckBox totalCheck;
    @FXML
    private CheckBox jumlahCheck;
    @FXML
    private Label pesanLabel;

    private Navigator navigator;
    private DataPembelian data;
    private final ObservableList<Pembelian> dataTampilan
            = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // KOLOM KODE
        PropertyValueFactory<Pembelian, String> sumberKode
                = new PropertyValueFactory<Pembelian, String>("kode");

        kodeCol.setCellValueFactory(sumberKode);

        // KOLOM NAMA PEMBELI
        PropertyValueFactory<Pembelian, String> sumberNama
                = new PropertyValueFactory<Pembelian, String>("namaPembeli");

        namaCol.setCellValueFactory(sumberNama);

        // KOLOM JENIS TIKET
        PropertyValueFactory<Pembelian, String> sumberJenis
                = new PropertyValueFactory<Pembelian, String>("jenis");

        jenisCol.setCellValueFactory(sumberJenis);

        // KOLOM JUMLAH
        PropertyValueFactory<Pembelian, Integer> sumberJumlah
                = new PropertyValueFactory<Pembelian, Integer>("jumlah");

        jumlahCol.setCellValueFactory(sumberJumlah);

        // KOLOM TOTAL
        PropertyValueFactory<Pembelian, Long> sumberTotal
                = new PropertyValueFactory<Pembelian, Long>("total");

        totalCol.setCellValueFactory(sumberTotal);

        filterJenisBox.getItems().add("Semua");
        filterJenisBox.getItems().add("Reguler");
        filterJenisBox.getItems().add("Mahasiswa");
        filterJenisBox.setValue("Semua");

        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada data yang sesuai."));
        ubahTampilanTotal();
        ubahTampilanJumlah();
    }

    public void setKonteks(Navigator navigator, DataPembelian data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML
    private void cari() {
        if (data == null) {
            return;
        }
        dataTampilan.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);
        String jenisDipilih = filterJenisBox.getValue();

        for (Pembelian pembelian : data.getDaftar()) {
            String kode = pembelian.getKode().toLowerCase(Locale.ROOT);
            String nama = pembelian.getNamaPembeli().toLowerCase(Locale.ROOT);
            boolean cocokKata = false;
            boolean cocokJenis = false;

            if (kode.contains(kata) || nama.contains(kata)) {
                cocokKata = true;
            }
            if ("Semua".equals(jenisDipilih)) {
                cocokJenis = true;
            } else if (pembelian.getJenis().equals(jenisDipilih)) {
                cocokJenis = true;
            }
            if (cocokKata && cocokJenis) {
                dataTampilan.add(pembelian);
            }
        }
        tabel.sort();
        pesanLabel.setText("Jumlah data yang tampil: " + dataTampilan.size());
    }

    @FXML
    private void resetPencarian() {
        cariField.clear();
        filterJenisBox.setValue("Semua");
        cari();
    }

    @FXML
    private void ubahTampilanTotal() {
        if (totalCheck.isSelected()) {
            totalCol.setVisible(true);
        } else {
            totalCol.setVisible(false);
        }
    }

    @FXML
    private void ubahTampilanJumlah() {
        if (jumlahCheck.isSelected()) {
            jumlahCol.setVisible(true);
        } else {
            jumlahCol.setVisible(false);
        }
    }

    @FXML
    private void tambah() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Form gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void detail() {
        Pembelian pembelian = tabel.getSelectionModel().getSelectedItem();
        if (pembelian == null) {
            pesanLabel.setText("Pilih satu baris dahulu.");
            return;
        }
        try {
            navigator.bukaDetail(pembelian);
        } catch (IOException e) {
            pesanLabel.setText("Detail gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void hapus() {
        Pembelian pembelian = tabel.getSelectionModel().getSelectedItem();
        if (pembelian == null) {
            pesanLabel.setText("Pilih satu baris dahulu.");
            return;
        }
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus " + pembelian.getKode() + "?",
                ButtonType.YES, ButtonType.NO);
        dialog.initOwner(tabel.getScene().getWindow());
        ButtonType jawaban = dialog.showAndWait().orElse(ButtonType.NO);
        if (jawaban == ButtonType.YES) {
            data.hapus(pembelian);
            cari();
        }
    }
}
