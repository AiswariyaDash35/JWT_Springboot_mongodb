package com.practice.demo.controller;

import com.practice.demo.entitiy.Employee;
import com.practice.demo.request.CreateRequest;
import com.practice.demo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody CreateRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployeesForCurrentUser();
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody CreateRequest request) {
        Employee employee = employeeService.updateEmployee(employeeId, request);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
