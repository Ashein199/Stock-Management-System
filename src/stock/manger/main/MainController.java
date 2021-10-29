/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import stock.manger.dao.ItemDAO;
import stock.manger.dao.TransactionDAO;
import stock.manger.model.Item;
import stock.manger.model.Transaction;
import stock.manger.util.MessageBox;

/**
 *
 * @author Sithu
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton inoutBtn;
    @FXML
    private JFXButton categoriesBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private JFXTabPane inoutView;
    @FXML
    private JFXButton itemBtn;
    @FXML
    private JFXTextField inQuantityField;
    @FXML
    private JFXTextField inSignField;
    @FXML
    private JFXTextField inRemarkField;
    @FXML
    private JFXButton inSaveBtn;
    @FXML
    private JFXDatePicker inDatePicker;
    @FXML
    private JFXTextField inItemIdField;
    
    private TransactionDAO transactionDAO;
    @FXML
    private JFXDatePicker outDatePicker;
    @FXML
    private JFXTextField outItemIdField;
    @FXML
    private JFXTextField outQuantityField;
    @FXML
    private JFXTextField outSignField;
    @FXML
    private JFXTextField outRemarkField;
    @FXML
    private JFXButton outSaveBtn;
    
    private ItemDAO itemDAO;
    @FXML
    private JFXButton transactionBtn;
    @FXML
    private FontAwesomeIconView inoutIcon;
    @FXML
    private FontAwesomeIconView itemIcon;
    @FXML
    private FontAwesomeIconView categoryIcon;
    @FXML
    private FontAwesomeIconView transactionIcon;
    
    private String btnDefaultStyle = "-fx-background-color:#00bcd4;-fx-text-fill:#fff";
    private String btnActiveStyle = "-fx-background-color:#fff;-fx-text-fill:#00bcd4";
    private String iconDefaultStyle = "-fx-fill:#fff";
    private String iconActiveStyle = "-fx-fill:#00bcd4";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         activeInoutView();
         LocalDate currentDate = LocalDate.now();
         inDatePicker.setValue(currentDate);
         transactionDAO = new TransactionDAO();
         itemDAO = new ItemDAO();
    }

    @FXML
    private void showInoutView(ActionEvent event) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(inoutView);
        activeInoutView();
    }

    @FXML
    private void showCategoriesView(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/stock/manger/categories/categories.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
        activeCategoryView();
    }

    @FXML
    private void showItemsView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/stock/manger/items/items.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
        activeItemView();
        
    }
    
     @FXML
    private void showTransactionView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/stock/manger/transactions/transaction.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
        activeTransactionView();
    }

    @FXML
    private void saveInputTransaction(ActionEvent event) {

        LocalDate localDate = inDatePicker.getValue();
        String idStr = inItemIdField.getText();
        String quantityStr = inQuantityField.getText();
        String sign = inSignField.getText();
        String remark = inRemarkField.getText();

        if (localDate == null || idStr.isEmpty() || quantityStr.isEmpty() || sign.isEmpty() || remark.isEmpty()) {
            System.out.println("Please fill out all field.");
            return;
        }
        try {
            int itemId = Integer.parseInt(idStr);
            int quantity = Integer.parseInt(quantityStr);
            Date date = Date.valueOf(localDate);
            Transaction transaction = new Transaction("IN", itemId, quantity, sign, remark, date);
            transactionDAO.saveTransaction(transaction);
            itemDAO.addStock(itemId,quantity);
            clearInputForm();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void saveOutputTransaction(ActionEvent event) {
        
        LocalDate localDate = outDatePicker.getValue();
        String idStr = outItemIdField.getText();
        String quantityStr = outQuantityField.getText();
        String sign = outSignField.getText();
        String remark = outRemarkField.getText();

        if (localDate == null || idStr.isEmpty() || quantityStr.isEmpty() || sign.isEmpty() || remark.isEmpty()) {
            System.out.println("Please fill out all field.");
            return;
        }
        try {
            int itemId = Integer.parseInt(idStr);
            int quantity = Integer.parseInt(quantityStr);
            Item item = itemDAO.getItem(itemId);
            if(item!=null){
                if(item.getStock()<quantity){
                    MessageBox.showError("ERROR","Invalid quantity input.");
                    return;
                }
            }else{
                MessageBox.showError("ERROR", "Item not found");
                return;
            }
            Date date = Date.valueOf(localDate);
            Transaction transaction = new Transaction("OUT", itemId, quantity, sign, remark, date);
            transactionDAO.saveTransaction(transaction);
            itemDAO.subtractStock(itemId, quantity);
            clearOutputForm();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void clearInputForm() {
        inDatePicker.setValue(LocalDate.now());
        inItemIdField.clear();
        inQuantityField.clear();
        inSignField.clear();
        inRemarkField.clear();
    }

    private void clearOutputForm() {
        outDatePicker.setValue(LocalDate.now());
        outItemIdField.clear();
        outQuantityField.clear();
        outSignField.clear();
        outRemarkField.clear();
    }

    private void activeInoutView() {
        inoutBtn.setStyle(btnActiveStyle);
        inoutIcon.setStyle(iconActiveStyle);
        itemBtn.setStyle(btnDefaultStyle);
        itemIcon.setStyle(iconDefaultStyle);
        categoriesBtn.setStyle(btnDefaultStyle);
        categoryIcon.setStyle(iconDefaultStyle);
        transactionBtn.setStyle(btnDefaultStyle);
        transactionIcon.setStyle(iconDefaultStyle);
    }
    
     private void activeItemView() {
        inoutBtn.setStyle(btnDefaultStyle);
        inoutIcon.setStyle(iconDefaultStyle);
        itemBtn.setStyle(btnActiveStyle);
        itemIcon.setStyle(iconActiveStyle);
        categoriesBtn.setStyle(btnDefaultStyle);
        categoryIcon.setStyle(iconDefaultStyle);
        transactionBtn.setStyle(btnDefaultStyle);
        transactionIcon.setStyle(iconDefaultStyle);
    }
     
      private void activeCategoryView() {
        inoutBtn.setStyle(btnDefaultStyle);
        inoutIcon.setStyle(iconDefaultStyle);
        itemBtn.setStyle(btnDefaultStyle);
        itemIcon.setStyle(iconDefaultStyle);
        categoriesBtn.setStyle(btnActiveStyle);
        categoryIcon.setStyle(iconActiveStyle);
        transactionBtn.setStyle(btnDefaultStyle);
        transactionIcon.setStyle(iconDefaultStyle);
    }
      
       private void activeTransactionView() {
        inoutBtn.setStyle(btnDefaultStyle);
        inoutIcon.setStyle(iconDefaultStyle);
        itemBtn.setStyle(btnDefaultStyle);
        itemIcon.setStyle(iconDefaultStyle);
        categoriesBtn.setStyle(btnDefaultStyle);
        categoryIcon.setStyle(iconDefaultStyle);
        transactionBtn.setStyle(btnActiveStyle);
        transactionIcon.setStyle(iconActiveStyle);
    }

   

}
