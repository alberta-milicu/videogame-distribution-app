package com.example.videogame_distribution_app.service;

import com.example.videogame_distribution_app.entity.Tester;
import com.example.videogame_distribution_app.entity.Publisher;
import com.example.videogame_distribution_app.entity.Customer;
import com.example.videogame_distribution_app.entity.User;
import com.example.videogame_distribution_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Tester tester;
    private Publisher publisher;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        tester = new Tester();
        tester.setId(1);
        tester.setUsername("TesterUser");
        tester.setPassword("password123");
        tester.setEmail("tester@example.com");
        tester.setJoinDate(LocalDate.of(2025, 1, 22));
        tester.setCompany("TestCompany");
        tester.setSalary(50000.00);

        publisher = new Publisher();
        publisher.setId(2);
        publisher.setUsername("PublisherUser");
        publisher.setPassword("password456");
        publisher.setEmail("publisher@example.com");
        publisher.setJoinDate(LocalDate.of(2025, 1, 22));
        publisher.setWebsite("www.publisher.com");
        publisher.setRating(5);

        customer = new Customer();
        customer.setId(3);
        customer.setUsername("CustomerUser");
        customer.setPassword("password789");
        customer.setEmail("customer@example.com");
        customer.setJoinDate(LocalDate.of(2025, 1, 22));
        customer.setMembershipType("Gold");
        customer.setPoints(100);
    }

    @Test
    public void testFindUsersWithUsernameLike() {
        when(userRepository.findUsersWithUsernameLike("User")).thenReturn(Arrays.asList(tester, publisher, customer));

        List<User> users = userService.findUsersWithUsernameLike("User");

        assertNotNull(users);
        assertEquals(3, users.size());
        assertTrue(users.contains(tester));
        assertTrue(users.contains(publisher));
        assertTrue(users.contains(customer));

        verify(userRepository, times(1)).findUsersWithUsernameLike("User");
    }

    @Test
    public void testCreateTester() {
        when(userRepository.save(any(Tester.class))).thenReturn(tester);

        Tester createdTester = (Tester) userService.createUser(tester);

        assertNotNull(createdTester);
        assertEquals("TesterUser", createdTester.getUsername());
        assertEquals("TestCompany", createdTester.getCompany());
        assertEquals(50000.00, createdTester.getSalary());
        verify(userRepository, times(1)).save(tester);
    }

    @Test
    public void testCreatePublisher() {
        when(userRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher createdPublisher = (Publisher) userService.createUser(publisher);

        assertNotNull(createdPublisher);
        assertEquals("PublisherUser", createdPublisher.getUsername());
        assertEquals("www.publisher.com", createdPublisher.getWebsite());
        assertEquals(5, createdPublisher.getRating());
        verify(userRepository, times(1)).save(publisher);
    }

    @Test
    public void testCreateCustomer() {
        when(userRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = (Customer) userService.createUser(customer);

        assertNotNull(createdCustomer);
        assertEquals("CustomerUser", createdCustomer.getUsername());
        assertEquals("Gold", createdCustomer.getMembershipType());
        assertEquals(100, createdCustomer.getPoints());
        verify(userRepository, times(1)).save(customer);
    }


    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(tester, publisher, customer));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(3, users.size());
        assertTrue(users.contains(tester));
        assertTrue(users.contains(publisher));
        assertTrue(users.contains(customer));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(0, users.size());
        verify(userRepository, times(1)).findAll();
    }


    @Test
    public void testGetUserById_Found() {
        when(userRepository.findById(1)).thenReturn(Optional.of(tester));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(tester, result.get());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1);
    }


    @Test
    public void testUpdateUser_NotFound() {
        User updatedUser = new Customer();
        updatedUser.setId(4); // Non-existing ID
        updatedUser.setUsername("UpdatedCustomer");
        updatedUser.setEmail("updated@example.com");
        ((Customer) updatedUser).setMembershipType("Platinum");
        ((Customer) updatedUser).setPoints(200);

        when(userRepository.findById(4)).thenReturn(Optional.empty());

        User result = userService.updateUser(4, updatedUser);

        assertNull(result);
        verify(userRepository, times(0)).save(updatedUser);
    }


    @Test
    public void testDeleteUser_Found() {
        doNothing().when(userRepository).deleteById(3);

        userService.deleteUser(3);

        verify(userRepository, times(1)).deleteById(3);
    }

    @Test
    public void testDeleteUser_NotFound() {
        doThrow(new IllegalArgumentException("User not found")).when(userRepository).deleteById(4);

        try {
            userService.deleteUser(4);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("User not found", e.getMessage());
        }
        verify(userRepository, times(1)).deleteById(4);
    }
}
