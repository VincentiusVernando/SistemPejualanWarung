/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import model.Barang;
import model.Detail_TransaksiModel;
import model.Jenis_Barang;
import model.LoginModel;
import model.TransaksiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import database.Koneksi;

/**
 *
 * @author asus
 */
public class TransaksiController {
   Koneksi koneksi;
    ArrayList<Barang> arrBarang;
    ArrayList<Jenis_Barang> arrJenisBarang;
    ArrayList<LoginModel> arrLogin;
    ArrayList<TransaksiModel> arrTransaksi;
    
    public TransaksiController() throws SQLException {
        koneksi = new Koneksi();
        arrBarang = new ArrayList<>();
        arrJenisBarang = new ArrayList<>();
        arrLogin = new ArrayList<>();
        arrTransaksi = new ArrayList<>();
       
    }
     public ArrayList<Jenis_Barang> getDataJenisBarang() throws SQLException {
        arrJenisBarang.clear();
        ResultSet rs = koneksi.getData("SELECT * FROM JenisBarang06974");
        while (rs.next()) {
            Jenis_Barang JB = new Jenis_Barang();
            JB.setId_JenisBarang(rs.getInt("Id_Jenisbarang"));
            JB.setNama_JenisBarang(rs.getString("Nama_JenisBarang"));

            arrJenisBarang.add(JB);
        }

        return arrJenisBarang;
    }
     
      public ArrayList<LoginModel> getDataLogin() throws SQLException {
        arrJenisBarang.clear();
        ResultSet rs = koneksi.getData("SELECT * From LOGIN06974");
        while (rs.next()) {
            LoginModel Lg = new LoginModel();
            Lg.setId_owner(rs.getInt("Id_owner"));
            Lg.setUsername(rs.getString("Username"));
            Lg.setPassword(rs.getString("Password"));

            arrLogin.add(Lg);
        }

        return arrLogin;
    }
      
       public ArrayList<Barang> getDataBarang() throws SQLException {
        arrBarang.clear();
        ResultSet rs = koneksi.getData("SELECT * FROM BARANG06974 JOIN JENISBARANG06974 ON "
                + "BARANG06974.ID_JENISBARANG = JENISBARANG06974.ID_JENISBARANG");
        while (rs.next()) {

            Jenis_Barang JB = new Jenis_Barang();
            JB.setId_JenisBarang(rs.getInt("ID_JENISBARANG"));
            JB.setNama_JenisBarang(rs.getString("NAMA_JENISBARANG"));

            Barang b = new Barang();
            b.setId_barang(rs.getInt("ID_BARANG"));
            b.setJenis_barang(JB);
            b.setNama_Barang(rs.getString("NAMA_BARANG"));
            b.setHarga_barang(rs.getInt("HARGA_BARANG"));
            

            arrBarang.add(b);
        }

        return arrBarang;
    }
       
       public ArrayList<TransaksiModel> getDataTransaksi() throws SQLException {
        arrTransaksi.clear();
        ResultSet rs = koneksi.getData("SELECT LOGIN06974.*, TRANSAKSI06974.* FROM TRANSAKSI06974 JOIN LOGIN06974 ON TRANSAKSI06974.ID_OWNER = "
                + "LOGIN06974.ID_OWNER ORDER BY ID_TRANSAKSI DESC");

        while (rs.next()) {
            LoginModel Lg = new LoginModel();
            Lg.setId_owner(rs.getInt("ID_OWNER"));
            Lg.setUsername(rs.getString("USERNAME"));
            Lg.setPassword(rs.getString("PASSWORD"));

            TransaksiModel tr = new TransaksiModel();
            tr.setId_transaksi(rs.getInt("ID_TRANSAKSI"));
            tr.setLogin(Lg);
            tr.setTotal_harga(rs.getDouble("TOTAL_HARGA"));
            tr.setTgl_transaksi(rs.getString("TGL_TRANSAKSI")); 
            tr.setUang_bayar(rs.getDouble("UANG_BAYAR"));
            tr.setKembalian(rs.getDouble("KEMBALIAN"));

            ResultSet rsDetail_Pemesanan = koneksi.getData("SELECT * FROM DETAIL_TRANSAKSI JOIN "
                    + "BARANG06974 ON DETAIL_TRANSAKSI.ID_BARANG = BARANG06974.ID_BARANG JOIN "
                    + "JENISBARANG06974 ON BARANG06974.ID_JENISBARANG = JENISBARANG06974.ID_JENISBARANG WHERE "
                    + "DETAIL_TRANSAKSI.ID_TRANSAKSI = " + rs.getString("ID_TRANSAKSI"));
            ArrayList<Detail_TransaksiModel> dt = new ArrayList<>();
            while (rsDetail_Pemesanan.next()) {
                Jenis_Barang JB = new Jenis_Barang();
                JB.setId_JenisBarang(rsDetail_Pemesanan.getInt("ID_JENISBARANG"));
                JB.setNama_JenisBarang(rsDetail_Pemesanan.getString("NAMA_JENISBARANG"));

                Barang b = new Barang();
                b.setId_barang(rsDetail_Pemesanan.getInt("ID_BARANG"));
                b.setJenis_barang(JB);
                b.setNama_Barang(rsDetail_Pemesanan.getString("NAMA_BARANG"));
                b.setHarga_barang(rsDetail_Pemesanan.getInt("HARGA_BARANG"));
               

                Detail_TransaksiModel dps = new Detail_TransaksiModel();
                dps.setBarang(b);
                dps.setJumlah(rsDetail_Pemesanan.getInt("JUMLAH"));
              
                dt.add(dps);
            }
            tr.setArrDetail_Transaksi(dt);
            arrTransaksi.add(tr);
        }

        return arrTransaksi;
    }
       
       public void insertTransaksi(TransaksiModel transaksi) {
        try {
            

             koneksi.ManipulasiData("INSERT INTO TRANSAKSI06974 VALUES (ID_TRANSAKSI.NEXTVAL, "
                    + transaksi.getLogin().getId_owner()+ "," + transaksi.getTotal_harga().toString()
                    +transaksi.getTgl_transaksi()
                    + transaksi.getUang_bayar().toString() + "'," + transaksi.getKembalian().toString() + ")");
            ResultSet trs = koneksi.getData("SELECT ID_TRANSAKSI.CURRVAL FROM DUAL");
            System.out.println(trs);
            trs.next();
            int id_transaksi = trs.getInt("CURRVAL");
            for (Detail_TransaksiModel p : transaksi.getArrDetail_Transaksi()) {
                koneksi.ManipulasiData("INSERT INTO DETAIL_TRANSAKSI VALUES (" + p.getBarang().getId_barang()
                        + "," + id_transaksi + "," + p.getJumlah() + ")");
                koneksi.ManipulasiData("UPDATE BARANG SET STOK = STOK- " + p.getJumlah()
                        + "WHERE ID_BARANG= " + p.getBarang().getId_barang());
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    } 
}
