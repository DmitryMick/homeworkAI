package com.example.userstore.service;

import com.example.userstore.model.User;
import com.example.userstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.userstore.dto.UserDTO;
import com.example.userstore.dto.AddressDTO;
import com.example.userstore.dto.CompanyDTO;
import com.example.userstore.dto.GeoDTO;
import com.example.userstore.model.Address;
import com.example.userstore.model.Company;
import com.example.userstore.model.Geo;

/**
 * Service class for User entity business operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Find all users.
     */
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        log.debug("Finding all users");
        return userRepository.findAll();
    }

    /**
     * Find user by ID.
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        log.debug("Finding user by ID: {}", id);
        return userRepository.findById(id);
    }

    /**
     * Find user by username.
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        log.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Find user by email.
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    /**
     * Find users by name containing the given string.
     */
    @Transactional(readOnly = true)
    public List<User> findUsersByNameContaining(String name) {
        log.debug("Finding users by name containing: {}", name);
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Find users by city.
     */
    @Transactional(readOnly = true)
    public List<User> findUsersByCity(String city) {
        log.debug("Finding users by city: {}", city);
        return userRepository.findByCity(city);
    }

    /**
     * Create a new user.
     */
    public User createUser(User user) {
        log.debug("Creating new user: {}", user.getUsername());

        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Update an existing user.
     */
    public User updateUser(Long id, User userDetails) {
        log.debug("Updating user with ID: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        // Check if username is being changed and if it already exists
        if (!existingUser.getUsername().equals(userDetails.getUsername()) &&
                userRepository.existsByUsername(userDetails.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userDetails.getUsername());
        }

        // Check if email is being changed and if it already exists
        if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDetails.getEmail());
        }

        // Update fields
        existingUser.setName(userDetails.getName());
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setWebsite(userDetails.getWebsite());
        existingUser.setCompany(userDetails.getCompany());

        return userRepository.save(existingUser);
    }

    /**
     * Delete a user by ID.
     */
    public void deleteUser(Long id) {
        log.debug("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }

    /**
     * Map User entity to UserDTO.
     */
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setWebsite(user.getWebsite());
        dto.setAddress(toAddressDTO(user.getAddress()));
        dto.setCompany(toCompanyDTO(user.getCompany()));
        return dto;
    }

    public AddressDTO toAddressDTO(Address address) {
        if (address == null) return null;
        AddressDTO dto = new AddressDTO();
        dto.setStreet(address.getStreet());
        dto.setSuite(address.getSuite());
        dto.setCity(address.getCity());
        dto.setZipcode(address.getZipcode());
        dto.setGeo(toGeoDTO(address.getGeo()));
        return dto;
    }

    public CompanyDTO toCompanyDTO(Company company) {
        if (company == null) return null;
        CompanyDTO dto = new CompanyDTO();
        dto.setName(company.getName());
        dto.setCatchPhrase(company.getCatchPhrase());
        dto.setBs(company.getBs());
        return dto;
    }

    public GeoDTO toGeoDTO(Geo geo) {
        if (geo == null) return null;
        GeoDTO dto = new GeoDTO();
        dto.setLat(geo.getLat());
        dto.setLng(geo.getLng());
        return dto;
    }

    /**
     * Find all users as DTOs.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findAllUserDTOs() {
        return findAllUsers().stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    /**
     * Find user by ID as DTO.
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserDTOById(Long id) {
        return findUserById(id).map(this::toUserDTO);
    }
} 