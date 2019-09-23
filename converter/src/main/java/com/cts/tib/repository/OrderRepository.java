package com.java.repository;

import com.java.entity.Order;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CassandraRepository<Order, String> {
    //
    @Query("select * from order where type = ?0")
    Iterable<Order> findByType(String type);

    void save(Order order, String orderId);

}