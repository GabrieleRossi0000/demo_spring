package com.example.demo_spring.controller;


import com.example.demo_spring.exception.ResourceNotFoundException;
import com.example.demo_spring.model.Employee;
import com.example.demo_spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/employees")
public class EmlpoyeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @GetMapping //return the list of all the employees
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    @PostMapping //create new employee
    public Employee createEmployee(@RequestBody Employee employee){

        return employeeRepo.save(employee);
    }

     @GetMapping("{id}")//get employee by id
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("The employee with the id: " + id + " does not exist"));

        return ResponseEntity.ok(employee);

    }

    @PutMapping("{id}")// update employee data
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("The employee with the id: " + id + " does not exist"));
        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());

        employeeRepo.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);

    }

    @DeleteMapping("{id}")// delete employee
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
        Employee employee =employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("The employee with the id: " + id + "does not exist"));
        employeeRepo.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
