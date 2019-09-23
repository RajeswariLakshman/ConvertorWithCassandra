package com.cts.tib.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(value = "Order_Management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@PrimaryKeyColumn(name = "orderId", ordinal = 0)
	private String orderId;

	@Column(value = "type")
	private String type;

	@Column(value = "status")
	private String status;

	@JsonCreator
	public void User(@JsonProperty("id") String orderId, @JsonProperty("type") String type) {
		this.orderId = orderId;
		this.type = type;
	}

	public String getOrderId() {
		// TODO Auto-generated method stub
		return this.orderId;
	}

}