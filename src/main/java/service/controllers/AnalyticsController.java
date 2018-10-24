package service.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.SprocketCustomerManagement;
import service.models.Customer;
import service.models.analytics.MostSprocketsOrdered;

@RestController
@CrossOrigin
@RequestMapping(AnalyticsController.ANALYTICS_URL)
public class AnalyticsController {
	public static final String ANALYTICS_URL = "/analytics";
	
	@Autowired
	SprocketCustomerManagement management;
	
	@RequestMapping(value = "/customers/ordered-most", method = RequestMethod.GET)
	public ResponseEntity<MostSprocketsOrdered> getCustomersWhoOrderedMost(
				@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
				@RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
		
		MostSprocketsOrdered result = management.getCustomersWhoOrderedMost(from, to);
		
		if (result != null)
			return ResponseEntity.status(HttpStatus.OK).body(result);
		else
			return ResponseEntity.notFound().build();
	}
}
