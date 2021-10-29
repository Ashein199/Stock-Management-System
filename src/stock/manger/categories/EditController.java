/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.categories;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import stock.manger.dao.CategoryDAO;
import stock.manger.model.Category;
import stock.manger.model.Item;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class EditController implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;
    
    private int id;
    
    private CategoryDAO categoryDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryDAO  = new CategoryDAO();
    }    

    @FXML
    private void updateCategory(ActionEvent event) {
        String name = nameField.getText();
        if(name.isEmpty()){
            System.out.println("Please fill out name field.");
            return;
        }
        
        Category category = new Category(id,name);
        try {
            categoryDAO.updateCategory(category);
            Stage currentStage = (Stage)saveBtn.getScene().getWindow();
            currentStage.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage)saveBtn.getScene().getWindow();
        currentStage.close();
    }

    public void setCategoryData(Category selectedCategory) {
        id = selectedCategory.getId();
        nameField.setText(selectedCategory.getName());
    }

    
    
}
