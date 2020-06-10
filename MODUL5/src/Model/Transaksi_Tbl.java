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
public class Transaksi_Tbl {
    private int id_transaksi;
    private int id_produk;
    private String nama_produk;
    private int harga_satuan;
    private int banyak_beli;
    private int total;
    
    public void setTransaksi_Tbl(int id_transaksi, int id_produk, String nama_produk, int harga_satuan, int banyak_beli, int total){
        this.id_transaksi = id_transaksi;
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.harga_satuan = harga_satuan;
        this.banyak_beli = banyak_beli;
        this.total = total;
    }

    public int getId_transaksi() {
        return id_transaksi;
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
