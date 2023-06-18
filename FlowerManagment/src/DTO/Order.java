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
import java.util.List;

public class Order implements Serializable{
    private OrderHeader orderHeader;
    private List<OrderDetail> orderDetails;

    public Order(OrderHeader orderHeader, List<OrderDetail> orderDetails) {
        this.orderHeader = orderHeader;
        this.orderDetails = orderDetails;
    }

    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

 @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("No.  Order Id  Order Date   Customer       FlowerCount   Order Total\n");
    int orderCount = 0;
    int totalFlowerCount = 0;
    double totalOrderTotal = 0;

    for (OrderDetail orderDetail : orderDetails) {
        orderCount++;
        String orderId = orderHeader.getOrderId();
        LocalDate orderDate = orderHeader.getOrderDate();
        String customer = orderHeader.getCustomerName();
        int flowerCount = orderDetail.getQuantity();
        double orderTotal = orderDetail.getFlowerCost() * flowerCount;

        totalFlowerCount += flowerCount;
        totalOrderTotal += orderTotal;

        sb.append(String.format("%-5d%-10s%-15s%-20s%-15d$ %-10.2f%n",
                orderCount, orderId, orderDate, customer, flowerCount, orderTotal));
    }

    if (orderCount == 0) {
        sb.append("No orders found.\n");
    } else {
        sb.append(String.format("Total                                        %-15d$ %-10.2f%n",
                totalFlowerCount, totalOrderTotal));
    }

    return sb.toString();
}


    
}
