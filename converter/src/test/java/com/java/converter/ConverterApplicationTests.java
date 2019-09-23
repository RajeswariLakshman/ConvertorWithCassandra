package com.java.converter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.java.config.CassandraConfig;
import com.java.entity.Order;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)

@SpringBootTest
public class ConverterApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private CassandraAdminOperations adminTemplate;

	@Ignore
	public void contextLoads2() throws URISyntaxException {
		System.out.println("test started");
		final String baseUrl = "http://localhost:8080" + "/pushtodb";
		URI uri = new URI(baseUrl);
		String request = "{ orderId: 'P001',  type: 'Purchase' };";

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		// Verify request succeed
		Assert.assertEquals(201, result.getStatusCodeValue());
	}

	@Test
	public void contextLoads() throws URISyntaxException {
		System.out.println("test started");
		final String baseUrl = "http://localhost:8080" + "/verify";
		URI uri = new URI(baseUrl);
		// String request = "{ orderId: 'P001', type: 'Purchase' };";

		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		System.out.println("result " + result);
		// Verify request succeed
		Assert.assertEquals(201, result.getStatusCodeValue());
	}

	@BeforeClass
	public static void startCassandraEmbedded()
			throws ConfigurationException, TTransportException, IOException, InterruptedException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
		Session session = cluster.connect();
	}

	@AfterClass
	public static void stopCassandraEmbedded() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}

	@Before
	public void createTable() {
		adminTemplate.createTable(true, CqlIdentifier.cqlId("OM_TABLE"), Order.class, new HashMap<String, Object>());
	}
}
