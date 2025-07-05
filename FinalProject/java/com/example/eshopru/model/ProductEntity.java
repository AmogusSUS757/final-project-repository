package com.example.eshopru.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "This field cannot be empty.")
	private String brand;
	@NotBlank(message = "This field cannot be empty.")
	private String type;
	@NotBlank(message = "This field cannot be empty.")
	private String category;
	private int price;
	@Min(value = 0)
	@Max(value = 5)
	private int rating;
	public int getRating() {
		return rating;
	}
	private String image_url;
	@NotNull(message = "This field cannot be empty.")
	private Long ownerId;
	
	private String description;
}
