/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import stock.manger.database.Database;
import stock.manger.model.Item;
import stock.manger.model.Transaction;

/**
 *
 * @author Sithu
 */
public class TransactionDAO {

    public void saveTransaction(Transaction transaction) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "insert into transactions (type,item_id,quantity,sign,remark,transaction_date) values (?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, transaction.getType());
        stmt.setInt(2, transaction.getItemId());
        stmt.setInt(3, transaction.getQuantity());
        stmt.setString(4, transaction.getSign());
        stmt.setString(5, transaction.getRemark());
        stmt.setDate(6, transaction.getDate());
        stmt.executeUpdate();
    }
    
    
    public List<Transaction> getTransactions(Date startDate,Date endDate) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select transactions.id,transactions.type,transactions.item_id,transactions.quantity,transactions.sign,transactions.remark,transactions.transaction_date,items.name from transactions left join items on transactions.item_id=items.id where transaction_date between ? and ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1,startDate);
        stmt.setDate(2,endDate);
        ResultSet result = stmt.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while(result.next()){
            int id = result.getInt("id");
            String type = result.getString("type");
            int itemId = result.getInt("item_id");
            int quantity = result.getInt("quantity");
            String sing = result.getString("sign");
            String remark = result.getString("remark");
            Date date = result.getDate("transaction_date");
            String name = result.getString("name");
            Item item = new Item(itemId,name);
            Transaction transaction = new Transaction(id, type, item, quantity, sing, remark, date);
            transactions.add(transaction);
        }
        return transactions;
    }

}
