package service.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.SprocketCustomerManagement;
import service.models.Order;

@RestController
@CrossOrigin
@RequestMapping(OrderController.ORDERS_URL)
public class OrderController {
	public static final String ORDERS_URL = CustomerController.CUSTOMERS_URL + "/{customerId}" + "/orders";
	
	@Autowired
	SprocketCustomerManagement management;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Order>> getOrders(@PathVariable("customerId") int customerId,
												@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
												@RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
		
		List<Order> orders = management.getCustomerOrders(customerId, from, to);
		
		if (orders != null)
			return ResponseEntity.status(HttpStatus.OK).body(orders);
		else
			return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Order> addOrder(@PathVariable("customerId") int customerId, @Valid @RequestBody Order order) {
		Order newOrder = management.addOrder(customerId, order);
		
		if (newOrder != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
		else
			return ResponseEntity.badRequest().build();
	}
}
