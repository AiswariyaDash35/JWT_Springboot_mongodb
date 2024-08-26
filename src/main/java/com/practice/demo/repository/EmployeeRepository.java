package com.practice.demo.repository;

import com.practice.demo.entitiy.Employee;
import com.practice.demo.entitiy.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByUser(User user);
}
