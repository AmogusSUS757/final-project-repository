package com.example.eshopru.request;

import lombok.Data;

@Data
public class ProductRequest {
	private String brand;
	private String type;
	private String category;
	private int price;
	private int rating;
	public int getRating() {
		return rating;
	}
	private String image_url;
	private Long ownerId;
	private String description;
}
