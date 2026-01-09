package com.gayathri.projects.service;

import com.gayathri.projects.entity.Employee;
import com.gayathri.projects.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        employeeService = new EmployeeService(employeeRepository, passwordEncoder);
    }

    @Test
    void testCreateEmployee() {
        Employee emp = Employee.builder()
                .Username("gayathri")
                .Password("plainpass")
                .Email("gayathri@example.com")
                .roles(Set.of("ROLE_USER"))
                .build();

        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArguments()[0]);

        Employee saved = employeeService.createEmployee(emp);

        assertNotNull(saved);
        assertTrue(passwordEncoder.matches("plainpass", saved.getPassword()));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById() {
        Employee emp = Employee.builder().id(1L).Username("test").build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

        Employee found = employeeService.getEmployeeById(1L);

        assertEquals("test", found.getUsername());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployee() {
        Employee existing = Employee.builder()
                .id(1L)
                .Username("olduser")
                .Password(passwordEncoder.encode("oldpass"))
                .Email("old@example.com")
                .roles(Set.of("ROLE_USER"))
                .build();

        Employee updated = Employee.builder()
                .Username("newuser")
                .Password("newpass")
                .Email("new@example.com")
                .roles(Set.of("ROLE_ADMIN"))
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArguments()[0]);

        Employee result = employeeService.updateEmployee(1L, updated);

        assertEquals("newuser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertTrue(passwordEncoder.matches("newpass", result.getPassword()));
        assertTrue(result.getRoles().contains("ROLE_ADMIN"));

        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(existing);
    }

}