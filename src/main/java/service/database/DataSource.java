package service.database;

import java.time.LocalDateTime;
import java.util.List;

import service.models.Customer;
import service.models.Order;

/**
 * Interface for the data source for this application
 */

public interface DataSource {
	public Customer addCustomer(Customer newCustomer);
	public Customer updateCustomer(Customer customer);
	public List<Customer> getCustomers();
	public Customer getCustomer(int customerId);
	public Order addOrder(int customerId, Order newOrder);
	public List<Order> getOrders(int customerId, LocalDateTime begin, LocalDateTime end);
	public List<CustomerOrderCount> getCustomersWhoOrderedMost(LocalDateTime begin, LocalDateTime end);
}
