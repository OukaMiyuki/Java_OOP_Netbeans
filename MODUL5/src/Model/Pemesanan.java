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
import java.util.ArrayList;
import java.util.Date;

public class Pemesanan{
    private int id_pemesanan;
    private Pelanggan pelanggan;
    private Pegawai pegawai;
    private Kurir kurir;
    private Date tanggal_pemesanan;
    private Date tanggal_pengiriman;
    private String alamat_pengiriman;
    private int harga_total;
    private ArrayList<Detail_Pemesanan> arrDetail_Pemesanan;
	
    public int getId_Pemesanan(){
	return this.id_pemesanan;
    }

    public void setId_Pemesanan(int id_pemesanan){
	this.id_pemesanan = id_pemesanan;
    }

    public Pelanggan getPelanggan(){
	return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan){
	this.pelanggan = pelanggan;
    }

    public Pegawai getPegawai(){
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai){
	this.pegawai = pegawai;
    }

    public Kurir getKurir(){
        return kurir;
    }

    public void setKurir(Kurir kurir){
	this.kurir = kurir;
    }

    public Date getTanggal_Pemesanan(Date tanggal_pemesanan){
	return tanggal_pemesanan;
    }

    public void setTanggal_Pemesanan(Date tanggal_pemesanan){
	this.tanggal_pemesanan = tanggal_pemesanan;
    }

    public Date getTanggal_Pemgiriman(){
	return tanggal_pengiriman;
    }

    public void setTanggal_Pengiriman(Date tanggal_pengiriman){
	this.tanggal_pengiriman = tanggal_pengiriman;
    }

    public String getAlamat(){
	return alamat_pengiriman;
    }

    public void setAlamat(String alamat){
	this.alamat_pengiriman = alamat;
    }

    public int getHarga_Total(){
	return harga_total;
    }

    public void setHarga_Total(int harga_total){
	this.harga_total = harga_total;
    }

    public void setArrDetail_Pemesanan(ArrayList<Detail_Pemesanan> arrDetail_Pemesanan){
	this.arrDetail_Pemesanan = arrDetail_Pemesanan;
    }

    public ArrayList<Detail_Pemesanan> getArrDetail_Pemesanan(){
	return arrDetail_Pemesanan;
    }
}