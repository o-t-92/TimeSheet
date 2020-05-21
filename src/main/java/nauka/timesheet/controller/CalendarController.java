package nauka.timesheet.controller;

import nauka.timesheet.domain.Department;
import nauka.timesheet.domain.dto.DeptMonthTimeSheetDto;
import nauka.timesheet.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("timesheet/{year}/{month}")
    ResponseEntity<DeptMonthTimeSheetDto> monthTimeSheet(@PathVariable("year") Integer year,
                                                         @PathVariable("month") Integer month,
                                                         @RequestParam(name = "dept_id") Department department) {
        if (department == null) {
            return ResponseEntity.notFound().build();
        }
        DeptMonthTimeSheetDto timeSheet = calendarService.getTimeSheet(department, YearMonth.of(year, month));
        return ResponseEntity.ok(timeSheet);
    }

    @GetMapping("prod_calendar/{year}/{month}")
    Map<LocalDate, String> productionCalendar(@PathVariable("year") Integer year,
                                              @PathVariable("month") Integer month) {
        return calendarService.getProductionCalendar(YearMonth.of(year, month));
    }


}
