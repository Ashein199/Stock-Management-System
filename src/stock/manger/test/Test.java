/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import stock.manger.dao.CategoryDAO;
import stock.manger.dao.ItemDAO;
import stock.manger.model.Item;

/**
 *
 * @author Sithu
 */
public class Test {

    public static void main(String[] args) {

        CategoryDAO catDAO = new CategoryDAO();
        ItemDAO itemDAO = new ItemDAO();
        try {

//            Category cat = catDAO.getCategory(3);
//            Item item = new Item("Laptop",890000, cat, 0);
//            itemDAO.saveItem(item);
//              Item item = itemDAO.getItem(1);
//              System.out.println("Item:"+item.getId()+","+item.getName());
            List<Item> items = itemDAO.getItems();
            
            for(Item item:items){
                System.out.println(item);
            }
            
            System.out.println("Scuccess.");
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
