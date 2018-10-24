package service.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import service.models.Customer;
import service.models.Order;

/**
 * Mock database implementation for storing the Customers and Orders
 */

@Service("mock")
public class MockDataSource implements DataSource {
	private int customerIndex = 1;
	private int orderIndex = 1;
	
	private HashMap<Integer, Customer> customers = new HashMap<>();
	private HashMap<Integer, ArrayList<Order>> orders = new HashMap<>();
	
	public MockDataSource() {
		//Create some initial test Customers and Orders
		seedData();
	}
	
	@Override
	public Customer addCustomer(Customer newCustomer) {

		newCustomer.setCustomerId(customerIndex);
		customers.put(customerIndex, newCustomer);
		
		orders.put(customerIndex, new ArrayList<Order>());
		
		customerIndex++;
		
		return newCustomer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		
		if (customers.containsKey(customer.getCustomerId())) {
			customers.put(customer.getCustomerId(), customer);
		}
		
		return customers.get(customer.getCustomerId());
	}

	@Override
	public List<Customer> getCustomers() {
		return customers.values().stream().collect(Collectors.toList());
	}

	@Override
	public Customer getCustomer(int customerId) {
		return customers.get(customerId);
	}
	
	@Override
	public List<Order> getOrders(int customerId, LocalDateTime from, LocalDateTime to) {
		return orders.get(customerId).stream()
				.filter(o -> o.getOrderDate().compareTo(from) >= 0)
				.filter(o -> o.getOrderDate().compareTo(to) <= 0)
				.sorted(Comparator.comparing(o -> o.getOrderDate())).collect(Collectors.toList());
	}

	@Override
	public List<CustomerOrderCount> getCustomersWhoOrderedMost(LocalDateTime from, LocalDateTime to) {
		int most = 0;
		ArrayList<CustomerOrderCount> results = new ArrayList<>();
		
		for (Integer customerId : orders.keySet()) {
			int sum = orders.get(customerId).stream()
					.filter(o -> o.getOrderDate().compareTo(from) >= 0)
					.filter(o -> o.getOrderDate().compareTo(to) <= 0)
					.mapToInt(o -> o.getQuantity()).sum();
			
			if (sum > most) {
				most = sum;
				results.clear();
			}
			
			if (sum == most) {
				CustomerOrderCount result = new CustomerOrderCount();
				result.setCustomer(customers.get(customerId));
				result.setOrderCount(sum);
				results.add(result);
			}
		}
		
		return results;
	}

	@Override
	public Order addOrder(int customerId, Order newOrder) {

		if (orders.containsKey(customerId)) {
			newOrder.setOrderId(orderIndex);
			orders.get(customerId).add(newOrder);
			orderIndex++;
			
			return newOrder;
		}
		
		return null;
	}

	private void seedData() {
		Customer customer1 = new Customer();
		customer1.setName("Test User 1");
		customer1.setAddress("123 Fake St, Springfield, IL, 55555");
		customer1.setEmail("testuser1@hotmail.com");
		customer1.setPhoneNumber("(555) 555-5555");
		addCustomer(customer1);
		
		Customer customer2 = new Customer();
		customer2.setName("Test User 2");
		customer2.setAddress("124 Fake St, Springfield, IL, 55555");
		customer2.setEmail("testuser2@hotmail.com");
		customer2.setPhoneNumber("(555) 555-6666");
		addCustomer(customer2);
		
		Order order1 = new Order();
		order1.setCustomer(customer1);
		order1.setOrderDate(LocalDateTime.of(2018, Month.JULY, 04, 0, 0));
		order1.setPrice(new BigDecimal("20.00"));
		order1.setQuantity(20);
		addOrder(customer1.getCustomerId(), order1);
		
		Order order2 = new Order();
		order2.setCustomer(customer2);
		order2.setOrderDate(LocalDateTime.of(2018, Month.AUGUST, 15, 0, 0));
		order2.setPrice(new BigDecimal("30.00"));
		order2.setQuantity(20);
		addOrder(customer2.getCustomerId(), order2);
	}
}
