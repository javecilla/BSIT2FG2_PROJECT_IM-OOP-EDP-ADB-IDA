package core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Cart class manages a collection of CartItem objects.
 * It provides methods to add, remove, and calculate the total cost of items in the cart.
 */
public class Cart {
    private List<CartItem> items; // List of CartItem objects representing the items in the cart

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
        items.remove(item); // Removes by exact object reference
    }

    public void removeItemById(int foodId) {
        items.removeIf(item -> item.getFoodId() == foodId); // Removes by foodId
    }

    public double getTotalAmount() {
        return items.stream()  
                    .mapToDouble(CartItem::getTotalPrice)  // Maps each CartItem to its total price
                    .sum();  // Sums up the total price of all CartItems
    }

    public void clearCart() {
        items.clear();
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }
}
