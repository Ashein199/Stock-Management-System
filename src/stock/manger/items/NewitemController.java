/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.items;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import stock.manger.dao.CategoryDAO;
import stock.manger.dao.ItemDAO;
import stock.manger.model.Category;
import stock.manger.model.Item;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class NewitemController implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField priceField;
    @FXML
    private JFXComboBox<Category> categoryCombo;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    
    private CategoryDAO categoryDAO;
    private ItemDAO itemDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryDAO = new CategoryDAO();
        itemDAO = new ItemDAO();
        try {
            List<Category> categories = categoryDAO.getCategories();
            categoryCombo.getItems().setAll(categories);
            categoryCombo.setValue(categories.get(0));
        } catch (SQLException ex) {
            Logger.getLogger(NewitemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void saveItem(ActionEvent event) {
        String name = nameField.getText();
        String priceStr = priceField.getText();
        Category category = categoryCombo.getValue();
        
        if(name.isEmpty()||priceStr.isEmpty()){
            System.out.println("Please fill out all required field.");
            return;
        }
        try{
            double price = Double.parseDouble(priceStr);
            Item item = new Item(name, price, category, 0);
            itemDAO.saveItem(item);
            System.out.println("Success");
            Stage currentStage = (Stage)saveBtn.getScene().getWindow();
            currentStage.close();
        }catch(NumberFormatException e){
            System.out.println("Invalid number.");
        } catch (SQLException ex) {
            Logger.getLogger(NewitemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage)saveBtn.getScene().getWindow();
        currentStage.close();
    }
    
}
