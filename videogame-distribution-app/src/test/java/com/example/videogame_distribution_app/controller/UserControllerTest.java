package com.example.videogame_distribution_app.controller;

import com.example.videogame_distribution_app.entity.Customer;
import com.example.videogame_distribution_app.entity.Tester;
import com.example.videogame_distribution_app.entity.Publisher;
import com.example.videogame_distribution_app.service.UserService;
import com.example.videogame_distribution_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private Customer customer;
    private Publisher publisher;
    private Tester tester;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Initialize Customer, Publisher, and Tester entities
        customer = new Customer();
        customer.setId(2);
        customer.setUsername("testcustomer");
        customer.setEmail("customer@example.com");

        publisher = new Publisher();
        publisher.setId(3);
        publisher.setUsername("testpublisher");
        publisher.setEmail("publisher@example.com");

        tester = new Tester();
        tester.setId(4);
        tester.setUsername("testtester");
        tester.setEmail("tester@example.com");
    }

    @Test
    public void testGetUserById_Found() throws Exception {
        // Mock the userService method to return the user
        when(userService.getUserById(2)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testcustomer"));

        verify(userService, times(1)).getUserById(2);
    }


    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(2);

        mockMvc.perform(delete("/api/users/2"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(2);
    }
}