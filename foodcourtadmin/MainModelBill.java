package my.edu.utar.foodcourtadmin;

import java.util.HashMap;
import java.util.Map;

public class MainModelBill {
    private String dateTime;
    private String tableNumber;
    private double tax;
    private String totalPrice;
    private Map<String, Item> items = new HashMap<>();

    public MainModelBill() {
        // Default constructor required for calls to DataSnapshot.getValue(MainModelBill.class)
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }

    public static class Item {
        private String itemName;
        private int quantity;
        private double price;
        private double subtotal;

        public Item() {
            // Default constructor required for calls to DataSnapshot.getValue(Item.class)
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }
}
