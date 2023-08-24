package pageObject;

import java.util.ArrayList;
import java.util.Objects;

public class Item {
	String name;
	String size;
	String color;
	int quantity=1;
	float price;
	float totalPrice;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float f) {
		this.price = f;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice() {
		this.totalPrice = getPrice()*quantity;
	}
	

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Item other = (Item) obj;
        return quantity == other.quantity &&
               Objects.equals(name, other.name) &&
               Objects.equals(color, other.color) &&
               Objects.equals(size, other.size)&&
               Objects.equals(price, other.price) &&
               Objects.equals(totalPrice, other.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, size, quantity);
    }
    
    public static ArrayList<Item> addItem(Item newItem, ArrayList<Item> addedItems) {
		for(Item item:addedItems) {
			if(item.getName().equals(newItem.getName())
					&&item.getColor().equals(newItem.getColor())
					&&item.getSize().equals(newItem.getSize())) {
				item.setQuantity(item.getQuantity()+1);
				return addedItems;
			}
		}
		addedItems.add(newItem);
		return addedItems;
}
}

