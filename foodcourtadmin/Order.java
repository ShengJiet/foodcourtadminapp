package my.edu.utar.foodcourtadmin;

import java.util.List;

public class Order implements Comparable<Order> {
    private String id;
    private List<Item> items;
    private long timestamp;

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public Order(String id, List<Item> items, long timestamp) {
        this.id = id;
        this.items = items;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Order other) {
        return Long.compare(this.timestamp, other.timestamp);
    }

    public static class Item {
        private String itemName;
        private int quantity;

        public Item() {
            // Default constructor required for calls to DataSnapshot.getValue(Item.class)
        }

        public Item(String itemName, int quantity) {
            this.itemName = itemName;
            this.quantity = quantity;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
