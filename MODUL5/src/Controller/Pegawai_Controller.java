/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Koneksi;
import java.sql.Connection;
import Model.Pegawai;
import View.Main_Form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ouka
 */
public class Pegawai_Controller {
    private DefaultTableModel tbl_pegawai;
    private Koneksi conn = new Koneksi();
    private Main_Form frame;
    private Pegawai pegawai;
    public static int pos = 0;
    
    public Pegawai_Controller(Main_Form frame, Pegawai pegawai){
        this.frame = frame;
        this.pegawai = pegawai;
        this.tbl_pegawai = (DefaultTableModel) this.frame.getTblViewPegawai().getModel();
        show_data_to_table();
        this.frame.getTblViewPegawai().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                tb_pegawai_clicked(evt);
            }
        });
        this.frame.getInsertButton_Pegawai().addActionListener(new ManagementData());
        this.frame.getUpdateButton_Pegawai().addActionListener(new ManagementData());
        this.frame.getResetFieldPegawai().addActionListener(new ManagementData());
        this.frame.getBtnDeletePegawai().addActionListener(new ManagementData());
        
        this.frame.getFirstControl_Pegawai().addActionListener(new ControlData());
        this.frame.getNextControl_Pegawai().addActionListener(new ControlData());
        this.frame.getPrevControl_Pegawai().addActionListener(new ControlData());
        this.frame.getLastControl_Pegawai().addActionListener(new ControlData());
    }
    
    public void tb_pegawai_clicked(java.awt.event.MouseEvent evt){
        this.frame.getTxt_ID_pegawai().setEditable(false);
        this.frame.getUpdateButton_Pegawai().setEnabled(true);
        this.frame.getBtnDeletePegawai().setEnabled(true);
        this.frame.getInsertButton_Pegawai().setEnabled(false);
        int index = this.frame.getTblViewPegawai().getSelectedRow();
        show_item_in_form(index);
    }
    
    public void show_item_in_form(int index){
        this.frame.getTxt_ID_pegawai().setText(frame.getTblViewPegawai().getValueAt(index, 1).toString());
        this.frame.getNama_Depan_Pegawai().setText(frame.getTblViewPegawai().getValueAt(index, 2).toString());
        this.frame.getNama_Belakang_Pegawai().setText(frame.getTblViewPegawai().getValueAt(index, 3).toString());
        try{
            java.util.Date lahirPegawai = new SimpleDateFormat("dd-MM-yyyy").parse(frame.getTblViewPegawai().getValueAt(index, 4).toString());
            this.frame.getlahir_Pegawai().setDate(lahirPegawai);
        } catch(ParseException ex){
            System.out.println("Parse Failed for date "+ ex);
        }
        this.frame.getAlamat_Pegawai().setText(frame.getTblViewPegawai().getValueAt(index, 4).toString());
        this.frame.getKode_Pos_Pegawai().setText(frame.getTblViewPegawai().getValueAt(index, 6).toString());
        this.frame.getNo_Telp_Pegawai().setText(frame.getTblViewPegawai().getValueAt(index, 7).toString());
    }
    
    public ArrayList<Pegawai> getItemIndatabase(){
        ArrayList<Pegawai> pegArr = new ArrayList<Pegawai>();
        Connection con = conn.getConnection();
        String query = "SELECT * FROM pegawai ORDER BY id_pegawai ASC";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Pegawai pegawai;
            while(rs.next()){
                pegawai = new Pegawai();
                pegawai.setPegawai(rs.getInt("ID_PEGAWAI"), rs.getString("NAMA_DEPAN"), rs.getString("NAMA_BELAKANG"), rs.getString("TANGGAL_LAHIR"), rs.getString("ALAMAT"), rs.getInt("KODE_POS"), rs.getString("NO_TELP"));
                pegArr.add(pegawai);
            }
        } catch(SQLException ex){
            System.out.println("failed to show! " + ex);
        }
        return pegArr;
    }
    
    public void show_data_to_table(){
        ArrayList<Pegawai> pegArr = getItemIndatabase();
        tbl_pegawai.setRowCount(0);
        Object[] kolom = new Object[8];
        for (int i = 0; i < pegArr.size(); i++) {
            kolom[0] = i+1;
            kolom[1] = pegArr.get(i).getId_pegawai();
            kolom[2] = pegArr.get(i).getNama_depan();
            kolom[3] = pegArr.get(i).getNama_belakang();
            kolom[4] = pegArr.get(i).getTanggal_lahir();
            kolom[5] = pegArr.get(i).getAlamat();
            kolom[6] = pegArr.get(i).getKode_pos();
            kolom[7] = pegArr.get(i).getNo_telp();
             
            tbl_pegawai.addRow(kolom);
         }
    }
    
    public void insert_data_pegawai(){
        try{
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO pegawai(ID_PEGAWAI, NAMA_DEPAN, NAMA_BELAKANG, TANGGAL_LAHIR, ALAMAT, KODE_POS, NO_TELP)"
                                                        +"VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1, Integer.parseInt(this.frame.getTxt_ID_pegawai().getText()));
            ps.setString(2, this.frame.getNama_Depan_Pegawai().getText());
            ps.setString(3, this.frame.getNama_Belakang_Pegawai().getText());
            ps.setString(4, this.frame.getTanggal_Lahir_Pegawai());
            ps.setString(5, this.frame.getAlamat_Pegawai().getText());
            ps.setInt(6, Integer.parseInt(this.frame.getKode_Pos_Pegawai().getText()));
            ps.setString(7, this.frame.getNo_Telp_Pegawai().getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil dimasukkan!");
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "data gagal dimasukkan! "+e);
        }
    }
    
    public void update_data_pegawai(){
        String UpdateQuery = null;
        PreparedStatement ps = null;
        Connection con = conn.getConnection();
        try{
            UpdateQuery = "UPDATE pegawai SET NAMA_DEPAN = ?, NAMA_BELAKANG = ?, TANGGAL_LAHIR = ?, ALAMAT = ?"
                        +", KODE_POS = ?, NO_TELP = ? WHERE ID_PEGAWAI = ?";
            ps = con.prepareStatement(UpdateQuery);
            ps.setString(1, this.frame.getNama_Depan_Pegawai().getText());
            ps.setString(2, this.frame.getNama_Belakang_Pegawai().getText());
            ps.setString(3, this.frame.getTanggal_Lahir_Pegawai());
            ps.setString(4, this.frame.getAlamat_Pegawai().getText());
            ps.setInt(5, Integer.parseInt(this.frame.getKode_Pos_Pegawai().getText()));
            ps.setString(6, this.frame.getNo_Telp_Pegawai().getText());
            ps.setInt(7, Integer.parseInt(this.frame.getTxt_ID_pegawai().getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Updated!");
        } catch(SQLException e){
            System.out.println("Update unsuccessfully! " +e);
        }
        
        
    }
    
    public void delete_data_pegawai(){
        try{
          Connection con = conn.getConnection();
          PreparedStatement ps = con.prepareStatement("DELETE FROM pegawai WHERE ID_PEGAWAI = ?");
          ps.setInt(1, Integer.parseInt(this.frame.getTxt_ID_pegawai().getText()));
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
            if(benda == frame.getInsertButton_Pegawai()) {
                if(frame.getTxt_ID_pegawai().getText().isEmpty() || frame.getNama_Depan_Pegawai().getText().isEmpty() 
                        || frame.getNama_Belakang_Pegawai().getText().isEmpty() || frame.getAlamat_Pegawai().getText().isEmpty()
                        || frame.getKode_Pos_Pegawai().getText().isEmpty() || frame.getNo_Telp_Pegawai().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    insert_data_pegawai();
                    show_data_to_table();
                    clear_form();
                }
            } else if(benda == frame.getUpdateButton_Pegawai()){
                if(frame.getTxt_ID_pegawai().getText().isEmpty() || frame.getNama_Depan_Pegawai().getText().isEmpty() 
                        || frame.getNama_Belakang_Pegawai().getText().isEmpty() || frame.getAlamat_Pegawai().getText().isEmpty()
                        || frame.getKode_Pos_Pegawai().getText().isEmpty() || frame.getNo_Telp_Pegawai().getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "One or more fields are empty!");
                } else{
                    update_data_pegawai();
                    show_data_to_table();
                    clear_form();
                }
            } else if(benda == frame.getResetFieldPegawai()){
                clear_form();
                show_data_to_table();
            } else if(benda == frame.getBtnDeletePegawai()){
                delete_data_pegawai();
                show_data_to_table();
                clear_form();
            }
        }
    }
    
    class ControlData implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if(benda == frame.getFirstControl_Pegawai()){
                pos = 0;
                show_item_in_form(pos);
                frame.getTblViewPegawai().setRowSelectionInterval(pos, pos);
                frame.getTxt_ID_pegawai().setEditable(false);
                frame.getUpdateButton_Pegawai().setEnabled(true);
                frame.getBtnDeletePegawai().setEnabled(true);
                frame.getInsertButton_Pegawai().setEnabled(false);
            } else if(benda == frame.getLastControl_Pegawai()){
                pos = frame.getTblViewPegawai().getRowCount()-1;
                show_item_in_form(pos);
                frame.getTblViewPegawai().setRowSelectionInterval(pos, pos);
                frame.getTxt_ID_pegawai().setEditable(false);
                frame.getUpdateButton_Pegawai().setEnabled(true);
                frame.getBtnDeletePegawai().setEnabled(true);
                frame.getInsertButton_Pegawai().setEnabled(false);
            } else if(benda == frame.getNextControl_Pegawai()){
                if(frame.getTblViewPegawai().getSelectionModel().isSelectionEmpty()){
                    frame.getTblViewPegawai().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTblViewPegawai().getSelectedRow();
                    pos++;
                    if(pos >= frame.getTblViewPegawai().getRowCount()){
                        pos = frame.getTblViewPegawai().getRowCount()-1;
                    }
                    frame.getTblViewPegawai().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTxt_ID_pegawai().setEditable(false);
                frame.getUpdateButton_Pegawai().setEnabled(true);
                frame.getBtnDeletePegawai().setEnabled(true);
                frame.getInsertButton_Pegawai().setEnabled(false);
            } else if(benda == frame.getPrevControl_Pegawai()){
                if(frame.getTblViewPegawai().getSelectionModel().isSelectionEmpty()){
                    frame.getTblViewPegawai().setRowSelectionInterval(0, 0);
                } else{
                    pos = frame.getTblViewPegawai().getSelectedRow();
                    pos--;
                    if(pos < 0){
                        pos = 0;
                    }
                    frame.getTblViewPegawai().setRowSelectionInterval(pos, pos);
                }
                show_item_in_form(pos);
                frame.getTxt_ID_pegawai().setEditable(false);
                frame.getUpdateButton_Pegawai().setEnabled(true);
                frame.getBtnDeletePegawai().setEnabled(true);
                frame.getInsertButton_Pegawai().setEnabled(false);
            }
        }

    }
    
    public void clear_form(){
        this.frame.getTxt_ID_pegawai().setText("");
        this.frame.getTxt_ID_pegawai().setEditable(true);
        this.frame.getNama_Depan_Pegawai().setText("");
        this.frame.getNama_Belakang_Pegawai().setText("");
        this.frame.getlahir_Pegawai().setCalendar(null);
        this.frame.getAlamat_Pegawai().setText("");
        this.frame.getKode_Pos_Pegawai().setText("");
        this.frame.getNo_Telp_Pegawai().setText("");
        this.frame.getInsertButton_Pegawai().setEnabled(true);
        this.frame.getBtnDeletePegawai().setEnabled(false);
        this.frame.getUpdateButton_Pegawai().setEnabled(false);
        this.frame.getTblViewPegawai().getSelectionModel().clearSelection();
    }
}