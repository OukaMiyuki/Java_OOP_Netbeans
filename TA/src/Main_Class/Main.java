/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main_Class;

import Main_Views.*;
import Model.*;
import Controller.*;
/**
 *
 * @author Ouka
 */
public class Main {
    public static void main(String[] args){
        Main_Frame view = new Main_Frame();
        Barang model = new Barang();
        Action_Controller control = new Action_Controller(view, model);
        view.setVisible(true);
    }
}
