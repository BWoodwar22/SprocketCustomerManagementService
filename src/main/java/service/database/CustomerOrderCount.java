package service.database;

import service.models.Customer;

/**
 * An object to return a Customer along with their order count from the database.
 */
public class CustomerOrderCount {
	private Customer customer;
	private int orderCount;
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getOrderCount() {
		return orderCount;
	}
	
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
}
