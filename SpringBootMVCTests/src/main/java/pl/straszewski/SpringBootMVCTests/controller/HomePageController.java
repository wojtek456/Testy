package pl.straszewski.SpringBootMVCTests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.straszewski.SpringBootMVCTests.model.Employee;
import pl.straszewski.SpringBootMVCTests.repo.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wojtek on 2017-06-11.
 */
@Controller
public class HomePageController {
    @Autowired
    EmployeeRepository employeeRepository;
    Employee employee = new Employee();
    List<Employee> lista = new ArrayList<Employee>();

    @RequestMapping(value = "/")
    public String home(Model model) {

        return "index";

    }
}
