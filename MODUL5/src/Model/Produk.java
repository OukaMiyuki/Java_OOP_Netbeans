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
public class Produk{
    private int id_produk;
    private int id_pemasok;
    private int id_kategori;
    private String nama_produk;
    private int harga_satuan;
    private int stok_produk;

    public void setProduk(int id_produk, int id_pemasok, int id_kategori, String nama_produk, int harga_satuan, int stok_produk){
        this.id_produk = id_produk;
        this.id_pemasok = id_pemasok;
        this.id_kategori = id_kategori;
        this.nama_produk = nama_produk;
        this.harga_satuan = harga_satuan;
        this.stok_produk = stok_produk;
    }

    public int getId_produk() {
        return id_produk;
    }

    public int getId_pemasok() {
        return id_pemasok;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public int getHarga_satuan() {
        return harga_satuan;
    }

    public int getStok_produk() {
        return stok_produk;
    }
}
