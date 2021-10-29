/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.items;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import stock.manger.categories.CategoriesController;
import stock.manger.dao.ItemDAO;
import stock.manger.model.Category;
import stock.manger.model.Item;
import stock.manger.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author Sithu
 */
public class ItemsController implements Initializable {

    @FXML
    private JFXButton addBtn;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private TableColumn<Item, Integer> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn;
    @FXML
    private TableColumn<Item, Category> categoryColumn;
    @FXML
    private TableColumn<Item, Integer> stockColumn;
    
    private ItemDAO itemDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemDAO = new ItemDAO();
        initColumns();
        loadTableData();
    }

    @FXML
    private void loadNewItemWindow(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/stock/manger/items/newitem.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        Stage mainStage = (Stage) addBtn.getScene().getWindow();
        stage.initOwner(mainStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        loadTableData();
    }

    @FXML
    private void loadEidtWindow(ActionEvent event) throws IOException {
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stock/manger/items/edit.fxml"));
            Parent root = loader.load();
            EditController controller =  loader.getController();
            controller.setItemData(selectedItem);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            Stage mainStage = (Stage) itemTable.getScene().getWindow();
            stage.initOwner(mainStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            loadTableData();
        }
    }

    @FXML
    private void deleteItemInfo(ActionEvent event) {
        // get selected item
        Item selectedItem = itemTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            Optional<ButtonType> option = MessageBox.showAndWaitConfirmation("Confirmation", "Are you sure you want to delete this item?");
            if (option.get() == ButtonType.OK) {
                try {
                    // delete from database
                    itemDAO.deleteItem(selectedItem.getId());
                    // delete form catTable list
                    itemTable.getItems().remove(selectedItem);
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("DELETE ERROR");
            alert.setContentText("Plase select the category you want to delete first.");
            alert.show();
        }

    }

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void loadTableData() {
        try {
            List<Item> items = itemDAO.getItems();
            itemTable.getItems().setAll(items);
        } catch (SQLException ex) {
            Logger.getLogger(ItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
