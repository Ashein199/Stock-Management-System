/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sithu
 */
public class Database {
    
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "";
    
    private Connection conn;
    
    private static Database db;
    
    private Database() throws SQLException{
        createConnection();
        createDatabase();
        creatTables();
    }
    
    public static Database getInstance() throws SQLException{
        if(db==null){
            db = new Database();
        }
        return db;
    }
    
    public void createConnection() throws SQLException{
        conn = DriverManager.getConnection(url,user,password);
    }
    
    public void createDatabase() throws SQLException{
        String sql  = "create database if not exists smdb";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.execute("use smdb");
    }
    
    public void creatTables() throws SQLException{
        String sql1 = "create table if not exists categories (id int primary key auto_increment,name varchar(40))";
        Statement stmt1 = conn.createStatement();
        stmt1.execute(sql1);
        
        String sql2 = "create table if not exists items (id int primary key auto_increment,name varchar(40),price double,category_id int,stock int,foreign key (category_id) references categories(id))";
        Statement stmt2 = conn.createStatement();
        stmt2.execute(sql2);
        
        String sql3 = "create table if not exists transactions (id int primary key auto_increment,type varchar(20),item_id int,quantity int,sign varchar(40),remark varchar(255),transaction_date date,foreign key (item_id) references items(id))";
        Statement stmt3 = conn.createStatement();
        stmt3.execute(sql3);
    }
     
    public Connection getConnection(){
        return conn;
    }
    
}
