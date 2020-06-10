/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul5;

import Controller.Kurir_Controller;
import Controller.Pegawai_Controller;
import Controller.Pemasok_Controller;
import Controller.Kategori_Controller;
import Controller.Produk_Controller;
import Controller.Transaksi_Controller;
import Database.Koneksi;
import Model.Kategori;
import Model.Kurir;
import Model.Pegawai;
import Model.Pemasok;
import Model.Produk;
import Model.Transaksi;
import View.Choose_Kategori;
import View.Choose_Kurir;
import View.Choose_Pegawai;
import View.Choose_Pemasok;
import View.Choose_Produk;
import View.Main_Form;
import javax.swing.JFrame;

/**
 *
 * @author Ouka
 */
public class MODUL5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Koneksi con = new Koneksi();
        con.getConnection();
        Main_Form form = new Main_Form();
        form.setExtendedState(form.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        form.setVisible(true);
        Pegawai modelPegawai = new Pegawai();
        Choose_Pegawai pilih_pegawai = new Choose_Pegawai();
        Pegawai_Controller controller_pegawai = new Pegawai_Controller(form, modelPegawai, pilih_pegawai);
        Kurir kurir = new Kurir();
        Choose_Kurir pilih_kurir = new Choose_Kurir();
        Kurir_Controller controller_kurir = new Kurir_Controller(form, kurir, pilih_kurir);
        Pemasok pemasok = new Pemasok();
        Choose_Pemasok pilih_pemasok = new Choose_Pemasok();
        Pemasok_Controller controller_pemasok = new Pemasok_Controller(pilih_pemasok, form, pemasok);
        Kategori kategori = new Kategori();
        Choose_Kategori pilih_kategori = new Choose_Kategori();
        Kategori_Controller controller_kategori = new Kategori_Controller(pilih_kategori, form, kategori);
        Produk produk = new Produk();
        Choose_Produk pilih_produk = new Choose_Produk();
        Produk_Controller controller_produk = new Produk_Controller(form, produk, pilih_produk);
        Transaksi transaksi = new Transaksi();
        Transaksi_Controller controller_transaksi = new Transaksi_Controller(form, transaksi);
    }
    
}
