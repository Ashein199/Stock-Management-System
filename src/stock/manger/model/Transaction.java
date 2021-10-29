/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.manger.model;

import java.sql.Date;

/**
 *
 * @author Sithu
 */
public class Transaction {
    
    private int id;
    private String type;
    private Item item;
    private int itemId;
    private int quantity;
    private String sign;
    private String remark;
    private Date date;

    public Transaction(String type, int itemId, int quantity, String sign, String remark, Date date) {
        this.type = type;
        this.itemId = itemId;
        this.quantity = quantity;
        this.sign = sign;
        this.remark = remark;
        this.date = date;
    }
    

    public Transaction(int id, String type, Item item, int quantity, String sign, String remark, Date date) {
        this.id = id;
        this.type = type;
        this.item = item;
        this.quantity = quantity;
        this.sign = sign;
        this.remark = remark;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    

   
    
    
   
    
    
}
