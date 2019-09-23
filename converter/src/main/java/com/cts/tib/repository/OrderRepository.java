package com.cts.tib.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.cts.tib.entity.Order;

@Repository
public interface OrderRepository extends CassandraRepository<Order,String> {
    //
    List<Order> findByType(String type);

    Order save(Order order);
    List<Order> findAll();

}