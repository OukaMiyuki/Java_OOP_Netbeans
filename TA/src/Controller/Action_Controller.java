/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import Main_Views.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Ouka
 */
public class Action_Controller{
    private ArrayList<Barang> modelBarang = new ArrayList<Barang>();
    private Main_Frame frame;
    private Barang barang;
    private DefaultTableModel produktbl;
    private DefaultTableModel penjualan;
    
    public Action_Controller(Main_Frame frame, Barang barang){
        this.frame = frame;
        this.barang = barang;
        //this.frame.addTambahListener(new addListener());
        this.frame.getAddButton().addActionListener(new addData());
        this.frame.getNewDataButton().addActionListener(new addData());
        this.frame.getClearFieldsButton().addActionListener(new addData());
        this.produktbl = (DefaultTableModel) frame.getTable().getModel();
        this.penjualan = (DefaultTableModel) frame.getTablePenjualan().getModel();
        frame.getTablePenjualan().addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                productclicked(evt);
            }
        });
        this.frame.getBanyakBeli().addCaretListener(new javax.swing.event.CaretListener(){
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                qtypress(evt);
            }
        });
        
        this.frame.getBanyakBeli().addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qtypresss(evt);
            }
        });
        
    }
    public void productclicked(java.awt.event.MouseEvent evt){
        int index = frame.getTablePenjualan().getSelectedRow();
        frame.getIDProduk().setText(penjualan.getValueAt(index, 0).toString());
        frame.getNamaProduk().setText(penjualan.getValueAt(index, 1).toString());
        frame.getHargaProduk().setText(penjualan.getValueAt(index, 2).toString());
    }
    
    public void qtypress(javax.swing.event.CaretEvent evt){
        int total;
	try{
            int harga = Integer.parseInt(frame.getHargaProduk().getText());
            int qty = Integer.parseInt(frame.getBanyakBeli().getText());
            if(frame.getBanyakBeli().getText().isEmpty()){
                total = 0;
            } else{
                total = harga*qty;
            }
            frame.setTTLHarga(total);
	} catch(Exception e){
			
	}
    }
    
    public void qtypresss(java.awt.event.KeyEvent evt){
         if(frame.getBanyakBeli().getText().isEmpty()){
             frame.setTTLHarga(0);
         }
    }
    
    class addData implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Object benda = e.getSource();
            if(benda == frame.getAddButton()){
                if(frame.idBarang().getText().isEmpty() || frame.namaBarang().getText().isEmpty() || frame.hargaBarang().getText().isEmpty() || frame.stokBarang().getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Text tidak boleh kosong");
                } else{
                    try{
                        Integer.parseInt(frame.hargaBarang().getText());
                        Integer.parseInt(frame.stokBarang().getText());
                        storetoTBL();
                    } catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Inputan Harga atau stok harus berupa angka!");
                    }
                }
            } else if(benda == frame.getNewDataButton()){
                frame.getAddButton().setEnabled(true);
                frame.getNewDataButton().setEnabled(false);
                frame.idBarang().setText("");
                frame.idBarang().setEnabled(true);
                frame.namaBarang().setText("");
                frame.namaBarang().setEnabled(true);
                frame.hargaBarang().setText("");
                frame.hargaBarang().setEnabled(true);
                frame.stokBarang().setText("");
                frame.stokBarang().setEnabled(true);
                frame.getClearFieldsButton().setEnabled(true);
            } else if(benda == frame.getClearFieldsButton()){
                frame.idBarang().setText("");
                frame.namaBarang().setText("");
                frame.hargaBarang().setText("");
                frame.stokBarang().setText("");
            }
        }
        
        public void storetoTBL(){
            ArrayList<Barang> listBarang = addtomodelBarang();
                Object rowData[] = new Object[4];
                for(int i=0;i<listBarang.size();i++){
                    rowData[0] = listBarang.get(i).id;
                    rowData[1] = listBarang.get(i).nama;
                    rowData[2] = listBarang.get(i).harga;
                    rowData[3] = listBarang.get(i).stok;
                
                    produktbl.addRow(rowData);
                    penjualan.addRow(rowData);
                }
                frame.idBarang().setText("");
                frame.idBarang().setEnabled(false);
                frame.namaBarang().setText("");
                frame.namaBarang().setEnabled(false);
                frame.hargaBarang().setText("");
                frame.hargaBarang().setEnabled(false);
                frame.stokBarang().setText("");
                frame.stokBarang().setEnabled(false);
                frame.getAddButton().setEnabled(false);
                frame.getNewDataButton().setEnabled(true);
                frame.getClearFieldsButton().setEnabled(false);
        }
        public ArrayList addtomodelBarang(){
            barang = new Barang();
            barang.setBarang(frame.idBarang().getText(), frame.namaBarang().getText(), Integer.parseInt(frame.hargaBarang().getText()), Integer.parseInt(frame.stokBarang().getText()));
            modelBarang.add(barang);
            
            return modelBarang;
        }
    }
}