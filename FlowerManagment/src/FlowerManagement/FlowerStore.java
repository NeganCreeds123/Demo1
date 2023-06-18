/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlowerManagement;

import DTO.Flower;
import DTO.Order;
import DTO.OrderDetail;
import DTO.OrderHeader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FlowerStore {
    private static final String FLOWERS_FILE = "flowers.dat";
    private static final String ORDERS_FILE = "orders.dat";
    
    private Set<Flower> flowers;
    private Set<Order> orders;

    public FlowerStore() {
        flowers = new HashSet<>();
        orders = new HashSet<>();
    }

    private boolean isIdUnique(String flowerId) {
        for (Flower flower : flowers) {
            if (flower.getFlowerID().equals(flowerId)) {
                return false;
            }
        }
        return true;
    }

    public void addFlower() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter flower details:");
            System.out.print("ID: ");
            String flowerID = sc.nextLine();

            while (flowerID.isEmpty()) {
                System.out.println("ID cannot be empty.");
                System.out.print("ID: ");
                flowerID = sc.nextLine();
            }

            boolean isIdUnique = isIdUnique(flowerID);
            while (!isIdUnique) {
                System.out.println("ID must be unique. Please enter a different ID.");
                System.out.print("ID: ");
                flowerID = sc.nextLine();
                isIdUnique = isIdUnique(flowerID);
            }

            System.out.print("Name: ");
            String name = null;

            while (name == null || name.isEmpty()) {
                name = sc.nextLine();

                if (name.isEmpty()) {
                    System.out.println("Name cannot be null or empty.Pls Enter name again:");
                    System.out.println("Name :");
                    name = sc.nextLine();

                }
            }

            System.out.print("Description: ");
            String description = null;

            while (description == null || description.isEmpty()) {
                description = sc.nextLine();

                if (description.isEmpty()) {
                    System.out.println("Description cannot be null or empty.");
                } else if (description.length() < 3 || description.length() > 50) {
                    System.out.println("Description must be between 3 and 50 characters.");
                    description = "";
                }
            }

            System.out.print("Import Date (YYYY-MM-DD): ");
            String importDateStr = "";

            while (importDateStr.isEmpty()) {
                importDateStr = sc.nextLine();

                if (importDateStr.isEmpty()) {
                    System.out.println("Import date cannot be null or empty.");
                }
            }

            LocalDate importDate = null;
            boolean validDate = false;

            while (!validDate) {
                try {
                    importDate = LocalDate.parse(importDateStr);
                    validDate = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
                    importDateStr = sc.nextLine();
                }
            }

            double unitPrice = 0;

            while (true) {
                System.out.print("Unit Price: ");
                try {
                    unitPrice = Double.parseDouble(sc.nextLine());
                    if (unitPrice <= 0) {
                        System.out.println("Unit price must be a positive number.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for the unit price.");
                }
            }

            System.out.print("Category: ");
            String category = sc.nextLine();

            if (category == null || category.isEmpty()) {
                System.out.println("Category cannot be null or empty.");
                continue;
            }

            Flower flower = new Flower(flowerID, name, description, importDate, unitPrice, category);
            flowers.add(flower);

            System.out.println("Flower added successfully!");

            System.out.println("Do you want to add another flower? (Y/N)");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    public void findFlower() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter flower ID or name: ");
        String found = sc.nextLine();

        Flower foundFlower = null;

        for (Flower flower : flowers) {
            if (flower.getFlowerID().equalsIgnoreCase(found) || flower.getName().equalsIgnoreCase(found)) {
                foundFlower = flower;
                break;
            }
        }

        if (foundFlower != null) {
            System.out.println("Flower found:");
            System.out.println(foundFlower.toString());
        } else {
            System.out.println("The flower does not exist.");
        }
    }

    public void updateFlower() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter flower name: ");
        String flowerName = sc.nextLine();

        Flower foundFlower = null;

        // Find the flower by name
        for (Flower flower : flowers) {
            if (flower.getName().equalsIgnoreCase(flowerName)) {
                foundFlower = flower;
                break;
            }
        }

        if (foundFlower == null) {
            System.out.println("The flower does not exist.");
        } else {
            // Flower found, allow user to update its details
            System.out.println("Flower found:");
            System.out.println(foundFlower.toString());

            // Prompt for update
            System.out.println("Enter the updated details:");

            System.out.print("New ID: ");
            String updatedId = sc.nextLine();

            while (updatedId.isEmpty()) {
                System.out.println("ID cannot be empty.");
                System.out.print("New ID: ");
                updatedId = sc.nextLine();
            }

            boolean isIdUnique = false;
            while (!isIdUnique) {
                isIdUnique = true;
                for (Flower flower : flowers) {
                    if (flower != foundFlower && flower.getFlowerID().equalsIgnoreCase(updatedId)) {
                        System.out.println("ID must be unique. Please enter a different ID.");
                        System.out.print("New ID: ");
                        updatedId = sc.nextLine();
                        isIdUnique = false;
                        break;
                    }
                }
            }

            foundFlower.setFlowerID(updatedId);

            System.out.print("New Description: ");
            String updatedDescription = null;

            while (updatedDescription == null || updatedDescription.isEmpty()) {
                updatedDescription = sc.nextLine();

                if (updatedDescription.isEmpty()) {
                    System.out.println("Description cannot be null or empty.");
                } else if (updatedDescription.length() < 3 || updatedDescription.length() > 50) {
                    System.out.println("Description must be between 3 and 50 characters.");
                    updatedDescription = "";
                }
            }

            foundFlower.setDescription(updatedDescription);

            System.out.print("New Import Date (YYYY-MM-DD): ");
            String updatedImportDate = "";

            while (updatedImportDate.isEmpty()) {
                updatedImportDate = sc.nextLine();

                if (updatedImportDate.isEmpty()) {
                    System.out.println("Import date cannot be null or empty.");
                }
            }

            LocalDate importDate = null;
            boolean validDate = false;

            while (!validDate) {
                try {
                    importDate = LocalDate.parse(updatedImportDate);
                    validDate = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
                    System.out.print("New Import Date (YYYY-MM-DD): ");
                    updatedImportDate = sc.nextLine();
                }
            }

            foundFlower.setImportDate(importDate);

            double updatedUnitPrice = 0;
            boolean validUnitPrice = false;
            while (!validUnitPrice) {
                System.out.print("New unit Price: ");
                try {
                    updatedUnitPrice = Double.parseDouble(sc.nextLine());
                    if (updatedUnitPrice <= 0) {
                        System.out.println("Unit price must be a positive number.");
                    } else {
                        validUnitPrice = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for the unit price.");
                }
            }
            foundFlower.setUnitPrice(updatedUnitPrice);

            System.out.print("New Category: ");
            String updatedCategory = "";

            while (updatedCategory.isEmpty()) {
                updatedCategory = sc.nextLine();

                if (updatedCategory.isEmpty()) {
                    System.out.println("Category cannot be null or empty.");
                }
            }

            foundFlower.setCategory(updatedCategory);
        }

        System.out.println("Flower updated successfully!");
        System.out.println(foundFlower.toString());
    }

    public void deleteFlower() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the flower ID to delete: ");
        String flowerId = scanner.nextLine();

        // Check if the flower exists
        Flower flowerToDelete = null;
        for (Flower flower : flowers) {
            if (flower.getFlowerID().equals(flowerId)) {
                flowerToDelete = flower;
                break;
            }
        }

        if (flowerToDelete == null) {
            System.out.println("The flower does not exist.");
            return;
        }

        // Check if the flower exists in any order details
        boolean isUsedInOrderDetails = false;
        for (Order order : orders) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                if (orderDetail.getFlowerId().equals(flowerId)) {
                    isUsedInOrderDetails = true;
                    break;
                }
            }
            if (isUsedInOrderDetails) {
                break;
            }
        }

        if (isUsedInOrderDetails) {
            System.out.println("The flower cannot be deleted as it is used in an order detail.");
            return;
        }

        // Confirmation message
        System.out.println("Are you sure you want to delete the flower? (Y/N)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            flowers.remove(flowerToDelete);
            System.out.println("The flower has been deleted successfully.");

            // Update any order details referencing the deleted flower
            for (Order order : orders) {
                List<OrderDetail> orderDetails = order.getOrderDetails();
                orderDetails.removeIf(detail -> detail.getFlowerId().equals(flowerId));
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    public void addOrder() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter order details:");

            System.out.print("Order ID: ");
            String orderId = scanner.nextLine();

            while (orderId.isEmpty()) {
                System.out.println("Order ID cannot be empty.");
                System.out.print("Order ID: ");
                orderId = scanner.nextLine();
            }

            boolean isOrderIdUnique = true;
            for (Order order : orders) {
                if (order.getOrderHeader().getOrderId().equals(orderId)) {
                    isOrderIdUnique = false;
                    break;
                }
            }

            if (!isOrderIdUnique) {
                System.out.println("Order ID must be unique. Please enter a different ID.");
                continue;
            }

            System.out.print("Order Date (YYYY-MM-DD): ");
            String orderDateStr = scanner.nextLine();

            LocalDate orderDate = null;
            boolean validDate = false;

            while (!validDate) {
                try {
                    orderDate = LocalDate.parse(orderDateStr);
                    validDate = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
                    System.out.print("Order Date (YYYY-MM-DD): ");
                    orderDateStr = scanner.nextLine();
                }
            }

            String customerName = "";

            while (customerName.isEmpty()) {
                System.out.print("Customer Name: ");
                customerName = scanner.nextLine();

                if (customerName.isEmpty()) {
                    System.out.println("Customer Name cannot be empty.");
                }
            }

            OrderHeader orderHeader = new OrderHeader(orderId, orderDate, customerName);
            List<OrderDetail> orderDetails = new ArrayList<>();

            while (true) {
                System.out.println("Enter order detail:");

                System.out.print("Order Detail ID: ");
                String orderDetailId = scanner.nextLine();

                while (orderDetailId.isEmpty()) {
                    System.out.println("Order Detail ID cannot be empty.");
                    System.out.print("Order Detail ID: ");
                    orderDetailId = scanner.nextLine();
                }

                boolean isOrderDetailIdUnique = true;
                for (OrderDetail orderDetail : orderDetails) {
                    if (orderDetail.getOrderDetailId().equals(orderDetailId)) {
                        isOrderDetailIdUnique = false;
                        break;
                    }
                }

                if (!isOrderDetailIdUnique) {
                    System.out.println("Order Detail ID must be unique. Please enter a different ID.");
                    continue;
                }

                String flowerId = "";
                Flower selectedFlower = null;

                while (true) {
                    System.out.print("Flower ID: ");
                    flowerId = scanner.nextLine();

                    while (flowerId.isEmpty()) {
                        System.out.println("Flower ID cannot be empty.");
                        System.out.print("Flower ID: ");
                        flowerId = scanner.nextLine();
                    }

                    if (isIdUnique(flowerId)) {
                        System.out.println("Invalid Flower ID. Please enter a valid Flower ID.");
                        continue;
                    }

                    selectedFlower = null;
                    for (Flower flower : flowers) {
                        if (flower.getFlowerID().equals(flowerId)) {
                            selectedFlower = flower;
                            break;
                        }
                    }

                    if (selectedFlower == null) {
                        System.out.println("Flower with ID " + flowerId + " does not exist.");
                        continue;
                    }

                    break;
                }
                System.out.print("Quantity: ");
                int quantity = 0;

                try {
                    quantity = Integer.parseInt(scanner.nextLine());

                    if (quantity <= 0) {
                        System.out.println("Quantity must be a positive integer.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for the quantity.");
                    continue;
                }

                double flowerCost = quantity * selectedFlower.getUnitPrice();

                OrderDetail orderDetail = new OrderDetail(orderDetailId, flowerId, quantity, flowerCost);
                orderDetails.add(orderDetail);
                // Add the created OrderDetail object to the list

                System.out.print("Add another order detail? (Y/N): ");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("N")) {
                    break;
                }
            }

            Order order = new Order(orderHeader, orderDetails);
            orders.add(order);
            // Add the created Order object to the list

            System.out.print("Add another order? (Y/N): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
    }

   public void displayOrders() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter the start date (YYYY-MM-DD): ");
    String startDateStr = scanner.nextLine();

    System.out.println("Enter the end date (YYYY-MM-DD): ");
    String endDateStr = scanner.nextLine();

    System.out.println("LIST ORDERS FROM " + startDateStr + " TO " + endDateStr);
    System.out.println("No.  Order Id  Order Date   Customer       FlowerCount   Order Total");

    LocalDate startDate = LocalDate.parse(startDateStr);
    LocalDate endDate = LocalDate.parse(endDateStr);

    int orderCount = 0;
    int totalFlowerCount = 0;
    double totalOrderTotal = 0;

    for (Order order : orders) {
        LocalDate orderDate = order.getOrderHeader().getOrderDate();

        if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
            orderCount++;
            String orderId = order.getOrderHeader().getOrderId();
            String customerName = order.getOrderHeader().getCustomerName();
            int flowerCount = 0;
            double orderTotal = 0;

            for (OrderDetail orderDetail : order.getOrderDetails()) {
                flowerCount += orderDetail.getQuantity();
                orderTotal += orderDetail.getFlowerCost();
            }

            totalFlowerCount += flowerCount;
            totalOrderTotal += orderTotal;

            System.out.printf("%-5d%-10s%-15s%-20s%-15d$ %-10.2f%n",
                    orderCount, orderId, orderDate, customerName, flowerCount, orderTotal);
        }
    }

    if (orderCount == 0) {
        System.out.println("No orders found.");
    } else {
        System.out.printf("Total                                        %-15d$ %-10.2f%n", totalFlowerCount, totalOrderTotal);
    }
}

    public void sortOrders() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the field to sort by (order id, order date, customer name): ");
        String field = scanner.nextLine();

        System.out.println("Enter the sort order (ASC, DESC): ");
        String sortOrder = scanner.nextLine();

        // Convert the set of orders to a list
        List<Order> orderList = new ArrayList<>(orders);

        // Create a comparator based on the selected field
        Comparator<Order> comparator = null;

        switch (field.toLowerCase()) {
            case "order id":
                comparator = Comparator.comparing(order -> order.getOrderHeader().getOrderId());
                break;
            case "order date":
                comparator = Comparator.comparing(order -> order.getOrderHeader().getOrderDate());
                break;
            case "customer name":
                comparator = Comparator.comparing(order -> order.getOrderHeader().getCustomerName());
                break;
            default:
                System.out.println("Invalid field. Sorting aborted.");
                return;
        }

        // Sort the orders based on the selected comparator and sort order
        if (sortOrder.equalsIgnoreCase("asc")) {
            Collections.sort(orderList, comparator);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            Collections.sort(orderList, comparator.reversed());
        } else {
            System.out.println("Invalid sort order. Sorting aborted.");
            return;
        }

        System.out.println("LIST OF ORDERS");
        System.out.println("Sorted by: " + field);
        System.out.println("Sort order: " + sortOrder);
        System.out.println("No.    Order Id    Order Date    Customer       FlowerCount   Order Total");

        int orderCount = 0;
        int totalFlowerCount = 0;
        double totalOrderTotal = 0;

        for (Order order : orderList) {
            orderCount++;
            String orderId = order.getOrderHeader().getOrderId();
            LocalDate orderDate = order.getOrderHeader().getOrderDate();
            String customerName = order.getOrderHeader().getCustomerName();
            int flowerCount = 0;
            double orderTotal = 0;

            for (OrderDetail orderDetail : order.getOrderDetails()) {
                flowerCount += orderDetail.getQuantity();
                orderTotal += orderDetail.getFlowerCost();
            }

            totalFlowerCount += flowerCount;
            totalOrderTotal += orderTotal;

            System.out.printf("%-5d%-10s%-15s%-20s%-15d$ %-10.2f%n",
                    orderCount, orderId, orderDate, customerName, flowerCount, orderTotal);
        }

        System.out.printf("Total                                        %-15d$ %-10.2f%n", totalFlowerCount, totalOrderTotal);
    }

