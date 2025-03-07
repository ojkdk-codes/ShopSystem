import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class ShopSystem {
    private static HashMap<String, Double> shopInventory = new HashMap<>();
    private static HashMap<String, Integer> cart = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Predefined shop items
        shopInventory.put("Apple", 0.99);
        shopInventory.put("Banana", 0.59);
        shopInventory.put("Milk", 2.49);
        shopInventory.put("Bread", 1.99);

        while (true) {
            System.out.println("\nWelcome to the Shop! Choose an option:");
            System.out.println("1. View Items");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewItems();
                case 2 -> addItemToCart();
                case 3 -> viewCart();
                case 4 -> checkout();
                case 5 -> {
                    System.out.println("Thank you for visiting!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewItems() {
        System.out.println("\nItems available in the shop:");
        for (var entry : shopInventory.entrySet()) {
            System.out.println(entry.getKey() + " - $" + entry.getValue());
        }
    }

    private static void addItemToCart() {
        System.out.print("Enter the item name: ");
        String item = scanner.nextLine();

        if (shopInventory.containsKey(item)) {
            System.out.print("Enter quantity for " + item + ": ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (quantity > 0) {
                cart.put(item, cart.getOrDefault(item, 0) + quantity);
                System.out.println(quantity + " " + item + "(s) added to cart.");
            } else {
                System.out.println("Invalid quantity. Please enter a positive number.");
            }
        } else {
            System.out.println("Item not found in shop.");
        }
    }

    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
        } else {
            System.out.println("\nItems in your cart:");
            for (var entry : cart.entrySet()) {
                System.out.println(entry.getKey() + " x" + entry.getValue());
            }
        }
    }

    private static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to checkout.");
            return;
        }

        double total = 0;
        StringBuilder invoice = new StringBuilder();
        invoice.append("Invoice\n");
        invoice.append("=======================\n");

        System.out.println("\nFinal Order Summary:");
        for (var entry : cart.entrySet()) {
            String item = entry.getKey();
            int quantity = entry.getValue();
            double price = shopInventory.get(item);
            total += quantity * price;
            System.out.println(item + " x" + quantity + " - $" + (quantity * price) + " @ " + price + " ea");
            invoice.append(item).append(" x").append(quantity).append(" - $").append(quantity * price)
                    .append(" @ ").append(price).append(" ea").append("\n");
        }
        System.out.printf("Total Amount: $%.2f\n", total);
        invoice.append("=======================\n");
        invoice.append("Total Amount: $").append(total).append("\n");

        saveInvoice(invoice.toString());
        cart.clear();
        System.out.println("Thank you for shopping!");
    }

    private static void saveInvoice(String invoiceContent) {
        int invoiceNumber = 1;
        File invoiceFile;

        do {
            invoiceFile = new File("Invoice_" + invoiceNumber + ".txt");
            invoiceNumber++;
        } while (invoiceFile.exists());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(invoiceFile))) {
            writer.write(invoiceContent);
            System.out.println("Invoice saved as " + invoiceFile.getName());
        } catch (IOException e) {
            System.out.println("Error saving invoice: " + e.getMessage());
        }
    }
}