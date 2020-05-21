package nauka.timesheet.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "holiday")
public class Holiday {
    @Id
    @Column(name = "holiday_date")
    private LocalDate date;

    @Column(name = "description")
    private String description;
}
