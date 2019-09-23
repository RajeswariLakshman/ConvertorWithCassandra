package com.cts.tib.service;

import java.io.File;

import com.cts.tib.entity.Order;
import com.cts.tib.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import lombok.Getter;

@RestController
public class converterService {
	private static final Logger LOGGER = Logger.getLogger(converterService.class);

	@Autowired
	private OrderRepository orderRepository;

	/* not in use */
	@Autowired
	private CassandraOperations cassandraTemplate;

	


	@PostMapping(path = "/pushtodb", consumes = { "application/json" })
	public String pushToDb(@RequestBody String orderDetails) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Order order = mapper.readValue(orderDetails, Order.class);
			orderRepository.save(order);
			LOGGER.info("Count of orders " + orderRepository.count());
		} catch (Exception ex) {
			LOGGER.error("Error in cassandra " + ex.getMessage());
		}
		return "count of order " + orderRepository.count();
	}
}