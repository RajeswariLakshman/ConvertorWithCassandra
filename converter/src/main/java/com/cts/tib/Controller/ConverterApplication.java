package com.cts.tib.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.cts.tib.repository.OrderRepository;

@SpringBootApplication
@EnableAutoConfiguration()
@ComponentScan(basePackages = "com.java.*")

public class ConverterApplication {
	@Autowired
	private OrderRepository orderRepository;
	
	public static void main(String[] args) {
		try {
		SpringApplication.run(ConverterApplication.class, args);
		}
		catch(Exception e) {
			System.out.println("main "+e.getMessage());
		}
	}

}
