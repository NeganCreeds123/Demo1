/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.time.LocalDate;

public class Flower implements Serializable{
    private String flowerID;
    private String name;
    private String description;
    private LocalDate importDate;
    private double unitPrice;
    private String category;

    // Constructor
    public Flower(String flowerID, String name, String description, LocalDate importDate, double unitPrice, String category) {
        this.flowerID = flowerID;
        this.name = name;
        this.description = description;
        this.importDate = importDate;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    // Getters and Setters
    public String getFlowerID() {
        return flowerID;
    }

    public void setFlowerID(String flowerID) {
        this.flowerID = flowerID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Flower ID: " + flowerID +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nImport Date: " + importDate +
                "\nUnit Price: " + unitPrice +
                "\nCategory: " + category;
    }
    
}
