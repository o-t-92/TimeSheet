package nauka.timesheet.repository;

import nauka.timesheet.domain.Department;
import nauka.timesheet.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDepartment(Department department);
}
