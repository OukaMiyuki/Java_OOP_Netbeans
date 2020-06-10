/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import Model.Pemasok;
import View.Choose_Pemasok;
import View.Main_Form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ouka
 */
public class Pemasok_Controller {
    private DefaultTableModel tb_pemasok;
    private DefaultTableModel tb_pilih_pemasok;
    private Koneksi conn = new Koneksi();
    private Main_Form frame;
    private Choose_Pemasok pilih_pemasok;
    private Pemasok pemasok;
    private int pos = 0;
    
    public Pemasok_Controller(Choose_Pemasok pilih_pemasok, Main_Form frame, Pemasok pemasok){
        this.pilih_pemasok = pilih_pemasok;
        this.frame = frame;
        this.pemasok = pemasok;
        this.tb_pemasok = (DefaultTableModel) frame.getTblPemasok().getModel();
        this.tb_pilih_pemasok = (DefaultTableModel) pilih_pemasok.getListTb_Pemasok().getModel();
        
        show_data_to_table();
        
        this.frame.getBtnInsert_Pemasok().addActionListener(new ManagementData());
        this.frame.getBtnUpdate_pemasok().addActionListener(new ManagementData());
        this.frame.getBtnDeletePemasok().addActionListener(new ManagementData());
        this.frame.getBtnResetPemasok().addActionListener(new ManagementData());
        
        this.frame.getBtn_Choose_Pemasok_produk().addActionListener(new ControlData());
        
        this.frame.getBtnFirst_Pemasok().addActionListener(new ControlData());
        this.frame.getBtnLast_Pemasok().addActionListener(new ControlData());
        this.frame.getBtnPrev_Pemasok().addActionListener(new ControlData());
        this.frame.getBtnNext_Pemasok().addActionListener(new ControlData());
        
        this.frame.getTblPemasok().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_pemasok_clicked(evt);
            }
        });
        
        this.pilih_pemasok.getListTb_Pemasok().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_list_clicked(evt);
            }
        });
    }
    
    public void tb_pemasok_clicked(java.awt.event.MouseEvent evt){
        this.frame.getTXT_id_pemasok().setEditable(false);
        this.frame.getBtnUpdate_pemasok().setEnabled(true);
        this.frame.getBtnDeletePemasok().setEnabled(true);
        this.frame.getBtnInsert_Pemasok().setEnabled(false);
        int index = this.frame.getTblPemasok().getSelectedRow();
        show_item_in_form(index);
    }
    
    public void show_item_in_form(int index){
        this.frame.getTXT_id_pemasok().setText(frame.getTblPemasok().getValueAt(index, 1).toString());
        this.frame.getTXT_nama_perusahaan_pemasok().setText(frame.getTblPemasok().getValueAt(index, 2).toString());
        this.frame.getTXT_alamat_pemasok().setText(frame.getTblPemasok().getValueAt(index, 3).toString());
        this.frame.getTXT_kode_pos_pemasok().setText(frame.getTblPemasok().getValueAt(index, 4).toString());
        this.frame.getTXT_no_telp_pemasok().setText(frame.getTblPemasok().getValueAt(index, 5).toString());
    }
    
    public void tb_list_clicked(java.awt.event.MouseEvent evt){
        int index = this.pilih_pemasok.getListTb_Pemasok().getSelectedRow();
        this.frame.getTXT_pilih_pemasok().setText(pilih_pemasok.getListTb_Pemasok().getValueAt(index, 1).toString());
        this.pilih_pemasok.dispose();
    }
    
    public ArrayList<Pemasok> getItemPemasokInDatabase(){
        ArrayList<Pemasok> pemArr = new ArrayList<Pemasok>();
        Connection con = conn.getConnection();
        String query = "SELECT * FROM pemasok ORDER BY id_pemasok ASC";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Pemasok pemasok;
            while(rs.next()){
                pemasok = new Pemasok();
                pemasok.setPemasok(rs.getInt("ID_PEMASOK"), rs.getString("NAMA_PERUSAHAAN"), rs.getString("ALAMAT"), rs.getInt("KODE_POS"), rs.getString("NO_TELP"));
                pemArr.add(pemasok);
            }
        } catch(SQLException ex){
            System.out.println("failed to show! " + ex);
        }
        return pemArr;
    }
    
    public void show_data_to_table(){
        ArrayList<Pemasok> pemArr = getItemPemasokInDatabase();
        tb_pemasok.setRowCount(0);
        tb_pilih_pemasok.setRowCount(0);
        Object[] kolom = new Object[6];
        Object[] kol_pemasok = new Object[3];
        for (int i = 0; i < pemArr.size(); i++) {
            kolom[0] = i+1;
            kolom[1] = pemArr.get(i).getId_pemasok();
            kolom[2] = pemArr.get(i).getNama_perusahaan();
            kolom[3] = pemArr.get(i).getAlamat();
            kolom[4] = pemArr.get(i).getKode_pos();
            kolom[5] = pemArr.get(i).getNo_telp();
            
            tb_pemasok.addRow(kolom);
        }
        
        for(int a=0; a<pemArr.size(); a++){
            kol_pemasok[0] = a+1;
            kol_pemasok[1] = pemArr.get(a).getId_pemasok();
            kol_pemasok[2] = pemArr.get(a).getNama_perusahaan();
            
            tb_pilih_pemasok.addRow(kol_pemasok);
        }
    }
    
    public void insert_data_pemasok(){
        try{
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO pemasok(ID_PEMASOK, NAMA_PERUSAHAAN, ALAMAT, KODE_POS, NO_TELP)"
                                                        +"VALUES(?,?,?,?,?)");
            ps.setInt(1, Integer.parseInt(this.frame.getTXT_id_pemasok().getText()));
            ps.setString(2, this.frame.getTXT_nama_perusahaan_pemasok().getText());
            ps.setString(3, this.frame.getTXT_alamat_pemasok().getText());
            ps.setString(4, this.frame.getTXT_kode_pos_pemasok().getText());
            ps.setString(5, this.frame.getTXT_no_telp_pemasok().getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil dimasukkan!");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "data gagal dimasukkan! "+e);
        }
    }
    
    public void update_data_pemasok(){
        String UpdateQuery = null;
        PreparedStatement ps = null;
        Connection con = conn.getConnection();
        
        try{
            UpdateQuery = "UPDATE pemasok SET NAMA_PERUSAHAAN = ?, ALAMAT = ?, KODE_POS = ?, NO_TELP = ? WHERE ID_PEMASOK = ?";
            ps = con.prepareStatement(UpdateQuery);
            ps.setString(1, this.frame.getTXT_nama_perusahaan_pemasok().getText());
            ps.setString(2, this.frame.getTXT_alamat_pemasok().getText());
            ps.setInt(3, Integer.parseInt(this.frame.getTXT_kode_pos_pemasok().getText()));
            ps.setString(4, this.frame.getTXT_no_telp_pemasok().getText());
            ps.setInt(5, Integer.parseInt(this.frame.getTXT_id_pemasok().getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated!");
        } catch(SQLException e){
            System.out.println("Update unsuccessfully! " +e);
        }
    }
    
    public void delete_data_pemasok(){
        try{
          Connection con = conn.getConnection();
          PreparedStatement ps = con.prepareStatement("DELETE FROM pemasok WHERE ID_PEMASOK = ?");
          ps.setInt(1, Integer.parseInt(this.frame.getTXT_id_pemasok().getText()));
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
            if(benda == frame.getBtnInsert_Pemasok()){
                if(frame.getTXT_id_pemasok().getText().isEmpty() || frame.getTXT_alamat_pemasok().getText().isEmpty() 
                        || frame.getTXT_kode_pos_pemasok().getText().isEmpty() || frame.getTXT_no_telp_pemasok().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    insert_data_pemasok();
                    show_data_to_table();
                    clear_form();
                }
            } else if(benda == frame.getBtnUpdate_pemasok()){
                if(frame.getTXT_id_pemasok().getText().isEmpty() || frame.getTXT_alamat_pemasok().getText().isEmpty() 
                        || frame.getTXT_kode_pos_pemasok().getText().isEmpty() || frame.getTXT_no_telp_pemasok().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    update_data_pemasok();
                    show_data_to_table();
                    clear_form();
                }
            } else if(benda == frame.getBtnDeletePemasok()){
                int result = JOptionPane.showConfirmDialog(null, 
                    "Delete data??",null, JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    delete_data_pemasok();
                    show_data_to_table();
                    clear_form();
                } else{
                    System.out.println("Abort!");
                }
            } else if(benda == frame.getBtnResetPemasok()){
                clear_form();
                show_data_to_table();
            }
        }
    }
    
    class ControlData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if(benda == frame.getBtnFirst_Pemasok()){
                pos = 0;
                show_item_in_form(pos);
                frame.getTblPemasok().setRowSelectionInterval(pos, pos);
                frame.getTXT_id_pemasok().setEditable(false);
                frame.getBtnUpdate_pemasok().setEnabled(true);
                frame.getBtnDeletePemasok().setEnabled(true);
                frame.getBtnInsertKurir().setEnabled(false);
            } else if(benda == frame.getBtnLast_Pemasok()){
                pos = frame.getTblPemasok().getRowCount()-1;
                show_item_in_form(pos);
                frame.getTblPemasok().setRowSelectionInterval(pos, pos);
                frame.getTXT_id_pemasok().setEditable(false);
                frame.getBtnUpdate_pemasok().setEnabled(true);
                frame.getBtnDeletePemasok().setEnabled(true);
                frame.getBtnInsert_Pemasok().setEnabled(false);
            } else if(benda == frame.getBtnNext_Pemasok()){
                if(frame.getTblPemasok().getSelectionModel().isSelectionEmpty()){
                    frame.getTblPemasok().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTblPemasok().getSelectedRow();
                    pos++;
                    if(pos >= frame.getTblPemasok().getRowCount()){
                        pos = frame.getTblPemasok().getRowCount()-1;
                    }
                    frame.getTblPemasok().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTXT_id_pemasok().setEditable(false);
                frame.getBtnUpdate_pemasok().setEnabled(true);
                frame.getBtnDeletePemasok().setEnabled(true);
                frame.getBtnInsert_Pemasok().setEnabled(false);
            } else if(benda == frame.getBtnPrev_Pemasok()){
                if(frame.getTblPemasok().getSelectionModel().isSelectionEmpty()){
                    frame.getTblPemasok().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTblPemasok().getSelectedRow();
                    pos--;
                    if(pos < 0){
                        pos = 0;
                    }
                    frame.getTblPemasok().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTXT_id_pemasok().setEditable(false);
                frame.getBtnUpdate_pemasok().setEnabled(true);
                frame.getBtnDeletePemasok().setEnabled(true);
                frame.getBtnInsert_Pemasok().setEnabled(false);
            } else if(benda == frame.getBtn_Choose_Pemasok_produk()){
                show_data_to_table();
                pilih_pemasok.setLocationRelativeTo(null);
                pilih_pemasok.setVisible(true);
            }
        }
        
    }
    
    public void clear_form(){
        this.frame.getTXT_id_pemasok().setText("");
        this.frame.getTXT_id_pemasok().setEditable(true);
        this.frame.getTXT_nama_perusahaan_pemasok().setText("");
        this.frame.getTXT_alamat_pemasok().setText("");
        this.frame.getTXT_kode_pos_pemasok().setText("");
        this.frame.getTXT_no_telp_pemasok().setText("");
        this.frame.getNo_Telp_Pegawai().setText("");
        this.frame.getBtnInsert_Pemasok().setEnabled(true);
        this.frame.getBtnDeletePemasok().setEnabled(false);
        this.frame.getBtnUpdate_pemasok().setEnabled(false);
        this.frame.getTblPemasok().getSelectionModel().clearSelection();
    }
}