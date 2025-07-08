package com.example.userstore.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.userstore.dto.UserDTO;
import com.example.userstore.model.User;
import com.example.userstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for User operations following JSONPlaceholder API structure.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	/**
	 * GET /users - Get all users
	 * Following JSONPlaceholder: https://jsonplaceholder.typicode.com/users
	 */
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		log.info("GET /users - Retrieving all users");
		List<UserDTO> users = userService.findAllUserDTOs();
		return ResponseEntity.ok(users);
	}

	/**
	 * GET /users/{id} - Get user by ID
	 * Following JSONPlaceholder: https://jsonplaceholder.typicode.com/users/1
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		log.info("GET /users/{} - Retrieving user by ID", id);
		return userService.findUserDTOById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * POST /users - Create a new user
	 * Following JSONPlaceholder: https://jsonplaceholder.typicode.com/guide/
	 */
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
		log.info("POST /users - Creating new user: {}", user.getUsername());
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.toUserDTO(createdUser));
	}

	/**
	 * PUT /users/{id} - Update user by ID
	 * Following JSONPlaceholder: https://jsonplaceholder.typicode.com/guide/
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
		log.info("PUT /users/{} - Updating user", id);
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(userService.toUserDTO(updatedUser));
	}

	/**
	 * PATCH /users/{id} - Partially update user by ID
	 * Following JSONPlaceholder: https://jsonplaceholder.typicode.com/guide/
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<UserDTO> patchUser(@PathVariable Long id, @RequestBody User userPatch) {
		log.info("PATCH /users/{} - Partially updating user", id);
		User existingUser = userService.findUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
		// Apply patches only to non-null fields
		if (userPatch.getName() != null) {
			existingUser.setName(userPatch.getName());
		}
		if (userPatch.getUsername() != null) {
			existingUser.setUsername(userPatch.getUsername());
		}
		if (userPatch.getEmail() != null) {
			existingUser.setEmail(userPatch.getEmail());
		}
		if (userPatch.getAddress() != null) {
			existingUser.setAddress(userPatch.getAddress());
		}
		if (userPatch.getPhone() != null) {
			existingUser.setPhone(userPatch.getPhone());
		}
		if (userPatch.getWebsite() != null) {
			existingUser.setWebsite(userPatch.getWebsite());
		}
		if (userPatch.getCompany() != null) {
			existingUser.setCompany(userPatch.getCompany());
		}
		User updatedUser = userService.updateUser(id, existingUser);
		return ResponseEntity.ok(userService.toUserDTO(updatedUser));
	}

	/**
	 * DELETE /users/{id} - Delete user by ID
	 * Following JSONPlaceholder: https://jsonplaceholder.typicode.com/guide/
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("DELETE /users/{} - Deleting user", id);
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * GET /users?name={name} - Get users by name containing
	 * Extended functionality beyond JSONPlaceholder
	 */
	@GetMapping(params = "name")
	public ResponseEntity<List<UserDTO>> getUsersByName(@RequestParam String name) {
		log.info("GET /users?name={} - Retrieving users by name", name);
		List<UserDTO> users = userService.findUsersByNameContaining(name)
				.stream()
				.map(userService::toUserDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(users);
	}

	/**
	 * GET /users?city={city} - Get users by city
	 * Extended functionality beyond JSONPlaceholder
	 */
	@GetMapping(params = "city")
	public ResponseEntity<List<UserDTO>> getUsersByCity(@RequestParam String city) {
		log.info("GET /users?city={} - Retrieving users by city", city);
		List<UserDTO> users =
				userService.findUsersByCity(city).stream().map(userService::toUserDTO).collect(Collectors.toList());
		return ResponseEntity.ok(users);
	}

	/**
	 * GET /users?username={username} - Get user by username
	 * Extended functionality beyond JSONPlaceholder
	 */
	@GetMapping(params = "username")
	public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username) {
		log.info("GET /users?username={} - Retrieving user by username", username);
		return userService.findUserByUsername(username)
				.map(userService::toUserDTO)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * GET /users?email={email} - Get user by email
	 * Extended functionality beyond JSONPlaceholder
	 */
	@GetMapping(params = "email")
	public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
		log.info("GET /users?email={} - Retrieving user by email", email);
		return userService.findUserByEmail(email)
				.map(userService::toUserDTO)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
} 