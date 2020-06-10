/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Ouka
 */
public class Transaksi {
    private int id_transaksi;
    private int id_pelanggan;
    private String nama_pelanggan;
    private int id_pegawai;
    private int id_kurir;
    private String tanggal_pemesanan;
    private String tanggal_pengiriman;
    private String alamat;
    private int kode_pos;
    private String no_telp;
    private int total_harga;
    private float diskon;
    private int bayar;
    private int kembalian;
    
    public void setTransaksi(int id_transaksi, int id_pelanggan, String nama_pelanggan, int id_pegawai, int id_kurir, String tanggal_pemesanan, 
            String tanggal_pengiriman, String alamat, int kode_pos, String no_telp, int total_harga, float diskon, int bayar, int kembalian)
    {
       this.id_transaksi = id_transaksi;
       this.id_pelanggan = id_pelanggan;
       this.nama_pelanggan = nama_pelanggan;
       this.id_pegawai = id_pegawai;
       this.id_kurir = id_kurir;
       this.tanggal_pemesanan = this.tanggal_pemesanan;
       this.tanggal_pengiriman = tanggal_pengiriman;
       this.alamat = alamat;
       this.kode_pos = kode_pos;
       this.no_telp = no_telp;
       this.total_harga = total_harga;
       this.diskon = diskon;
       this.bayar = bayar;
       this.kembalian = kembalian;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public int getId_pegawai() {
        return id_pegawai;
    }

    public int getId_kurir() {
        return id_kurir;
    }

    public String getTanggal_pemesanan() {
        return tanggal_pemesanan;
    }

    public String getTanggal_pengiriman() {
        return tanggal_pengiriman;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getKode_pos() {
        return kode_pos;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public float getDiskon() {
        return diskon;
    }

    public int getBayar() {
        return bayar;
    }

    public int getKembalian() {
        return kembalian;
    }
}
