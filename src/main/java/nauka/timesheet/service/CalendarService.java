package nauka.timesheet.service;

import nauka.timesheet.domain.Department;
import nauka.timesheet.domain.EmplDayMark;
import nauka.timesheet.domain.Employee;
import nauka.timesheet.domain.Holiday;
import nauka.timesheet.domain.dto.DeptMonthTimeSheetDto;
import nauka.timesheet.domain.dto.EmplWithMarksDto;
import nauka.timesheet.repository.EmplDayMarkRepository;
import nauka.timesheet.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static java.util.stream.Collectors.*;

@Service
public class CalendarService {
    private final EmplDayMarkRepository emplDayMarkRepository;
    private final HolidayRepository holidayRepository;

    private static final String DAY_TYPE_WORK = "workday";
    private static final String DAY_TYPE_WEEKEND = "weekend";
    private static final String DAY_TYPE_PRE_HOLIDAY = "pre-holiday";
    private static final String DAY_TYPE_HOLIDAY = "holiday";

    @Autowired
    public CalendarService(EmplDayMarkRepository emplDayMarkRepository, HolidayRepository holidayRepository) {
        this.emplDayMarkRepository = emplDayMarkRepository;
        this.holidayRepository = holidayRepository;
    }

    public DeptMonthTimeSheetDto getTimeSheet(Department department, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        List<Employee> employees = department.getEmployees();
        List<EmplDayMark> marks =
                emplDayMarkRepository.findAllByDayBetweenAndEmployeeIn(startDate, endDate, employees);

        Map<Employee, List<EmplDayMark>> emplMarksMap = employees.stream()
                .collect(toMap(e -> e, e -> new ArrayList<>()));

        for (EmplDayMark emplDayMark : marks) {
            emplMarksMap.get(emplDayMark.getEmployee()).add(emplDayMark);
        }

        List<EmplWithMarksDto> emplsWithMarks = emplMarksMap.entrySet().stream()
                .map(e -> {
                    Map<LocalDate, String> dayMarkMap = e.getValue().stream()
                            .collect(toMap(EmplDayMark::getDay, EmplDayMark::getMark,
                                    (a, b) -> a, TreeMap::new));
                    return new EmplWithMarksDto(e.getKey(), dayMarkMap);
                }).collect(toList());

        return new DeptMonthTimeSheetDto(department, yearMonth, emplsWithMarks);
    }

    public Map<LocalDate, String> getProductionCalendar(YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth().plusDays(1);

        List<LocalDate> holidays = holidayRepository.findAllByDateBetween(startDate, endDate)
                .stream().map(Holiday::getDate).collect(toList());

        Map<LocalDate, String> result = new LinkedHashMap<>(31);

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayType;
            if (holidays.contains(date)) {
                dayType = DAY_TYPE_HOLIDAY;
            } else if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                dayType = DAY_TYPE_WEEKEND;
            } else if (holidays.contains(date.plusDays(1))) {
                dayType = DAY_TYPE_PRE_HOLIDAY;
            } else {
                dayType = DAY_TYPE_WORK;
            }
            result.put(date, dayType);
        }

        return result;
    }
}
