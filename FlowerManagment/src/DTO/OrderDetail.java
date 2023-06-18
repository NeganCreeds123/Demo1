/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author 84878
 */
import java.io.Serializable;
import java.time.LocalDate;

public class OrderDetail implements Serializable{
    private String orderDetailId;
    private String flowerId;
    private int quantity;
    private double flowerCost;

    public OrderDetail(String orderDetailId, String flowerId, int quantity, double flowerCost) {
        this.orderDetailId = orderDetailId;
        this.flowerId = flowerId;
        this.quantity = quantity;
        this.flowerCost = flowerCost;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getFlowerCost() {
        return flowerCost;
    }
}