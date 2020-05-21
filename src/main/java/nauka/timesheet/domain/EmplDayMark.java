package nauka.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "empl_day_mark")
@IdClass(EmplDayMarkId.class)
public class EmplDayMark {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @Id
    @Column(name = "day")
    private LocalDate day;

    @Column(name = "mark_code")
    private String mark;
}
