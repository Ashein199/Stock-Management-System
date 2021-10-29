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

/**
 *
 * @author Sithu
 */
public class CategoryDAO {
    
    
    public void saveCategory(Category category) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into categories (name) values (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, category.getName());
        stmt.executeUpdate();
    } 
    
    public Category getCategory(int id) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from categories where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        Category category = null;
        if(result.next()){
            String name = result.getString("name");
            category = new Category(id, name);
        }
        return category;
    }
    
    public List<Category> getCategories() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from categories";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        List<Category> list = new ArrayList<>();
        while(result.next()){
            int id = result.getInt("id");
            String name = result.getString("name");
            Category category = new Category(id, name);
            list.add(category);
        }
        return list;
    }
    
    public void  updateCategory(Category category) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "update categories set name=? where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, category.getName());
        stmt.setInt(2, category.getId());
        stmt.executeUpdate();
    }
    
    
    public void deleteCategory(int id) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "delete from categories where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
