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

    public int getId_Kategori(){
	return id_kategori;
    }
	
    public void setId_Kategori(int id_kategori){
	this.id_kategori = id_kategori;
    }

    public String getNama_Kategori(){
	return nama_kategori;
    }

    public void setNama_Kategori(String nama_kategori){
	this.nama_kategori = nama_kategori;
    }
}
