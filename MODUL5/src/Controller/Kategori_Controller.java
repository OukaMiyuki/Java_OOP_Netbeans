/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import Model.Kategori;
import View.Main_Form;
import View.Choose_Kategori;
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
public class Kategori_Controller {
    private DefaultTableModel tbl_kategori;
    private DefaultTableModel tbl_kategori_choose;
    private Koneksi conn = new Koneksi();
    private Main_Form frame;
    private Choose_Kategori pilih_kategori;
    private Kategori kategori;
    public static int pos = 0;
    
    public Kategori_Controller(Choose_Kategori pilih_kategori, Main_Form frame, Kategori kategori){
        this.pilih_kategori = pilih_kategori;
        this.frame = frame;
        this.kategori = kategori;
        this.tbl_kategori = (DefaultTableModel) frame.getTbl_Kategori().getModel();
        this.tbl_kategori_choose = (DefaultTableModel) pilih_kategori.getTbl_kategori_choose().getModel();
        show_data_to_table();
        
        this.frame.getTbl_Kategori().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_kategori_clicked(evt);
            }
        });
        
        this.pilih_kategori.getTbl_kategori_choose().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_kategori_choose_clicked(evt);
            }
        });
        
        this.frame.getBtn_Insert_kategori().addActionListener(new ManagementData());
        this.frame.getBtn_Update_kategori().addActionListener(new ManagementData());
        this.frame.getBtn_Delete_kategori().addActionListener(new ManagementData());
        this.frame.getBtn_Reset_kategori().addActionListener(new ManagementData());
        
        this.frame.getBtn_Choose_kategori_Produk().addActionListener(new ControlData());
        
        this.frame.getBtn_First_kategori().addActionListener(new ControlData());
        this.frame.getBtn_Last_kategori().addActionListener(new ControlData());
        this.frame.getBtn_Next_kategori().addActionListener(new ControlData());
        this.frame.getBtn_Prev_kategori().addActionListener(new ControlData());
    }
    
    public void tb_kategori_clicked(java.awt.event.MouseEvent evt){
        this.frame.getTXT_kategori_id().setEditable(false);
        this.frame.getBtn_Update_kategori().setEnabled(true);
        this.frame.getBtn_Delete_kategori().setEnabled(true);
        this.frame.getBtn_Insert_kategori().setEnabled(false);
        int index = this.frame.getTbl_Kategori().getSelectedRow();
        show_item_in_form(index);
    }
    
    public void tb_kategori_choose_clicked(java.awt.event.MouseEvent evt){
        int index = pilih_kategori.getTbl_kategori_choose().getSelectedRow();
        this.frame.getTXT_pilih_kategori().setText((pilih_kategori.getTbl_kategori_choose().getValueAt(index, 1).toString()));
        this.pilih_kategori.dispose();
    }
    
    public void show_item_in_form(int index){
        this.frame.getTXT_kategori_id().setText(frame.getTbl_Kategori().getValueAt(index, 1).toString());
        this.frame.getTXT_nama_kategori().setText(frame.getTbl_Kategori().getValueAt(index, 2).toString());
    }
    
    public ArrayList<Kategori> getItemKategoriIndatabase(){
        ArrayList<Kategori> katArr = new ArrayList<Kategori>();
        Connection con = conn.getConnection();
        String query = "SELECT * FROM kategori ORDER BY id_kategori ASC";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Kategori kategori;
            while(rs.next()){
                kategori = new Kategori();
                kategori.setKategori(rs.getInt("ID_KATEGORI"), rs.getString("NAMA_KATEGORI"));
                katArr.add(kategori);
            }
        } catch(SQLException ex){
            System.out.println("failed to show! " + ex);
        }
        return katArr;
    }
    
    public void show_data_to_table(){
        ArrayList<Kategori> katArr = getItemKategoriIndatabase();
        tbl_kategori.setRowCount(0);
        tbl_kategori_choose.setRowCount(0);
        Object[] kolom = new Object[3];
        for (int i = 0; i < katArr.size(); i++) {
            kolom[0] = i+1;
            kolom[1] = katArr.get(i).getId_kategori();
            kolom[2] = katArr.get(i).getNama_kategori();
             
            tbl_kategori.addRow(kolom);
            tbl_kategori_choose.addRow(kolom);
         }
    }
    
    public void insert_data_kategori(){
        try{
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO kategori(ID_KATEGORI, NAMA_KATEGORI)"
                                                        +"VALUES(?,?)");
            ps.setInt(1, Integer.parseInt(this.frame.getTXT_kategori_id().getText()));
            ps.setString(2, this.frame.getTXT_nama_kategori().getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil dimasukkan!");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "data gagal dimasukkan! "+e);
        }
    }
    
    public void update_data_kategori(){
        String UpdateQuery = null;
        PreparedStatement ps = null;
        Connection con = conn.getConnection();
        try{
            UpdateQuery = "UPDATE kategori SET nama_kategori = ? WHERE id_kategori = ?";
            ps = con.prepareStatement(UpdateQuery);
            ps.setString(1, this.frame.getTXT_nama_kategori().getText());
            ps.setInt(2, Integer.parseInt(this.frame.getTXT_kategori_id().getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated!");
        } catch(SQLException e){
            System.out.println("Update unsuccessfully! " +e);
        }
    }
    
    public void delete_data_kategori(){
        try{
          Connection con = conn.getConnection();
          PreparedStatement ps = con.prepareStatement("DELETE FROM kategori WHERE ID_KATEGORI = ?");
          ps.setInt(1, Integer.parseInt(this.frame.getTXT_kategori_id().getText()));
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
            if(benda == frame.getBtn_Insert_kategori()){
                if(frame.getTXT_kategori_id().getText().isEmpty() || frame.getTXT_nama_kategori().getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    insert_data_kategori();
                    show_data_to_table();
                    clear_form();
                }
            } else if(benda == frame.getBtn_Update_kategori()){
                if(frame.getTXT_kategori_id().getText().isEmpty() || frame.getTXT_nama_kategori().getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    update_data_kategori();
                    show_data_to_table();
                    clear_form();
                }
            } else if(benda == frame.getBtn_Delete_kategori()){
                int result = JOptionPane.showConfirmDialog(null, 
                    "Delete data??",null, JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    delete_data_kategori();
                    show_data_to_table();
                    clear_form();
                } else{
                    System.out.println("Abort!");
                }
            } else if(benda == frame.getBtn_Reset_kategori()){
                clear_form();
                show_data_to_table();
            }
        }
    
    }
    
    class ControlData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if(benda == frame.getBtn_First_kategori()){
                pos = 0;
                show_item_in_form(pos);
                frame.getTbl_Kategori().setRowSelectionInterval(pos, pos);
                frame.getTXT_kategori_id().setEditable(false);
                frame.getBtn_Update_kategori().setEnabled(true);
                frame.getBtn_Delete_kategori().setEnabled(true);
                frame.getBtn_Insert_kategori().setEnabled(false);
            } else if(benda == frame.getBtn_Last_kategori()){
                pos = frame.getTbl_Kategori().getRowCount()-1;
                show_item_in_form(pos);
                frame.getTbl_Kategori().setRowSelectionInterval(pos, pos);
                frame.getTXT_kategori_id().setEditable(false);
                frame.getBtn_Update_kategori().setEnabled(true);
                frame.getBtn_Delete_kategori().setEnabled(true);
                frame.getBtn_Insert_kategori().setEnabled(false);
            } else if(benda == frame.getBtn_Next_kategori()){
                if(frame.getTbl_Kategori().getSelectionModel().isSelectionEmpty()){
                    frame.getTbl_Kategori().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTbl_Kategori().getSelectedRow();
                    pos++;
                    if(pos >= frame.getTbl_Kategori().getRowCount()){
                        pos = frame.getTbl_Kategori().getRowCount()-1;
                    }
                    frame.getTbl_Kategori().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTXT_kategori_id().setEditable(false);
                frame.getBtn_Update_kategori().setEnabled(true);
                frame.getBtn_Delete_kategori().setEnabled(true);
                frame.getBtn_Insert_kategori().setEnabled(false);
            } else if(benda == frame.getBtn_Prev_kategori()){
                if(frame.getTbl_Kategori().getSelectionModel().isSelectionEmpty()){
                    frame.getTbl_Kategori().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTbl_Kategori().getSelectedRow();
                    pos--;
                    if(pos < 0){
                        pos = 0;
                    }
                    frame.getTbl_Kategori().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTXT_kategori_id().setEditable(false);
                frame.getBtn_Update_kategori().setEnabled(true);
                frame.getBtn_Delete_kategori().setEnabled(true);
                frame.getBtn_Insert_kategori().setEnabled(false);
            } else if(benda == frame.getBtn_Choose_kategori_Produk()){
                pilih_kategori.setLocationRelativeTo(null);
                pilih_kategori.setVisible(true);
            }
        }
    
    }
    
    public void clear_form(){
        this.frame.getTXT_kategori_id().setText("");
        this.frame.getTXT_kategori_id().setEditable(true);
        this.frame.getTXT_nama_kategori().setText("");
        this.frame.getBtn_Insert_kategori().setEnabled(true);
        this.frame.getBtn_Delete_kategori().setEnabled(false);
        this.frame.getBtn_Update_kategori().setEnabled(false);
        this.frame.getTbl_Kategori().getSelectionModel().clearSelection();
    }
}
