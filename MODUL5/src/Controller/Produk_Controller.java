/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import Model.Produk;
import View.Choose_Produk;
import View.Main_Form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ouka
 */
public class Produk_Controller {
    private DefaultTableModel tbl_produk;
    private DefaultTableModel tbl_produk_list;
    private Koneksi conn = new Koneksi();
    private Main_Form frame;
    private Choose_Produk pilih_produk;
    private Produk produk;
    public static int pos = 0;
    
    public Produk_Controller(Main_Form frame, Produk produk, Choose_Produk pilih_produk){
        this.frame = frame;
        this.produk = produk;
        this.pilih_produk = pilih_produk;
        this.tbl_produk = (DefaultTableModel) frame.getTbl_produk().getModel();
        this.tbl_produk_list = (DefaultTableModel) pilih_produk.getTblProduk().getModel();
        show_data_to_table();
        
        this.frame.getTbl_produk().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_produk_clicked(evt);
            }
        });
        
        this.pilih_produk.getTblProduk().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_produk_list_clicked(evt);
            }
        });
        
        this.frame.getBtn_insert_produk().addActionListener(new ManagementData());
        this.frame.getBtn_update_produk().addActionListener(new ManagementData());
        this.frame.getBtn_delete_produk().addActionListener(new ManagementData());
        this.frame.getBtn_reset_produk().addActionListener(new ManagementData());
        
        this.frame.geBtnChooseProduk().addActionListener(new ControlData());
        
        this.frame.getBtn_first_produk().addActionListener(new ControlData());
        this.frame.getBtn_last_produk().addActionListener(new ControlData());
        this.frame.getBtn_prev_produk().addActionListener(new ControlData());
        this.frame.getBtn_next_produk().addActionListener(new ControlData());
    }
    
    public void tb_produk_clicked(java.awt.event.MouseEvent evt){
        this.frame.getTXT_produk_id().setEditable(false);
        this.frame.getBtn_update_produk().setEnabled(true);
        this.frame.getBtn_delete_produk().setEnabled(true);
        this.frame.getBtn_insert_produk().setEnabled(false);
        int index = this.frame.getTbl_produk().getSelectedRow();
        show_item_in_form(index);
    }
    
    public void tb_produk_list_clicked(java.awt.event.MouseEvent evt){
        int index = this.pilih_produk.getTblProduk().getSelectedRow();
        this.frame.getProduk_Transaksi().setText(pilih_produk.getTblProduk().getValueAt(index, 1).toString());
        this.frame.setHarga(Integer.parseInt(pilih_produk.getTblProduk().getValueAt(index, 3).toString()));
        this.frame.setNamaProduk(pilih_produk.getTblProduk().getValueAt(index, 2).toString());
        this.frame.setStok(Integer.parseInt(pilih_produk.getTblProduk().getValueAt(index, 4).toString()));
        this.pilih_produk.dispose();
    }
    
    public void show_item_in_form(int index){
        this.frame.getTXT_produk_id().setText(frame.getTbl_produk().getValueAt(index, 1).toString());
        this.frame.getTXT_pilih_pemasok().setText(frame.getTbl_produk().getValueAt(index, 2).toString());
        this.frame.getTXT_pilih_kategori().setText(frame.getTbl_produk().getValueAt(index, 3).toString());
        this.frame.getTXT_nama_produk().setText(frame.getTbl_produk().getValueAt(index, 4).toString());
        this.frame.getTXT_harga_produk().setText(frame.getTbl_produk().getValueAt(index, 5).toString());
        this.frame.getTXT_stok_produk().setText(frame.getTbl_produk().getValueAt(index, 6).toString());
    }
    
    public ArrayList<Produk> getItemProdukIndatabase(){
        ArrayList<Produk> proArr = new ArrayList<Produk>();
        Connection con = conn.getConnection();
        String query = "SELECT * FROM produk ORDER BY id_produk ASC";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Produk produk;
            while(rs.next()){
                produk = new Produk();
                produk.setProduk(rs.getInt("ID_PRODUK"), rs.getInt("ID_PEMASOK"), rs.getInt("ID_KATEGORI"), rs.getString("NAMA_PRODUK"), rs.getInt("HARGA_SATUAN"), rs.getInt("STOK_PRODUK"));
                proArr.add(produk);
            }
        } catch(SQLException ex){
            System.out.println("failed to show! " + ex);
        }
        return proArr;
    }
    
    public void show_data_to_table(){
        ArrayList<Produk> proArr = getItemProdukIndatabase();
        tbl_produk.setRowCount(0);
        tbl_produk_list.setRowCount(pos);
        Object[] kolom = new Object[7];
        Object[] kol = new Object[5];
        for (int i = 0; i < proArr.size(); i++) {
            kolom[0] = i+1;
            kolom[1] = proArr.get(i).getId_produk();
            kolom[2] = proArr.get(i).getId_pemasok();
            kolom[3] = proArr.get(i).getId_kategori();
            kolom[4] = proArr.get(i).getNama_produk();
            kolom[5] = proArr.get(i).getHarga_satuan();
            kolom[6] = proArr.get(i).getStok_produk();
             
            tbl_produk.addRow(kolom);
        }
        
         for(int a=0; a<proArr.size(); a++){
            kol[0] = a+1;
            kol[1] = proArr.get(a).getId_produk();
            kol[2] = proArr.get(a).getNama_produk();
            kol[3] = proArr.get(a).getHarga_satuan();
            kol[4] = proArr.get(a).getStok_produk();
            
            tbl_produk_list.addRow(kol);
        }
    }
    
    public void insert_data_produki(){
        try{
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO produk(ID_PRODUK, ID_PEMASOK, ID_KATEGORI, NAMA_PRODUK, HARGA_SATUAN, STOK_PRODUK)"
                                                        +"VALUES(?,?,?,?,?,?)");
            ps.setInt(1, Integer.parseInt(this.frame.getTXT_produk_id().getText()));
            ps.setInt(2, Integer.parseInt(this.frame.getTXT_pilih_pemasok().getText()));
            ps.setInt(3, Integer.parseInt(this.frame.getTXT_pilih_kategori().getText()));
            ps.setString(4, this.frame.getTXT_nama_produk().getText());
            ps.setInt(5, Integer.parseInt(this.frame.getTXT_harga_produk().getText()));
            ps.setInt(6, Integer.parseInt(this.frame.getTXT_stok_produk().getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil dimasukkan!");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "data gagal dimasukkan! "+e);
        }
    }
    
    public void insert(){
        int harga, stok_barang;
        try{
            harga = Integer.parseInt(frame.getTXT_harga_produk().getText());
            stok_barang = Integer.parseInt(frame.getTXT_stok_produk().getText());
            if(frame.getTXT_produk_id().getText().isEmpty() || frame.getTXT_pilih_pemasok().getText().isEmpty() || 
                    frame.getTXT_pilih_kategori().getText().isEmpty() || frame.getTXT_nama_produk().getText().isEmpty() ||
                    frame.getTXT_harga_produk().getText().isEmpty() || frame.getTXT_stok_produk().getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "One or more fields are empty!");
            } else{
                insert_data_produki();
            }
        } catch(NumberFormatException   e){
            JOptionPane.showMessageDialog(null, "Masukkan angka! "+e);
        }
    }
    
    public void update_data_produk(){
        String UpdateQuery = null;
        PreparedStatement ps = null;
        Connection con = conn.getConnection();
        try{
            UpdateQuery = "UPDATE produk SET id_pemasok = ?, id_kategori = ?, nama_produk = ?, harga_satuan = ?"
                        +", stok_produk = ? WHERE id_produk = ?";
            ps = con.prepareStatement(UpdateQuery);
            ps.setInt(1, Integer.parseInt(this.frame.getTXT_pilih_pemasok().getText()));
            ps.setInt(2, Integer.parseInt(this.frame.getTXT_pilih_kategori().getText()));
            ps.setString(3, this.frame.getTXT_nama_produk().getText());
            ps.setInt(4, Integer.parseInt(this.frame.getTXT_harga_produk().getText()));
            ps.setInt(5, Integer.parseInt(this.frame.getTXT_stok_produk().getText()));
            ps.setInt(6, Integer.parseInt(this.frame.getTXT_produk_id().getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated!");
        } catch(SQLException e){
            System.out.println("Update unsuccessfully! " +e);
        }
    }
    
    public void update(){
        int harga, stok_barang;
        try{
            harga = Integer.parseInt(frame.getTXT_harga_produk().getText());
            stok_barang = Integer.parseInt(frame.getTXT_stok_produk().getText());
            if(frame.getTXT_produk_id().getText().isEmpty() || frame.getTXT_pilih_pemasok().getText().isEmpty() || 
                    frame.getTXT_pilih_kategori().getText().isEmpty() || frame.getTXT_nama_produk().getText().isEmpty() ||
                    frame.getTXT_harga_produk().getText().isEmpty() || frame.getTXT_stok_produk().getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "One or more fields are empty!");
            } else{
                update_data_produk();
            }
        } catch(NumberFormatException   e){
            JOptionPane.showMessageDialog(null, "Masukkan angka! "+e);
        }
    }
    
    public void delete_data_produk(){
        try{
          Connection con = conn.getConnection();
          PreparedStatement ps = con.prepareStatement("DELETE FROM produk WHERE id_produk = ?");
          ps.setInt(1, Integer.parseInt(this.frame.getTXT_produk_id().getText()));
          ps.executeUpdate();
          JOptionPane.showMessageDialog(null, "Data Deleted Successfully");
        } catch(SQLException e){
            System.out.println("Unsuccessfully Deleted! "+e);
        }
    }
    
    class ManagementData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if (benda == frame.getBtn_insert_produk()){
                insert();
                show_data_to_table();
                clear_form();
            } else if(benda == frame.getBtn_update_produk()){
                update();
                show_data_to_table();
                clear_form();
            } else if(benda == frame.getBtn_delete_produk()){
                int result = JOptionPane.showConfirmDialog(null, 
                    "Delete data??",null, JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    delete_data_produk();
                    show_data_to_table();
                    clear_form();
                } else{
                    System.out.println("Abort!");
                }
            } else if(benda == frame.getBtn_reset_produk()){
                clear_form();
                show_data_to_table();
            }
        }
    
    }
    
    class ControlData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if (benda == frame.getBtn_first_produk()){
                pos = 0;
                show_item_in_form(pos);
                frame.getTbl_produk().setRowSelectionInterval(pos, pos);
                frame.getTXT_produk_id().setEditable(false);
                frame.getBtn_update_produk().setEnabled(true);
                frame.getBtn_delete_produk().setEnabled(true);
                frame.getBtn_insert_produk().setEnabled(false);
            } else if(benda == frame.getBtn_last_produk()){
                pos = frame.getTbl_produk().getRowCount()-1;
                show_item_in_form(pos);
                frame.getTbl_produk().setRowSelectionInterval(pos, pos);
                frame.getTXT_produk_id().setEditable(false);
                frame.getBtn_update_produk().setEnabled(true);
                frame.getBtn_delete_produk().setEnabled(true);
                frame.getBtn_insert_produk().setEnabled(false);
            } else if(benda == frame.getBtn_next_produk()){
                if(frame.getTbl_produk().getSelectionModel().isSelectionEmpty()){
                    frame.getTbl_produk().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTbl_produk().getSelectedRow();
                    pos++;
                    if(pos >= frame.getTbl_produk().getRowCount()){
                        pos = frame.getTbl_produk().getRowCount()-1;
                    }
                    frame.getTbl_produk().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTXT_produk_id().setEditable(false);
                frame.getBtn_update_produk().setEnabled(true);
                frame.getBtn_delete_produk().setEnabled(true);
                frame.getBtn_insert_produk().setEnabled(false);
            } else if(benda == frame.getBtn_prev_produk()){
                if(frame.getTbl_produk().getSelectionModel().isSelectionEmpty()){
                    frame.getTbl_produk().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTbl_produk().getSelectedRow();
                    pos--;
                    if(pos < 0){
                        pos = 0;
                    }
                    frame.getTbl_produk().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTXT_produk_id().setEditable(false);
                frame.getBtn_update_produk().setEnabled(true);
                frame.getBtn_delete_produk().setEnabled(true);
                frame.getBtn_insert_produk().setEnabled(false);
            } else if(benda == frame.geBtnChooseProduk()){
                show_data_to_table();
                pilih_produk.setLocationRelativeTo(null);
                pilih_produk.setVisible(true);
            }
        }
        
    }
    
    public void clear_form(){
        this.frame.getTXT_produk_id().setText("");
        this.frame.getTXT_produk_id().setEditable(true);
        this.frame.getTXT_pilih_pemasok().setText("");
        this.frame.getTXT_pilih_kategori().setText("");
        this.frame.getTXT_nama_produk().setText("");
        this.frame.getTXT_harga_produk().setText("");
        this.frame.getTXT_stok_produk().setText("");
        this.frame.getBtn_insert_produk().setEnabled(true);
        this.frame.getBtn_delete_produk().setEnabled(false);
        this.frame.getBtn_update_produk().setEnabled(false);
        this.frame.getTbl_produk().getSelectionModel().clearSelection();
    }
}
