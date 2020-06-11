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
public class Detail_transaksi {
    private String tanggal_pemesanan;
    private String tanggal_pengiriman;
    private String alamat_pengiriman;
    private String kode_pos;
    private String no_telp;
    private int total_harga;
    private int diskon;
    private int bayar;
    private int kembalian;
    private int id_produk;
    private String nama_produk;
    private int harga_satuan;
    private int banyak_beli;
    private int total;
    
    public Detail_transaksi(String tanggal_pemesanan, String tanggal_pengiriman, 
            String alamat_pengiriman, String kode_pos, String no_telp, int total_harga, 
            int diskon, int bayar, int kembalian, int id_produk, String nama_produk,
            int harga_satuan, int banyak_beli, int total)
    {
        this.tanggal_pemesanan = tanggal_pemesanan;
        this.tanggal_pengiriman = tanggal_pengiriman;
        this.alamat_pengiriman = alamat_pengiriman;
        this.kode_pos = kode_pos;
        this.no_telp = no_telp;
        this.total_harga = total_harga;
        this.diskon = diskon;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.harga_satuan = harga_satuan;
        this.banyak_beli = banyak_beli;
        this.total= total;
    }

    public String getTanggal_pemesanan() {
        return tanggal_pemesanan;
    }

    public String getTanggal_pengiriman() {
        return tanggal_pengiriman;
    }

    public String getAlamat_pengiriman() {
        return alamat_pengiriman;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public int getDiskon() {
        return diskon;
    }

    public int getBayar() {
        return bayar;
    }

    public int getKembalian() {
        return kembalian;
    }

    public int getId_produk() {
        return id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public int getHarga_satuan() {
        return harga_satuan;
    }

    public int getBanyak_beli() {
        return banyak_beli;
    }

    public int getTotal() {
        return total;
    }

    
    
}
