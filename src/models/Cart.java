package models;

import java.util.ArrayList;
import java.util.List;

/*
The Cart class methods manage the collection of items in the cart.
*/
public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item); // Removes the exact object reference
    }

    public void removeItemById(int foodId) {
        items.removeIf(item -> item.getFoodId() == foodId); // Removes by foodId
    }

    // New method to calculate the total amount
    public double getTotalAmount() {
        double totalAmount = 0;
        for (CartItem item : items) {
            totalAmount += item.getTotalPrice();
        }
        return totalAmount;
    }
}
