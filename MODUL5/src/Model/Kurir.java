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
public class Kurir{
    private int id_kurir;
    private String nama_perusahaan;
    private String no_telp;
    
    public void setKurir(int id_kurir, String nama_perusahaan, String no_telp){
        this.id_kurir = id_kurir;
        this.nama_perusahaan = nama_perusahaan;
        this.no_telp = no_telp;
    }

    public int getId_kurir() {
        return id_kurir;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public String getNo_telp() {
        return no_telp;
    }
}