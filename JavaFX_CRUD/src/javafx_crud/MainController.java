/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_crud;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import Model.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Connection.connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
/**
 *
 * @author Ouka
 */
public class MainController implements Initializable {
    
    connection conn = new connection();
    private Label label;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfPages;
    @FXML
    private TableView<Books> tvBooks;
    @FXML
    private TableColumn<Books, Integer> colId;
    @FXML
    private TableColumn<Books, String> colTitle;
    @FXML
    private TableColumn<Books, String> colAuthor;
    @FXML
    private TableColumn<Books, Integer> colYear;
    @FXML
    private TableColumn<Books, Integer> colPages;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    
    @FXML
    private void handleInsertAction(ActionEvent event) {
        if(event.getSource() == btnInsert){
            insertRecord();
        } else if(event.getSource() == btnupdate){
            updateRecord();
            btnupdate.setDisable(true);
            btnDelete.setDisable(true);
            btnInsert.setDisable(false);
        }
    }
    
    @FXML 
    private void handleClearAction(ActionEvent event) {
        if(event.getSource() == btnClear){
            clear();
            btnupdate.setDisable(true);
            btnDelete.setDisable(true);
            btnInsert.setDisable(false);
        }
    }
    
    @FXML 
    private void handleUpdateAction(ActionEvent event) {
        if(event.getSource() == btnupdate){
            updateRecord();
            btnupdate.setDisable(true);
            btnDelete.setDisable(true);
            btnInsert.setDisable(false);
        }
    }
    
    @FXML 
    private void handleDeleteAction(ActionEvent event) {
        if(event.getSource() == btnDelete){
            deleteButton();
            btnupdate.setDisable(true);
            btnDelete.setDisable(true);
            btnInsert.setDisable(false);
        }
    }
    
    private void formCheck(){
        if(tfId.getText().isEmpty() || tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() || tfPages.getText().isEmpty() || tfYear.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "One or more fields are empty!");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBooks();
        int total=1;
        for(int i=0;i<tvBooks.getItems().size();i++){
            total+=1;
        }
        tfId.setText(Integer.toString(total));
        tfId.setEditable(false);
        System.out.print(total);
        btnupdate.setDisable(true);
        btnDelete.setDisable(true);
        tblClicked();
    }    
    
    private void tblClicked(){
        tvBooks.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                btnupdate.setDisable(false);
                btnDelete.setDisable(false);
                btnInsert.setDisable(true);
                if (tvBooks.getSelectionModel().getSelectedItem() == null) {
                    System.out.println("Null");
                } else if(tvBooks.getSelectionModel().getSelectedItem() != null){
                    tfId.setText(Integer.toString(tvBooks.getSelectionModel().getSelectedItem().getId()));
                    tfId.setEditable(true);
                    tfTitle.setText(tvBooks.getSelectionModel().getSelectedItem().getTitle());
                    tfAuthor.setText(tvBooks.getSelectionModel().getSelectedItem().getAuthor());
                    tfYear.setText(Integer.toString(tvBooks.getSelectionModel().getSelectedItem().getYear()));
                    tfPages.setText(Integer.toString(tvBooks.getSelectionModel().getSelectedItem().getPages()));
                }
            }
            
        });
    }
    
    public ObservableList<Books> getBooksList(){
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        String query = "SELECT*FROM books";
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.getConnection().createStatement();
            rs = st.executeQuery(query);
            Books books;
            while (rs.next()) {
                books = new Books(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("Year"), rs.getInt("pages"));
                bookList.add(books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }
    
    public void showBooks(){
        ObservableList<Books> list = getBooksList();
        colId.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));
        tvBooks.setItems(list);
    }
    
    private void clear(){
        tfTitle.setText("");
        tfAuthor.setText("");
        tfYear.setText("");
        tfPages.setText("");
        int total=1;
        for(int i=0;i<tvBooks.getItems().size();i++){
            total+=1;
        }
        System.out.print(total);
        tfId.setText(Integer.toString(total));
    }
    
    private void insertRecord(){
        String query = "INSERT INTO books VALUES("+ tfId.getText() + ",'" + tfTitle.getText() + "','" + tfAuthor.getText() + "',"
                + tfYear.getText() + "," + tfPages.getText() + ")";
        executeQuery(query);
        showBooks(); //update the table view after data inserted
        clear();
    }
    
    private void updateRecord(){
        String query = "UPDATE  books SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " +
                tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";
        executeQuery(query);
        showBooks();
        clear();
    }

    private void deleteButton(){
        String query = "DELETE FROM books WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showBooks();
        clear();
    }
    private void executeQuery(String query) {
        Statement st;
        try {
            st = conn.getConnection().createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAction(javafx.scene.input.MouseEvent event) {
    }
}
