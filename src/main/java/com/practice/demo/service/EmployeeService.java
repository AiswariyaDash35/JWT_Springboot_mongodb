package com.practice.demo.service;

import com.practice.demo.entitiy.Employee;
import com.practice.demo.request.CreateRequest;

import java.util.List;

public interface EmployeeService {

    public Employee createEmployee(CreateRequest request);
    public List<Employee> getEmployeesForCurrentUser();
    public Employee getEmployeeById(String id);
    public Employee updateEmployee(String id,CreateRequest request);
    public void deleteEmployee(String id);
}
