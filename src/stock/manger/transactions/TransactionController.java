/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.transactions;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stock.manger.dao.TransactionDAO;
import stock.manger.model.Item;
import stock.manger.model.Transaction;
import stock.manger.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class TransactionController implements Initializable {

    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Date> dateColumn;
    @FXML
    private TableColumn<Transaction, Integer> idColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, Item> itemColumn;
    @FXML
    private TableColumn<Transaction, Integer> quantityColumn;
    @FXML
    private TableColumn<Transaction, String> signColumn;
    @FXML
    private TableColumn<Transaction, String> remarkColumn;
    
    private TransactionDAO transactionDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transactionDAO = new TransactionDAO();
        initColumns();
    }    

    @FXML
    private void loadTransactionData(ActionEvent event) {
        
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        
        if(startDate==null|| endDate==null){
            MessageBox.showError("Error", "Please select the start date and end date.");
            return;
        }
        
        if(startDate.isAfter(endDate)){
            MessageBox.showError("Error", "Date input error.");
            return;
        }
        
        try {
           List<Transaction> transactions = transactionDAO.getTransactions(Date.valueOf(startDate),Date.valueOf(endDate));
           transactionTable.getItems().setAll(transactions);
        } catch (SQLException ex) {
            Logger.getLogger(TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void initColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        signColumn.setCellValueFactory(new PropertyValueFactory<>("sign"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
    }
    
}
