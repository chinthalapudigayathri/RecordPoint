package com.gayathri.projects.service;

import com.gayathri.projects.entity.Employee;
import com.gayathri.projects.repository.EmployeeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employee", key = "#id")
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @CacheEvict(value = {"employees", "employee"}, allEntries = true)
    public Employee createEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword())); // encrypt password
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = {"employees", "employee"}, allEntries = true)
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);
        existing.setUsername(updatedEmployee.getUsername());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setRoles(updatedEmployee.getRoles());
        if (updatedEmployee.getPassword() != null) {
            existing.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
        }
        return employeeRepository.save(existing);
    }

    //tells Spring to remove cache entries after this method executes.
    @CacheEvict(value = {"employees", "employee"}, allEntries = true)
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }


}