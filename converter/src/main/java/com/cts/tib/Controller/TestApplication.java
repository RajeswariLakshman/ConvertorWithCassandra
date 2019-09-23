package com.cts.tib.Controller;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class TestApplication {
	public  TestApplication(String cassandraHost, int cassandraPort, String keyspaceName) {
	    String m_cassandraHost = cassandraHost;
	    int m_cassandraPort = cassandraPort;
	    String m_keyspaceName = keyspaceName;

	    System.out.println("Connecting to {}:{}..."+cassandraHost+" "+cassandraPort);
	    Cluster cluster = Cluster.builder().withPort(m_cassandraPort).addContactPoint(cassandraHost).build();
	    Session session = cluster.connect(m_keyspaceName);
	    System.out.println("Connected.");
	}

	public static void main(String[] args) {
		TestApplication bdt = new TestApplication("localhost", 9042, "Test Cluster");
	}
}
