package nauka.timesheet.controller;

import nauka.timesheet.domain.Employee;
import nauka.timesheet.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends AbstractRestController<Employee, EmployeeRepository> {
    @Autowired
    public EmployeeController(EmployeeRepository repository) {
        super(repository);
    }
}
