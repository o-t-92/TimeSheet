package nauka.timesheet.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "workday_start")
    private LocalTime workdayStart;

    @Column(name = "workday_end")
    private LocalTime workdayEnd;

    @Column(name = "working_hours")
    private Integer workingHours;
}
