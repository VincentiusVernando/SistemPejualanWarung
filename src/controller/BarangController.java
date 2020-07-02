/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Barang;
import model.Jenis_Barang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.Koneksi;
/**
 *
 * @author asus
 */
public class BarangController {
   Koneksi koneksi;
    ArrayList<Barang> arrproduk;

    public BarangController() throws SQLException {
        this.koneksi = new Koneksi();
        this.arrproduk = new ArrayList<>();
    }

    public ArrayList<Barang> getDataBarang(Barang p) throws SQLException {

       this.arrproduk.clear();
       ResultSet rs = this.koneksi.getData("SELECT BARANG06974.*, JENISBARANG06974.* FROM BARANG06974 JOIN JENISBARANG06974 ON BARANG06974.ID_JENISBARANG = JENISBARANG06974.ID_JENISBARANG ORDER BY ID_BARANG ASC");

        while (rs.next()) {
            Jenis_Barang JB = new Jenis_Barang();
            JB.setId_JenisBarang(rs.getInt("ID_JENISBARANG"));
            JB.setNama_JenisBarang(rs.getString("NAMA_JENISBARANG"));

            Barang barang = new Barang();
            barang.setId_barang(rs.getInt("ID_BARANG"));
            barang.setJenis_barang(JB);
            barang.setNama_Barang(rs.getString("NAMA_BARANG"));
            barang.setHarga_barang(rs.getInt("HARGA_BARANG"));
            
            this.arrproduk.add(barang);

        }
        return this.arrproduk;
    }

    public void insertBarang(Barang databarang) {
        this.koneksi.ManipulasiData("INSERT INTO BARANG VALUES (" + databarang.getId_barang() + ", "
                + databarang.getJenis_barang().getId_JenisBarang() + ", '" + databarang.getNama_barang() + "', '"
                + databarang.getHarga_barang() + "', '" + databarang.getStok() + "')");

    }

    public void deleteProduk(int idProduk) {
        try {
            this.koneksi.ManipulasiData("DELETE BARANG WHERE ID_BARANG=" + idProduk);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update(Barang databarang) {
        String kodeSql = "UPDATE BARANG SET " + "ID_JENISBARANG = '" + databarang.getJenis_barang().getId_JenisBarang()+ "', "
                + "NAMA_BARANG = '" + databarang.getNama_barang() + "', " + "HARGA_BARANG = '" + databarang.getHarga_barang() + "'," 
                + "STOK = '" + databarang.getStok() + "'" 
                + "WHERE ID_BARANG = " + databarang.getId_barang();
        this.koneksi.ManipulasiData(kodeSql);
    }  
}
