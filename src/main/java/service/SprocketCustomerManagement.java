package service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import service.database.CustomerOrderCount;
import service.database.DataSource;
import service.models.Customer;
import service.models.Order;
import service.models.analytics.MostSprocketsOrdered;

/**
 * Plan would be to put the business logic in this layer so it's not all in the controllers.
 * In this simplified case using the same model for both back end and returning for Customers and Orders, 
 * there isn't much to put here, but I decided to make up a return model for the analytics request.
 * 
 * If there had been more to do in this layer, I'd have broken this out into multiple classes.
 * For now I'm returning null if there was an issue caught at this level as opposed to some sort of 
 * status/exception message that could be forwarded to the user.
 */

@Component
public class SprocketCustomerManagement {
	
	@Autowired
	@Qualifier("mock")
	private DataSource dataSource;
	
	private LocalDateTime minDate = LocalDateTime.of(1900, Month.JANUARY, 01, 0, 0);
	
	/**
	 * Add a new Customer to the system
	 * 
	 * @param newCustomer
	 * @return the newly added Customer
	 */
	public Customer addCustomer(Customer newCustomer) {	
		return dataSource.addCustomer(newCustomer);
	}
	
	/**
	 * Update a Customer already in the system
	 * 
	 * @param customer
	 * @return the updated customer or null if no existing match was found 
	 */
	public Customer updateCustomer(Customer customer) {
		return dataSource.updateCustomer(customer);
	}
	
	/**
	 * Gets the Customer with the given customer id or null if no match
	 * 
	 * @param customerId
	 * @return 
	 */
	public Customer getCustomer(int customerId) {
		return dataSource.getCustomer(customerId);
	}
	
	/**
	 * Get a List of all Customers
	 * 
	 * @return
	 */
	public List<Customer> getCustomers() {
		return dataSource.getCustomers();
	}
	
	/**
	 * Add an Order for the Customer with the given id
	 * 
	 * @param customerId
	 * @param newOrder
	 * @return the created Order or null if there was an issue
	 */
	public Order addOrder(int customerId, Order newOrder) {
		Customer customer = dataSource.getCustomer(customerId); 
		
		if (customer == null
			|| newOrder.getQuantity() <= 0 
			|| newOrder.getOrderDate() == null 
			|| newOrder.getOrderDate().isBefore(minDate))
			return null;
		
		newOrder.setCustomer(customer);
		
		return dataSource.addOrder(customerId, newOrder);
	}
	
	/**
	 * Get the List of Orders made by a Customer
	 * 
	 * @param customerId
	 * @return
	 */
	public List<Order> getCustomerOrders(int customerId) {
		return getCustomerOrders(customerId, null, null);	
	}
	
	/**
	 * Get the List of Orders made by a Customer within a date range
	 * 
	 * @param customerId
	 * @param from
	 * @param to
	 * @return
	 */
	public List<Order> getCustomerOrders(int customerId, LocalDateTime from, LocalDateTime to) {
		if (from == null)
			from = minDate;
		if (to == null)
			to = LocalDateTime.now();
		
		return dataSource.getOrders(customerId, from, to);
	}
	
	/**
	 * Get a MostSprocketsOrdered detailing who purchased the most sprockets overall
	 * 
	 * @return
	 */
	public MostSprocketsOrdered getCustomersWhoOrderedMost() {
		return getCustomersWhoOrderedMost(null, null);
	}
	
	/**
	 * Get a MostSprocketsOrdered detailing who purchased the most sprockets within a date range
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public MostSprocketsOrdered getCustomersWhoOrderedMost(LocalDateTime from, LocalDateTime to) {
		if (from == null)
			from = minDate;
		if (to == null)
			to = LocalDateTime.now();

		List<CustomerOrderCount> customerCounts = dataSource.getCustomersWhoOrderedMost(from, to);
		MostSprocketsOrdered result = new MostSprocketsOrdered();
		
		if (customerCounts != null && !customerCounts.isEmpty()) {
			List<Customer> customers = customerCounts.stream().map(co -> co.getCustomer()).collect(Collectors.toList());
			result.setCustomers(customers);
			result.setQuantity(customerCounts.get(0).getOrderCount());
		}
		
		result.setFromDate(from);
		result.setToDate(to);
		
		return result;
	}
}
