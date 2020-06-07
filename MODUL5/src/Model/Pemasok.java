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
public class Pemasok{
    private int id_pemasok;
    private String nama_perusahaan;
    private String alamat;
    private int kode_pos;
    private String no_telp;
    
    public void setPemasok(int id_pemasok, String nama_perusahaan, String alamat, int kode_pos, String no_telp){
        this.id_pemasok = id_pemasok;
        this.nama_perusahaan = nama_perusahaan;
        this.alamat = alamat;
        this.kode_pos = kode_pos;
        this.no_telp = no_telp;
    }

    public int getId_pemasok() {
        return id_pemasok;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
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