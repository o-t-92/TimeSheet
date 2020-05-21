package nauka.timesheet.repository;

import nauka.timesheet.domain.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, LocalDate> {
    List<Holiday> findAllByDateBetween(LocalDate from, LocalDate to);
}
