package com.java.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
public class Order {

  @PrimaryKeyColumn
  private String orderId;
  @Column
  private String type;

  public Order(String orderId, String type) {
    this.orderId = orderId;
    this.type = type;
  }

  @JsonCreator
  public void User(@JsonProperty("id") String orderId, @JsonProperty("type") String type) {
    this.orderId = orderId;
    this.type = type;
  }

}