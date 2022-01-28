package com.dci.exercises.mvcAnnotations.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employeeList;

    @Autowired
    public EmployeeController(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addEmployee(@RequestBody Employee employee) {
        employeeList.add(employee);
        return "employee added";
    }

    @GetMapping()
    @ResponseBody
    public List<Employee> listEmployees(@RequestParam(required = false) Integer max_number) {
        int breakPoint = (max_number != null && max_number < employeeList.size()) ? max_number : employeeList.size();
        List<Employee> result = employeeList.subList(0, breakPoint);
        return result;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Employee selectEmployee(@PathVariable String id) {
        for (Employee employee : employeeList) {
            if (id.equals(employee.getId())) { return employee; }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String removeEmployee(@PathVariable String id) {
        for (Employee employee : employeeList) {
            if (id.equals(employee.getId())) {
                employeeList.remove(employee);
                return "Employee with Id " + id + " deleted.";
            }
        }
        return "Employee not found";
    }
}
