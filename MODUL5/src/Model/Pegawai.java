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
public class Pegawai {
    private int id_pegawai;
    private String nama_depan;
    private String nama_belakang;
    private String tanggal_lahir;
    private String alamat;
    private int kode_pos;
    private String no_telp;
    
    public void setPegawai(int id_pegawai, String nama_depan, String nama_belakang, String tanggal_lahir, String alamat, int kode_pos, String no_telp){
        this.id_pegawai = id_pegawai;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
        this.tanggal_lahir = tanggal_lahir;
        this.alamat = alamat;
        this.kode_pos = kode_pos;
        this.no_telp = no_telp;
    }

    public int getId_pegawai() {
        return id_pegawai;
    }

    public String getNama_depan() {
        return nama_depan;
    }

    public String getNama_belakang() {
        return nama_belakang;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
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
}
