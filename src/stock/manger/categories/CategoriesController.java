/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.categories;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stock.manger.dao.CategoryDAO;
import stock.manger.model.Category;
import stock.manger.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class CategoriesController implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXButton createBtn;
    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, Integer> idColumn;
    @FXML
    private TableColumn<Category, String> nameColumn;

    private CategoryDAO categoryDAO;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryDAO = new CategoryDAO();
        initColumns();
        loadTableData();
    }

    @FXML
    private void createCategory(ActionEvent event) {
        String name = nameField.getText();

        if (name.isEmpty()) {
            MessageBox.showError("INPUT ERROR","Plase fill out name field.");
            return;
        }
        try {
            categoryDAO.saveCategory(new Category(name));
            nameField.clear();
            loadTableData();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadTableData() {
        try {
            List<Category> categories = categoryDAO.getCategories();
            categoryTable.getItems().setAll(categories);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showEditWindow(ActionEvent event) throws IOException {
        
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        
        if (selectedCategory != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stock/manger/categories/edit.fxml"));
            Parent root = loader.load();
            EditController controller =  loader.getController();
            controller.setCategoryData(selectedCategory);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            Stage mainStage = (Stage) categoryTable.getScene().getWindow();
            stage.initOwner(mainStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            loadTableData();
        }
    }

    @FXML
    private void deleteCategory(ActionEvent event) {
        // get selected item
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {

            Optional<ButtonType> option = MessageBox.showAndWaitConfirmation("Confirmation", "Are you sure you want to delete?");
            if (option.get() == ButtonType.OK) {
                try {
                    // delete from database
                    categoryDAO.deleteCategory(selectedCategory.getId());
                    // delete form catTable list
                    categoryTable.getItems().remove(selectedCategory);
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            MessageBox.showError("Delete Error","Plase select the category you want to delete first.");   
        }

    }

}
