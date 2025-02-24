package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService theEmployeeService) { employeeService = theEmployeeService; }

    @GetMapping("/list")
    public String listEmployees(Model theModel) {
        // get the employees from db
        List<Employee> theEmployee = employeeService.findAll();
        // add to the spring model
        theModel.addAttribute("employees", theEmployee);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        // create model attribute to bind from data
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);
        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);
        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {
        // delete the employee
        employeeService.deleteById(theId);

        // redirect to /employee/list
        return "redirect:/employees/list";
    }
}
