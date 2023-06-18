/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlowerManagement;

import java.util.Scanner;

/**
 *
 * @author 84878
 */
public class FlowerStoreManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        FlowerStore flo = new FlowerStore();
        
        while (true) {
            System.out.println("Flower Management Program");
            System.out.println("-----------------------------");
            System.out.println("1. Create a flower");
            System.out.println("2. Find a flower");
            System.out.println("3. Update a flower");
            System.out.println("4. Delete a flower");
            System.out.println("5. Add an Order");
            System.out.println("6. Display an Order");
            System.out.println("7. Sort Order list");
            System.out.println("8. Save data");
            System.out.println("9. Load data");
            System.out.println("10. Quit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    flo.addFlower();
                    break;
                case 2:
                    flo.findFlower();
                    break;
                case 3:
                    flo.updateFlower();
                    break;
                case 4:
                    flo.deleteFlower();
                    break;
                case 5:
                    flo.addOrder();
                    break;
                case 6:
                    flo.displayOrders();
                    break;
                case 7:
                    flo.sortOrders();
                    break;
                case 8:
                    flo.saveData();
                    break;
                case 9:
                    flo.loadData();
                    break;
                case 10:
                    flo.quitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
            if (choice == 10) {
                break; // Exit the loop and end the program
            }
        }

        sc.close();
    }
}

