package nauka.timesheet.domain.dto;

import lombok.Getter;
import nauka.timesheet.domain.Employee;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class EmplWithMarksDto {
    private final Long emplId;
    private final String fullName;
    private final String positionName;
    private final Map<LocalDate, String> marks;
    private final Map<String, Long> markCounter;

    public EmplWithMarksDto(Employee empl, Map<LocalDate, String> marks) {
        this.emplId = empl.getId();
        this.fullName = empl.getFirstName() + " " + empl.getLastName();
        this.positionName = empl.getPosition().getName();
        this.marks = marks;
        this.markCounter = marks.values().stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }
}
