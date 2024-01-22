package com.luv2code.springboot.cruddemo.contoller;

import com.luv2code.springboot.cruddemo.EmployeeApplication;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import com.luv2code.springboot.cruddemo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String getAllEmployees(Model model){

        //getting employees from DB
        List<Employee> listOfEmployees = employeeService.findAll();

        //adding employee list to model container
        model.addAttribute("listOfEmployees", listOfEmployees);

        return "employees/list-employees";
    }



    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){

        Employee employee = new Employee();

        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }


    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){

        employeeService.save(employee);

        //redirecting to prevent duplicate submissions
        return "redirect:/employee/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model model){


        Employee employee = employeeService.findById(id);

        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("employeeId") int id, Model model){

        employeeService.deleteById(id);

        return "redirect:/employee/list";
    }



}
