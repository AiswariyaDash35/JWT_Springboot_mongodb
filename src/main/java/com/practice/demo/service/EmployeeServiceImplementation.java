package com.practice.demo.service;

import com.practice.demo.entitiy.CustomeUserDetails;
import com.practice.demo.entitiy.Employee;
import com.practice.demo.entitiy.User;
import com.practice.demo.repository.EmployeeRepository;
import com.practice.demo.request.CreateRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository,PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee createEmployee(CreateRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));

        //User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomeUserDetails currentUserDetails= (CustomeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentUserDetails.getUser();
        employee.setUser(currentUser);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(String id, CreateRequest request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesForCurrentUser() {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomeUserDetails currentUserDetails = (CustomeUserDetails) authentication.getPrincipal();
        User currentUser = currentUserDetails.getUser();
        return employeeRepository.findByUser(currentUser);
    }
}
