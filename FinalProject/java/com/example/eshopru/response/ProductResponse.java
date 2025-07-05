package com.example.eshopru.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private String brand;

	public void setBrand(String brand) {
		this.brand = brand;
	}
}
