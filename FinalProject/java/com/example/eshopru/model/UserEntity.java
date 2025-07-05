package com.example.eshopru.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "This field cannot be empty.")
	private String username;
	@NotNull(message = "This field cannot be empty.")
	private String password;
	private boolean enabled;
	@NotBlank(message = "This field cannot be empty.")
	private String email;
	@NotBlank(message = "This field cannot be empty.")
	@Pattern(regexp = "^(\\+994|00994|0)(50|51|55|70|77|99)\\d{7}$", message = "Invalid Azerbaijani phone number")
	private String phone_number;
}