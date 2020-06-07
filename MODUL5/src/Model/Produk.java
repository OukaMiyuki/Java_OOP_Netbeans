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
    private Pemasok pemasok;
    private Kategori kategori;
    private String nama_produk;
    private int harga_satuan;
    private int stok_produk;

    public int getId_Produk(){
	return id_produk;
    }

    public void setId_Produk(int id_produk){
	this.id_produk = id_produk;
    }

    public Pemasok getPemasok(){
	return pemasok;
    }

    private void setPemasok(Pemasok pemasok){
	this.pemasok = pemasok;	
    }

    public Kategori getKategori(){
	return kategori;
    }

    public void setKategori(Kategori kategori){
	this.kategori = kategori;
    }

    public String getNama_Produk(){
	return this.nama_produk;
    }

    public void setNama_Produk(String nama_produk){
	this.nama_produk = nama_produk;
    }

    public int getHarga_Satuan(){
	return harga_satuan;
    }

    public void setHarga_Satuan(){
	this.harga_satuan = harga_satuan;
    }

    public int getStok_Produk(){
	return stok_produk;
    }

    public void setStok_Produk(int stok_produk){
	this.stok_produk = stok_produk;
    }
}
