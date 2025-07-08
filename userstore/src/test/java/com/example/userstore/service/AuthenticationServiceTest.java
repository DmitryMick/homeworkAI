package com.example.userstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.userstore.dto.AuthenticationRequest;
import com.example.userstore.dto.AuthenticationResponse;
import com.example.userstore.dto.RegisterRequest;
import com.example.userstore.model.AuthUser;
import com.example.userstore.repository.AuthUserRepository;
import com.example.userstore.security.JwtService;

class AuthenticationServiceTest {

	@Mock
	private AuthUserRepository authUserRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private AuthenticationService authenticationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void register_success() {
		RegisterRequest req = RegisterRequest.builder().name("Test").email("a@b.com").password("Password1").build();
		when(authUserRepository.existsByEmail("a@b.com")).thenReturn(false);
		when(passwordEncoder.encode(any())).thenReturn("hashed");
		when(authUserRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
		when(jwtService.generateToken(anyString())).thenReturn("token");
		AuthenticationResponse resp = authenticationService.register(req);
		assertThat(resp.getToken()).isEqualTo("token");
	}

	@Test
	void register_duplicateEmail() {
		RegisterRequest req = RegisterRequest.builder().name("Test").email("a@b.com").password("Password1").build();
		when(authUserRepository.existsByEmail("a@b.com")).thenReturn(true);
		assertThrows(IllegalArgumentException.class, () -> authenticationService.register(req));
	}

	@Test
	void authenticate_success() {
		AuthenticationRequest req = AuthenticationRequest.builder().email("a@b.com").password("Password1").build();
		AuthUser user = new AuthUser();
		user.setEmail("a@b.com");
		user.setPasswordHash("hashed");
		when(authUserRepository.findByEmail("a@b.com")).thenReturn(Optional.of(user));
		when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token");
		AuthenticationResponse resp = authenticationService.authenticate(req);
		assertThat(resp.getToken()).isEqualTo("token");
	}

	@Test
	void authenticate_userNotFound() {
		AuthenticationRequest req =
				AuthenticationRequest.builder().email("not@found.com").password("Password1").build();
		when(authUserRepository.findByEmail("not@found.com")).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(req));
	}

	@Test
	void loadUserByUsername_success() {
		AuthUser user = new AuthUser();
		user.setEmail("a@b.com");
		user.setPasswordHash("hashed");
		when(authUserRepository.findByEmail("a@b.com")).thenReturn(Optional.of(user));
		UserDetails details = authenticationService.loadUserByUsername("a@b.com");
		assertThat(details.getUsername()).isEqualTo("a@b.com");
		assertThat(details.getPassword()).isEqualTo("hashed");
	}

	@Test
	void loadUserByUsername_notFound() {
		when(authUserRepository.findByEmail("not@found.com")).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername("not@found.com"));
	}
} 