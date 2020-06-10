/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import Model.Transaksi;
import Model.Transaksi_Tbl;
import View.Main_Form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ouka
 */
public class Transaksi_Controller {
    
    private DefaultTableModel tb_transaksi;
    private Koneksi conn = new Koneksi();
    private Transaksi transaksi;
    private Main_Form frame;
    private Transaksi_Tbl transaksi_table;
    private ArrayList<Transaksi_Tbl> transArr = new ArrayList<Transaksi_Tbl>();
    
    public Transaksi_Controller(Main_Form frame, Transaksi transaksi){
        this.frame = frame;
        this.transaksi = transaksi;
        this.tb_transaksi = (DefaultTableModel) frame.getTb_Transaksi().getModel();
        this.frame.getBtnTambah().addActionListener(new ManagementData());
        this.frame.getBtnEditTransaksiField().addActionListener(new ManagementData());
        this.frame.getCekDiskon().addActionListener(new ManagementData());
    }
    
   public ArrayList addtomodelTransaksi(){
        try{
            int id_transaksi = Integer.parseInt(frame.getTXT_id_transaksi().getText());
            int id_produk = Integer.parseInt(frame.getProduk_Transaksi().getText());
            String nama_produk = frame.getNama_Produk();
            int harga_satuan = frame.getHarga();
            int banyak_beli= Integer.parseInt(frame.getBanyak_Beli_transaksi().getText());
            int total = harga_satuan*banyak_beli;
            
            transaksi_table = new Transaksi_Tbl();
            transaksi_table.setTransaksi_Tbl(id_transaksi, id_produk, nama_produk, harga_satuan, banyak_beli, total);
            transArr.add(transaksi_table);
            
            return transArr;
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Please fill value, or give the right values for particular fields! "+ e);
            return null;
        }
    }
   
    public void storeToTBL(){
        ArrayList<Transaksi_Tbl> listBeli = addtomodelTransaksi();
                Object rowData[] = new Object[7];
                int banyak = Integer.parseInt(frame.getBanyak_Beli_transaksi().getText());
                int stok = frame.getStok();
                int sisa = stok-banyak;
                for(int i=0;i<listBeli.size();i++){
                    rowData[0] = listBeli.get(i).getId_transaksi();
                    rowData[1] = listBeli.get(i).getId_produk();
                    rowData[2] = listBeli.get(i).getNama_produk();
                    rowData[3] = listBeli.get(i).getHarga_satuan();
                    rowData[4] = listBeli.get(i).getBanyak_beli();
                    rowData[5] = listBeli.get(i).getTotal();
                    rowData[6] = sisa;
                }
                tb_transaksi.addRow(rowData);
    }
    
//    private int diskon(){
//        int harga_diskon = 0;
//        int
//        return harga_diskon;
//    }
    
    class ManagementData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            int banyak = Integer.parseInt(frame.getBanyak_Beli_transaksi().getText());
            int stok = frame.getStok();
            if(benda == frame.getBtnTambah()){
                if(frame.getTXT_id_transaksi().getText().isEmpty() || frame.getTXT_id_pelanggan().getText().isEmpty() ||
                        frame.getNama_pelanggan().getText().isEmpty() || frame.getTXT_id_pegawai_transaksi().getText().isEmpty() ||
                        frame.getTXT_id_kurir_transaksi().getText().isEmpty() || frame.getTXT_tgl_pengiriman().isEmpty() ||
                        frame.getTXT_tgl_pemesanan().isEmpty() || frame.getTXTAlamat_transaksi().getText().isEmpty() || 
                        frame.getTXTKode_Pos_transaksi().getText().isEmpty() || frame.getTXTNo_telp_transaksi().getText().isEmpty() ||
                        frame.getProduk_Transaksi().getText().isEmpty() || frame.getBanyak_Beli_transaksi().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    if(banyak>stok){
                        JOptionPane.showMessageDialog(null, "Pembelian Melebihi Stok!");
                    } else{
                        frame.getTXT_id_transaksi().setEditable(false);
                        frame.getTXT_id_pelanggan().setEditable(false);
                        frame.getNama_pelanggan().setEditable(false);
                        frame.getTXTAlamat_transaksi().setEditable(false);
                        frame.getTXTKode_Pos_transaksi().setEditable(false);
                        frame.getTXTNo_telp_transaksi().setEditable(false);
                        frame.getBtnChoosePegawai().setEnabled(false);
                        frame.getBtnChooseKurir().setEnabled(false);
                        
                        int total = 0;
                        int harga_satuan = frame.getHarga();
                        int banyak_beli= Integer.parseInt(frame.getBanyak_Beli_transaksi().getText());
                        if(frame.getTb_Transaksi().getRowCount() ==  0){
                            storeToTBL();
                            total = harga_satuan*banyak_beli;
                            frame.getHargaTotalTransaksi().setText(Integer.toString(total));
                        } else {
                            adaIsi(frame.getTb_Transaksi().getRowCount());
                            for(int i=0; i<frame.getTb_Transaksi().getRowCount(); i++){
                                total+=Integer.parseInt(frame.getTb_Transaksi().getValueAt(i, 5).toString());
                            }
                            frame.getHargaTotalTransaksi().setText(Integer.toString(total));
                        }
                        
                        frame.getProduk_Transaksi().setText("");
                        frame.getBanyak_Beli_transaksi().setText("");
                        
                    }
                    
                }
            } else if(benda == frame.getBtnEditTransaksiField()){
                frame.getTXT_id_transaksi().setEditable(true);
                frame.getTXT_id_pelanggan().setEditable(true);
                frame.getNama_pelanggan().setEditable(true);
                frame.getTXTAlamat_transaksi().setEditable(true);
                frame.getTXTKode_Pos_transaksi().setEditable(true);
                frame.getTXTNo_telp_transaksi().setEditable(true);
                frame.getBtnChoosePegawai().setEnabled(true);
                frame.getBtnChooseKurir().setEnabled(true);
            }
        }
        
    }
    
    public void adaIsi(int banyak){
        int id_produk = Integer.parseInt(frame.getProduk_Transaksi().getText()), id;
        boolean tes = false;
        int sama = 0;
        for(int i=0; i<banyak; i++){
            id = (int) frame.getTb_Transaksi().getValueAt(i, 1);
            if(id_produk == id){
                tes = true;
                sama = i;
            }
        }
        sama(tes, sama);
    }
    
    public void sama(boolean check, int id){
        int harga_satuan = frame.getHarga();
        int banyak_beli= Integer.parseInt(frame.getBanyak_Beli_transaksi().getText());
        int total = harga_satuan*banyak_beli;
        int total_banyak_beli;
        
        System.out.print(check+"  "+ id);
        int banyak_beli_di_tabel = (int) frame.getTb_Transaksi().getValueAt(id, 4);
        int banyak_total_di_tabel = (int) frame.getTb_Transaksi().getValueAt(id, 5);
        total_banyak_beli = banyak_beli_di_tabel+banyak_beli;
        if(check){
            if(total_banyak_beli>frame.getStok()){
                JOptionPane.showMessageDialog(null, "Melebihi stok gudang!");
            } else{
                this.frame.getTb_Transaksi().setValueAt(total_banyak_beli, id, 4);
                this.frame.getTb_Transaksi().setValueAt(banyak_total_di_tabel+total, id, 5);
                this.frame.getTb_Transaksi().setValueAt(frame.getStok()-total_banyak_beli, id, 6);
            }
        } else if(!check){
            storeToTBL();
        }
    }
}
