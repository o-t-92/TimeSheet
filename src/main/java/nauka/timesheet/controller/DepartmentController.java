package nauka.timesheet.controller;

import nauka.timesheet.domain.Department;
import nauka.timesheet.domain.Employee;
import nauka.timesheet.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/department")
@RestController
public class DepartmentController extends AbstractRestController<Department, DepartmentRepository> {
    @Autowired
    public DepartmentController(DepartmentRepository repository) {
        super(repository);
    }

    @GetMapping("{id}/employees")
    public ResponseEntity<List<Employee>> listEmployees(@PathVariable("id") Department department) {
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department.getEmployees());
    }
}
