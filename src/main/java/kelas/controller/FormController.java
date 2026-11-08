package kelas.controller;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import kelas.Navigator;
import kelas.data.DataPembelian;
import kelas.model.*;

public class FormController {

    @FXML
    private TextField namaField, jumlahField;
    @FXML
    private ComboBox<String> jenisBox;
    @FXML
    private Label pesanLabel;
    @FXML
    private ImageView gambarView;
    private Navigator navigator;
    private DataPembelian data;
    private String gambarUri;

    @FXML
    private void initialize() {
        jenisBox.getItems().addAll("Reguler", "Mahasiswa");
        jenisBox.setValue("Reguler");
    }

    public void setKonteks(Navigator navigator, DataPembelian data) {
        this.navigator = navigator;
        this.data = data;
    }

    @FXML
    private void pilihGambar() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Pilih lampiran gambar");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Gambar PNG/JPEG", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(namaField.getScene().getWindow());
        if (file == null) {
            return; // Batal: pertahankan pilihan sebelumnya.
        }
        if (file.length() > 5L * 1024 * 1024) {
            pesanLabel.setText("Maksimum gambar 5 MB.");
            return;
        }
        Image image = new Image(file.toURI().toString(), 640, 480, true, true);
        if (image.isError()) {
            pesanLabel.setText("Gambar tidak dapat dibaca.");
            return;
        }
        gambarUri = file.toURI().toString();
        gambarView.setImage(image);
        pesanLabel.setText("Gambar dipilih, belum diunggah ke server.");
    }

    @FXML
    private void simpan() {
        Pembelian p;
        try {
            int jumlah = Integer.parseInt(jumlahField.getText().trim());
            Tiket tiket;

            if ("Mahasiswa".equals(jenisBox.getValue())) {
                tiket = new TiketMahasiswa(namaField.getText(), jumlah);
            } else {
                tiket = new TiketReguler(namaField.getText(), jumlah);
            }
            p = data.tambah(tiket, gambarUri);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Jumlah harus bilangan bulat dalam rentang int positif.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }
        try {
            navigator.bukaDetail(p);
        } catch (IOException e) {
            data.hapus(p);
            pesanLabel.setText("Halaman detail gagal dibuka; data dibatalkan. Periksa FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    private void bukaDaftar() {
        try {
            navigator.bukaDaftar();
        } catch (IOException e) {
            pesanLabel.setText("Gagal membuka daftar.");
            e.printStackTrace();
        }
    }
}
