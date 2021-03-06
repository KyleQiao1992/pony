package com.pony.domain;

import com.pony.productManage.entity.Product;

import java.util.Date;

/**
 * @author: qiaoyi
 * @edit:
 * @created:17/3/31
 */
public class ShoppingCartEntry {
    private int id;
    private String phone;
    private int count;
    private int userId;
    private int productId;
    private Date time;
    private Date updateTime;
    private int addressId;
    private int cabinetId;
    private int type;
    private int stock;
    private int status;
    //余量
    private int surplus;
    //商品实体类
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(int cabinetId) {
        this.cabinetId = cabinetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ShoppingCartEntry{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", count=" + count +
                ", userId=" + userId +
                ", productId=" + productId +
                ", time=" + time +
                ", updateTime=" + updateTime +
                ", addressId=" + addressId +
                ", cabinetId=" + cabinetId +
                ", type=" + type +
                ", stock=" + stock +
                ", status=" + status +
                ", surplus=" + surplus +
                ", product=" + product +
                '}';
    }
}
