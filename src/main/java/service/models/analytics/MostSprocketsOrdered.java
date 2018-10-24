package service.models.analytics;

import java.time.LocalDateTime;
import java.util.List;

import service.models.Customer;

/**
 * Class to return who ordered the most sprockets, how many, and what the given date range was
 */
public class MostSprocketsOrdered {
	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private int quantity = 0;
	private List<Customer> customers;
	
	public LocalDateTime getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}
	
	public LocalDateTime getToDate() {
		return toDate;
	}
	
	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
}
