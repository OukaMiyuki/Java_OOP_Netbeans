/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import Model.Detail_transaksi;
import Model.Transaksi;
import Model.Transaksi_Tbl;
import View.Main_Form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ouka
 */
public class Record_Controller {
    private DefaultTableModel tb_transaksi_record;
    private DefaultTableModel tb_transaksi_record_detail;
    private Koneksi conn = new Koneksi();
    private Main_Form frame;
    private Transaksi transaksi;
    private int pos = 0;
    
    public Record_Controller(Main_Form frame, Transaksi transaksi){
        this.frame = frame;
        this.transaksi = transaksi;
        this.tb_transaksi_record = (DefaultTableModel) this.frame.getTB_transaksi_record().getModel();
        this.tb_transaksi_record_detail = (DefaultTableModel) this.frame.getTB_transaksi_record_detail().getModel();
        this.frame.getBtnRefreshRecord().addActionListener(new ManagementData());
        
        show_data_to_table_transaksi_record();
        
        this.frame.getTB_transaksi_record().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_transaksi_record_clicked(evt);
            }
        });
        
    }
    
    public void tb_transaksi_record_clicked(java.awt.event.MouseEvent evt){
        int index =  frame.getTB_transaksi_record().getSelectedRow();
        int id_pelanggan = Integer.parseInt(frame.getTB_transaksi_record().getValueAt(index, 2).toString());
        ArrayList<Detail_transaksi> tranDArr = getItemTransaksiDetailRecord(id_pelanggan);
        this.tb_transaksi_record_detail.setRowCount(0);
        Object[] kolom = new Object[5];
        for (int i = 0; i < tranDArr.size(); i++) {
            kolom[0] = tranDArr.get(i).getId_produk();
            kolom[1] = tranDArr.get(i).getNama_produk();
            kolom[2] = tranDArr.get(i).getHarga_satuan();
            kolom[3] = tranDArr.get(i).getBanyak_beli();
            kolom[4] = tranDArr.get(i).getTotal();
            
            tb_transaksi_record_detail.addRow(kolom);
            
            this.frame.getTXTarea_record().setText("=======================================\n"
                                                + "Tanggal Pemesanan : "+tranDArr.get(i).getTanggal_pemesanan()+"\n"
                                                + "Tanggal Pengiriman : "+tranDArr.get(i).getTanggal_pengiriman()+"\n"
                                                + "Alamat Pengiriman : "+tranDArr.get(i).getAlamat_pengiriman()+"\n"
                                                + "Kode Pos : "+tranDArr.get(i).getKode_pos()+"\n"
                                                + "No. Telp : "+tranDArr.get(i).getNo_telp()+"\n"
                                                + "Total Harga : "+tranDArr.get(i).getTotal_harga()+"\n"
                                                + "Diskon : "+tranDArr.get(i).getDiskon()+"\n"
                                                + "Bayar : "+tranDArr.get(i).getBayar()
                                                + "Kembalian : "+tranDArr.get(i).getKembalian()+"\n"
                                                + "=======================================\n");
        }
        
    }
    
    public ArrayList<Detail_transaksi> getItemTransaksiDetailRecord(int id_pelanggan){
        ArrayList<Detail_transaksi> tranDArr = new ArrayList<Detail_transaksi>();
        Connection con = conn.getConnection();
        String query = "SELECT*FROM transaksi INNER JOIN detail_transaksi ON detail_transaksi.id_transaksi = transaksi.id_transaksi WHERE id_pelanggan = "+ id_pelanggan +"";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Detail_transaksi dtl_transaksi;
            while(rs.next()){
                dtl_transaksi = new Detail_transaksi(rs.getString("tanggal_pemesanan"), rs.getString("tanggal_pengiriman"), rs.getString("alamat_pengiriman"), 
                                                        rs.getString("kode_pos"), rs.getString("no_telp"), rs.getInt("total_harga"), rs.getInt("diskon"), 
                                                        rs.getInt("bayar"), rs.getInt("kembalian"), rs.getInt("id_produk"), rs.getString("nama_produk"),
                                                        rs.getInt("harga_satuan"), rs.getInt("banyak_beli"), rs.getInt("total"));
                tranDArr.add(dtl_transaksi);
            }
        } catch(SQLException ex){
            System.out.println("failed to show! " + ex);
        }
        System.out.print(id_pelanggan);
        return tranDArr;
    }
    
    public ArrayList<Transaksi> getItemTransaksiRecord(){
        ArrayList<Transaksi> transArr = new ArrayList<Transaksi>();
        Connection con = conn.getConnection();
        String query = "SELECT * FROM transaksi ORDER BY id_transaksi ASC";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Transaksi transaksi;
            while(rs.next()){
                transaksi = new Transaksi();
                transaksi.setTransaksi(rs.getInt("id_transaksi"), rs.getInt("id_pelanggan"), 
                        rs.getString("nama_pelanggan"), rs.getInt("id_pegawai"), rs.getInt("id_kurir"), 
                        rs.getString("tanggal_pemesanan"), rs.getString("tanggal_pengiriman"), rs.getString("alamat_pengiriman"), 
                        rs.getString("kode_pos"), rs.getString("no_telp"), rs.getInt("total_harga"), rs.getInt("diskon"),
                        rs.getInt("bayar"), rs.getInt("kembalian"));
                transArr.add(transaksi);
            }
        } catch(SQLException ex){
            System.out.println("failed to show! " + ex);
        }
        return transArr;
    }
    
    public void show_data_to_table_transaksi_record(){
        ArrayList<Transaksi> transArr = getItemTransaksiRecord();
        tb_transaksi_record.setRowCount(0);
        Object[] kolom = new Object[7];
        for (int i = 0; i < transArr.size(); i++) {
            kolom[0] = i+1;
            kolom[1] = transArr.get(i).getId_transaksi();
            kolom[2] = transArr.get(i).getId_pelanggan();
            kolom[3] = transArr.get(i).getNama_pelanggan();
            kolom[4] = transArr.get(i).getTanggal_pemesanan();
            kolom[5] = transArr.get(i).getTanggal_pengiriman();
            kolom[6] = transArr.get(i).getTotal_harga();
            tb_transaksi_record.addRow(kolom);
        }
    }
    
    class ManagementData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if(benda == frame.getBtnRefreshRecord()){
                show_data_to_table_transaksi_record();
            }
        }
    }
    
}
