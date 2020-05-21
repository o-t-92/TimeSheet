package nauka.timesheet.repository;

import nauka.timesheet.domain.EmplDayMark;
import nauka.timesheet.domain.EmplDayMarkId;
import nauka.timesheet.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmplDayMarkRepository extends JpaRepository<EmplDayMark, EmplDayMarkId> {
    List<EmplDayMark> findAllByDayBetweenAndEmployeeIn(LocalDate start, LocalDate end, List<Employee> empls);
}
