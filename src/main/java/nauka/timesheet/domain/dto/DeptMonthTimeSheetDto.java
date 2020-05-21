package nauka.timesheet.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nauka.timesheet.domain.Department;

import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
@Getter
public class DeptMonthTimeSheetDto {
    private final Department department;
    private final YearMonth yearMonth;
    private final List<EmplWithMarksDto> emplsWithMarks;
}
