package com.csc.service;

import com.csc.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;



public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    Page<Employee> findPaginated(int pageNo, int pageSize);
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}