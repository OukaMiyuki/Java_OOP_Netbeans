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
public class Kategori{
    private int id_kategori;
    private String nama_kategori;
    
    public void  setKategori(int id_kategori, String nama_kategori){
        this.id_kategori = id_kategori;
        this.nama_kategori = nama_kategori;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }
}
