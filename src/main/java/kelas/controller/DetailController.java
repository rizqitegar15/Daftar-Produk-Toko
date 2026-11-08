package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kelas.Navigator;
import kelas.model.Pembelian;

public class DetailController {

    @FXML
    private Label kodeLabel, namaLabel, jenisLabel, jumlahLabel, totalLabel, pesanLabel;
    @FXML
    private ImageView gambarView;
    private Navigator navigator;

    public void setData(Navigator navigator, Pembelian p) {
        this.navigator = navigator;
        kodeLabel.setText(p.getKode());
        namaLabel.setText(p.getTiket().getNamaPembeli());
        jenisLabel.setText(p.getJenis());
        jumlahLabel.setText(String.valueOf(p.getTiket().getJumlah()));
        totalLabel.setText(NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID")).format(p.getTiket().hitungTotal()));
        if (p.getGambarUri() != null) {
            Image image = new Image(p.getGambarUri(), 640, 480, true, true);
            if (image.isError()) {
                pesanLabel.setText("Lampiran tidak ditemukan atau tidak dapat dibaca.");
            } else {
                gambarView.setImage(image);
            }
        } else {
            pesanLabel.setText("Tanpa lampiran gambar.");
        }
    }

    @FXML
    private void tambahLagi() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Gagal membuka form.");
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
