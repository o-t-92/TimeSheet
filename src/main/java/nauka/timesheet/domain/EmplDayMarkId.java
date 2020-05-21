package nauka.timesheet.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EmplDayMarkId implements Serializable {
    private Employee employee;
    private LocalDate day;
}
