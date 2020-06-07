/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Ouka
 */
public class Pelanggan{
    private int id_pelanggan;
    private String nama_depan;
    private String nama_belakang;
    private Date tanggal_lahir;
    private String alamat;
    private int kode_pos;
    private int no_telp;

    public int getId_Pelanggan(){
        return id_pelanggan;
    }

    public void setId_Pelanggan(Integer id_pelanggan){
	this.id_pelanggan = id_pelanggan;
    }

    public String getNama_Depan(){
        return nama_depan;
    }

    public void setNama_Depan(String nama_pdepan){
        this.nama_depan = nama_depan;
    }

    public String getNama_Belakang(){
        return nama_belakang;
    }

    public void setNama_Belakang(String nama_belakang){
        this.nama_belakang = nama_belakang;
    }

    public Date getTanggal_Lahir(){
        return tanggal_lahir;
    }

    public void setTanggal_lahir(Date tanggal_lahir){
        this.tanggal_lahir = tanggal_lahir;
    }

    public String get_Alamat(){
        return alamat;
    }

    public void setAlamat(String alamat){
	this.alamat = alamat;
    }

    public int getKode_Pos(){
	return kode_pos;
    }

    public void setKode_Pos(Integer kode_pos){
	this.kode_pos = kode_pos;
    }

    public int getNo_Telp(){
	return no_telp;
    }

    public void setNo_Telp(Integer id_pegawai){
	this.no_telp = no_telp;
    }
}