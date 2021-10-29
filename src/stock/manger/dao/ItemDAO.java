/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import stock.manger.database.Database;
import stock.manger.model.Category;
import stock.manger.model.Item;

/**
 *
 * @author Sithu
 */
public class ItemDAO {

    public void saveItem(Item item) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into items (name,price,category_id,stock) values (?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, item.getName());
        stmt.setDouble(2, item.getPrice());
        stmt.setInt(3, item.getCategory().getId());
        stmt.setInt(4, 0);
        stmt.executeUpdate();
    }

    public Item getItem(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from items where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        CategoryDAO catDAO = new CategoryDAO();
        Item item = null;
        if (result.next()) {
            String name = result.getString("name");
            double price = result.getDouble("price");
            int categoryId = result.getInt("category_id");
            Category category = catDAO.getCategory(categoryId);
            int stock = result.getInt("stock");
            item = new Item(id, name, price, category, stock);
        }
        return item;
    }

    public List<Item> getItems() throws SQLException {
        //select items.id as item_id,items.name as item_name,items.price,items.category_id,items.stock,categories.name as category_name from items left join categories on items.category_id=categories.id;
        Connection conn = Database.getInstance().getConnection();
        String sql = "select items.id as item_id,items.name as item_name,items.price,items.category_id,items.stock,categories.name as category_name from items left join categories on items.category_id=categories.id";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Item> items = new ArrayList<>();
        while (result.next()) {
            int itemId = result.getInt("item_id");
            String itemName = result.getString("item_name");
            double price = result.getDouble("price");
            int categoryId = result.getInt("category_id");
            String categoryName = result.getString("category_name");
            int stock = result.getInt("stock");
            Category category = new Category(categoryId, categoryName);
            Item item = new Item(itemId, itemName, price, category, stock);
            items.add(item);
        }
        return items;
    }

    public void updateItem(Item item) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "update items set name=?,price=?,category_id=? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, item.getName());
        stmt.setDouble(2, item.getPrice());
        stmt.setInt(3,item.getCategory().getId());
        stmt.setInt(4, item.getId());
        stmt.executeUpdate();
    }

    public void deleteItem(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from items where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public void addStock(int itemId, int quantity) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "update items set stock=stock+? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quantity);
        stmt.setInt(2, itemId);
        stmt.executeUpdate();
    }
    public void subtractStock(int itemId, int quantity) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "update items set stock=stock-? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, quantity);
        stmt.setInt(2, itemId);
        stmt.executeUpdate();
    }

}
