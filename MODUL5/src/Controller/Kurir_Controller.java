/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import Model.Kurir;
import View.Choose_Kurir;
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
public class Kurir_Controller {
    private Main_Form frame;
    Choose_Kurir pilih_kurir = new Choose_Kurir();
    private Kurir kurir;
    private DefaultTableModel tbl_kurir;
    private DefaultTableModel tbl_kurir_list;
    private Koneksi conn = new Koneksi();
    public static int pos = 0;
    
    public Kurir_Controller(Main_Form frame, Kurir kurir, Choose_Kurir pilih_kuri){
        this.frame = frame;
        this.kurir = kurir;
        this.pilih_kurir = pilih_kuri;
        this.tbl_kurir = (DefaultTableModel) this.frame.getTblKurir().getModel();
        this.tbl_kurir_list = (DefaultTableModel) this.pilih_kurir.getTbl_kurir_list().getModel();
        show_data_to_table();
        this.frame.getBtnInsertKurir().addActionListener(new ManagementData());
        this.frame.getBtnUpdateKurir().addActionListener(new ManagementData());
        this.frame.getBtnDeleteKurir().addActionListener(new ManagementData());
        this.frame.getBtnResetButtonKurir().addActionListener(new ManagementData());
        
        this.frame.getBtnChooseKurir().addActionListener(new ControlData());
        
        this.frame.getFirst_kurir().addActionListener(new ControlData());
        this.frame.getLast_kurir().addActionListener(new ControlData());
        this.frame.getNext_kurir().addActionListener(new ControlData());
        this.frame.getPrev_kurir().addActionListener(new ControlData());
        
        this.frame.getTblKurir().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_kurir_clicked(evt);
            }
        });
        
        this.pilih_kurir.getTbl_kurir_list().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_kurir_list_clicked(evt);
            }
        });
    }
    
    public void tb_kurir_clicked(java.awt.event.MouseEvent evt){
        this.frame.getTXTId_Kurir().setEditable(false);
        this.frame.getBtnInsertKurir().setEnabled(false);
        this.frame.getBtnUpdateKurir().setEnabled(true);
        this.frame.getBtnDeleteKurir().setEnabled(true);
        int index = this.frame.getTblKurir().getSelectedRow();
        show_item_in_forms(index);
    }
    
    public void show_item_in_forms(int index){
        this.frame.getTXTId_Kurir().setText(this.frame.getTblKurir().getValueAt(index, 1).toString());
        this.frame.getTXTNama_Perusahaan().setText(this.frame.getTblKurir().getValueAt(index, 2).toString());
        this.frame.getTXTNo_Telp_Kurir().setText(this.frame.getTblKurir().getValueAt(index, 3).toString());
    }
    
    public void tb_kurir_list_clicked(java.awt.event.MouseEvent evt){
        int index = this.pilih_kurir.getTbl_kurir_list().getSelectedRow();
        this.frame.getTXT_id_kurir_transaksi().setText(pilih_kurir.getTbl_kurir_list().getValueAt(index, 1).toString());
        this.pilih_kurir.dispose();
    }
    
    public ArrayList<Kurir> getItemKuririnDatabase(){
        ArrayList<Kurir> kurArr = new ArrayList<Kurir>();
        Connection con = conn.getConnection();
        String query = "SELECT * FROM kurir ORDER BY id_kurir ASC";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Kurir kurir;
            while(rs.next()){
                kurir = new Kurir();
                kurir.setKurir(rs.getInt("ID_KURIR"), rs.getString("NAMA_PERUSAHAAN"), rs.getString("NO_TELP"));
                kurArr.add(kurir);
            }
        } catch(SQLException e){
            System.out.print("Error oi! "+e);
        }
        return kurArr;
    }
    
    public void show_data_to_table(){
        ArrayList<Kurir> kurArr = getItemKuririnDatabase();
        tbl_kurir.setRowCount(0);
        tbl_kurir_list.setRowCount(0);
        Object[] kolom = new Object[4];
        Object[] kol = new Object[3];
        for (int i = 0; i < kurArr.size(); i++) {
            kolom[0] = i+1;
            kolom[1] = kurArr.get(i).getId_kurir();
            kolom[2] = kurArr.get(i).getNama_perusahaan();
            kolom[3] = kurArr.get(i).getNo_telp();
             
            tbl_kurir.addRow(kolom);
        }
        
        for(int a=0; a<kurArr.size(); a++){
            kol[0] = a+1;
            kol[1] = kurArr.get(a).getId_kurir();
            kol[2] = kurArr.get(a).getNama_perusahaan();
            
            tbl_kurir_list.addRow(kol);
        }
    }
    
    public void insert_data_kurir(){
        try{
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO kurir (ID_KURIR, NAMA_PERUSAHAAN, NO_TELP)"
                                                        +"VALUES(?, ?, ?)");
            ps.setInt(1, Integer.parseInt(this.frame.getTXTId_Kurir().getText()));
            ps.setString(2, this.frame.getTXTNama_Perusahaan().getText());
            ps.setString(3, this.frame.getTXTNo_Telp_Kurir().getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Successfully Inserted!");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "data gagal dimasukkan! "+e);
        }
    }
    
    public void update_data_kurir(){
        String UpdateQuery = null;
        PreparedStatement ps = null;
        Connection con = conn.getConnection();
        try{
            UpdateQuery = "UPDATE kurir SET nama_perusahaan = ?, no_telp = ? WHERE id_kurir = ?";
            ps = con.prepareStatement(UpdateQuery);
            ps.setString(1, this.frame.getTXTNama_Perusahaan().getText());
            ps.setString(2, this.frame.getTXTNo_Telp_Kurir().getText());
            ps.setInt(3, Integer.parseInt(this.frame.getTXTId_Kurir().getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated!");
        } catch(SQLException e){
            System.out.print("Data unsuccessfully updated!" + e);
        }
    }
    
    public void delete_data_kurir(){
        try{
          Connection con = conn.getConnection();
          PreparedStatement ps = con.prepareStatement("DELETE FROM kurir WHERE ID_KURIR = ?");
          ps.setInt(1, Integer.parseInt(this.frame.getTXTId_Kurir().getText()));
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
            if(benda == frame.getBtnInsertKurir()){
                if(frame.getTXTId_Kurir().getText().isEmpty() || frame.getTXTNama_Perusahaan().getText().isEmpty() || 
                        frame.getTXTNo_Telp_Kurir().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    insert_data_kurir();
                    show_data_to_table();
                    clear_forms();
                }
            } else if(benda == frame.getBtnUpdateKurir()){
                if(frame.getTXTId_Kurir().getText().isEmpty() || frame.getTXTNama_Perusahaan().getText().isEmpty() || 
                        frame.getTXTNo_Telp_Kurir().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    update_data_kurir();
                    show_data_to_table();
                    clear_forms();
                }
            } else if(benda == frame.getBtnDeleteKurir()){
                int result = JOptionPane.showConfirmDialog(null, 
                    "Delete data??",null, JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    delete_data_kurir();
                    show_data_to_table();
                    clear_forms();
                } else{
                    System.out.println("Abort!");
                }
            } else if(benda == frame.getBtnResetButtonKurir()){
                clear_forms();
                show_data_to_table();
            }
        }
    }
    
    class ControlData implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if (benda == frame.getFirst_kurir()){
                pos = 0;
                show_item_in_forms(pos);
                frame.getTblKurir().setRowSelectionInterval(pos, pos);
                frame.getTXTId_Kurir().setEditable(false);
                frame.getBtnUpdateKurir().setEnabled(true);
                frame.getBtnDeleteKurir().setEnabled(true);
                frame.getBtnInsertKurir().setEnabled(false);
            } else if(benda == frame.getLast_kurir()){
                pos = frame.getTblKurir().getRowCount()-1;
                show_item_in_forms(pos);
                 frame.getTblKurir().setRowSelectionInterval(pos, pos);
                frame.getTXTId_Kurir().setEditable(false);
                frame.getBtnUpdateKurir().setEnabled(true);
                frame.getBtnDeleteKurir().setEnabled(true);
                frame.getBtnInsertKurir().setEnabled(false);
            } else if(benda == frame.getNext_kurir()){
                if(frame.getTblKurir().getSelectionModel().isSelectionEmpty()){
                    frame.getTblKurir().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTblKurir().getSelectedRow();
                    pos++;
                    if(pos >= frame.getTblKurir().getRowCount()){
                        pos = frame.getTblKurir().getRowCount()-1;
                    }
                    frame.getTblKurir().setRowSelectionInterval(pos, pos);
                }
                show_item_in_forms(pos);
                frame.getTXTId_Kurir().setEditable(false);
                frame.getBtnUpdateKurir().setEnabled(true);
                frame.getBtnDeleteKurir().setEnabled(true);
                frame.getBtnInsertKurir().setEnabled(false);
            } else if(benda == frame.getPrev_kurir()){
                if(frame.getTblKurir().getSelectionModel().isSelectionEmpty()){
                    frame.getTblKurir().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTblKurir().getSelectedRow();
                    pos--;
                    if(pos < 0){
                        pos = 0;
                    }
                    frame.getTblKurir().setRowSelectionInterval(pos, pos);
                }
                show_item_in_forms(pos);
                frame.getTXTId_Kurir().setEditable(false);
                frame.getBtnUpdateKurir().setEnabled(true);
                frame.getBtnDeleteKurir().setEnabled(true);
                frame.getBtnInsertKurir().setEnabled(false);
            } else if(benda == frame.getBtnChooseKurir()){
                show_data_to_table();
                pilih_kurir.setLocationRelativeTo(null);
                pilih_kurir.setVisible(true);
            }
        }
    
    }
    
    public void clear_forms(){
        this.frame.getTXTId_Kurir().setText("");
        this.frame.getTXTId_Kurir().setEditable(true);
        this.frame.getTXTNama_Perusahaan().setText("");
        this.frame.getTXTNo_Telp_Kurir().setText("");
        this.frame.getBtnInsertKurir().setEnabled(true);
        this.frame.getBtnUpdateKurir().setEnabled(false);
        this.frame.getBtnDeleteKurir().setEnabled(false);
        this.frame.getTblPemasok().getSelectionModel().clearSelection();
    }
}