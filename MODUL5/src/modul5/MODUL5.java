/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul5;

import Controller.Kurir_Controller;
import Controller.Pegawai_Controller;
import Controller.Pemasok_Controller;
import Database.Koneksi;
import Model.Kurir;
import Model.Pegawai;
import Model.Pemasok;
import View.Main_Form;

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
        form.setVisible(true);
        Pegawai modelPegawai = new Pegawai();
        Pegawai_Controller controller_pegawai = new Pegawai_Controller(form, modelPegawai);
        Kurir kurir = new Kurir();
        Kurir_Controller controller_kurir = new Kurir_Controller(form, kurir);
        Pemasok pemasok = new Pemasok();
        Pemasok_Controller controller_pemasok = new Pemasok_Controller(form, pemasok);
    }
    
}
