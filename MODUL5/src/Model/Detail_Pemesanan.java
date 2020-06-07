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
public class Detail_Pemesanan{
    private Produk produk;
    private int jumlah;
    private Double diskon;

    public Produk getProduk(){
	return produk;
    }

    public void setProduk(Produk produk){
	this.produk = produk;
    }

    public int getjumlah(){
	return jumlah;
    }

    public void setJumlah(int jumlah){
	this.jumlah = jumlah;
    }

    public Double getDiskon(){
	return diskon;
    }

    public void setDiskon(Double diskon){
	this.diskon = diskon;
    }
}