public void saveData() {
    try {
        // Save flowers to flowers.dat
        FileOutputStream flowerFile = new FileOutputStream(FLOWERS_FILE);
        ObjectOutputStream flowerOutput = new ObjectOutputStream(flowerFile);

        for (Flower flower : flowers) {
            String flowerData = flower.toString();
            flowerOutput.writeObject(flowerData);
        }

        flowerOutput.close();
        flowerFile.close();

        // Save orders to orders.dat
        FileOutputStream orderFile = new FileOutputStream(ORDERS_FILE);
        ObjectOutputStream orderOutput = new ObjectOutputStream(orderFile);

        for (Order order : orders) {
            String orderData = order.toString();
            orderOutput.writeObject(orderData);
        }

        orderOutput.close();
        orderFile.close();

        System.out.println("Data saved successfully.");
    } catch (IOException e) {
        System.out.println("Error saving data: " + e.getMessage());
    }
}


public void loadData() {
    try {
        // Load flowers from flowers.dat
        FileInputStream flowerFile = new FileInputStream(FLOWERS_FILE);
        ObjectInputStream flowerInput = new ObjectInputStream(flowerFile);

        Object flowerData = flowerInput.readObject();
        if (flowerData instanceof Set) {
            flowers = (Set<Flower>) flowerData;
        } else {
            System.out.println("Invalid data format in flowers.dat");
        }

        flowerInput.close();
        flowerFile.close();

        // Load orders from orders.dat
        FileInputStream orderFile = new FileInputStream(ORDERS_FILE);
        ObjectInputStream orderInput = new ObjectInputStream(orderFile);

        Object orderData = orderInput.readObject();
        if (orderData instanceof Set) {
            orders = (Set<Order>) orderData;
        } else {
            System.out.println("Invalid data format in orders.dat");
        }

        orderInput.close();
        orderFile.close();

        System.out.println("Data loaded successfully.");

        // Display the contents of flowers.dat
        System.out.println("Contents of flowers.dat:");
        for (Flower flower : flowers) {
            System.out.println(flower.toString());
        }

        // Display the contents of orders.dat
        System.out.println("Contents of orders.dat:");
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    } catch (IOException e) {
        System.out.println("Error loading data: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.out.println("Error loading data: Class not found.");
    }
}
public void quitProgram() {
    Scanner scanner = new Scanner(System.in);
    boolean confirmedExit = false;

    while (!confirmedExit) {
        System.out.println("Are you sure you want to quit? (Y/N)");
        String userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("Y")) {
            saveData();
            System.out.println("Exiting the program...");
            System.exit(0);
        } else if (userInput.equalsIgnoreCase("N")) {
            System.out.println("Returning to the main menu...");
            confirmedExit = true; // Set flag to exit the loop and return to the main menu
        } else {
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
        }
    }
}


}
