package com.example.userstore.service;

import com.example.userstore.model.User;
import com.example.userstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.userstore.dto.UserDTO;
import com.example.userstore.dto.AddressDTO;
import com.example.userstore.dto.CompanyDTO;
import com.example.userstore.dto.GeoDTO;
import com.example.userstore.model.Address;
import com.example.userstore.model.Company;
import com.example.userstore.model.Geo;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        assertThat(userService.findAllUsers()).hasSize(1);
    }

    @Test
    void findUserById_returnsUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThat(userService.findUserById(1L)).contains(user);
    }

    @Test
    void createUser_throwsIfUsernameExists() {
        User user = new User();
        user.setUsername("test");
        when(userRepository.existsByUsername("test")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    @Test
    void createUser_throwsIfEmailExists() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@example.com");
        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    @Test
    void createUser_savesUser() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@example.com");
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertThat(userService.createUser(user)).isEqualTo(user);
    }

    @Test
    void toUserDTO_mapsAllFields() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPhone("123-456-7890");
        user.setWebsite("example.com");
        Address address = new Address();
        address.setCity("Test City");
        Geo geo = new Geo();
        geo.setLat("1.23");
        geo.setLng("4.56");
        address.setGeo(geo);
        user.setAddress(address);
        Company company = new Company();
        company.setName("TestCo");
        user.setCompany(company);
        UserDTO dto = userService.toUserDTO(user);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Test User");
        assertThat(dto.getAddress().getCity()).isEqualTo("Test City");
        assertThat(dto.getAddress().getGeo().getLat()).isEqualTo("1.23");
        assertThat(dto.getCompany().getName()).isEqualTo("TestCo");
    }

    @Test
    void toAddressDTO_nullSafe() {
        assertThat(userService.toAddressDTO(null)).isNull();
    }

    @Test
    void toCompanyDTO_nullSafe() {
        assertThat(userService.toCompanyDTO(null)).isNull();
    }

    @Test
    void toGeoDTO_nullSafe() {
        assertThat(userService.toGeoDTO(null)).isNull();
    }

    @Test
    void findAllUserDTOs_mapsList() {
        User user = new User();
        user.setId(2L);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertThat(userService.findAllUserDTOs()).hasSize(1);
        assertThat(userService.findAllUserDTOs().get(0).getId()).isEqualTo(2L);
    }

    @Test
    void findUserDTOById_mapsOptional() {
        User user = new User();
        user.setId(3L);
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        assertThat(userService.findUserDTOById(3L)).isPresent();
        assertThat(userService.findUserDTOById(3L).get().getId()).isEqualTo(3L);
    }
} 