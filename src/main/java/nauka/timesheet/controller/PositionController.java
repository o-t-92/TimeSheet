package nauka.timesheet.controller;

import nauka.timesheet.domain.Position;
import nauka.timesheet.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/position")
@RestController
public class PositionController extends AbstractRestController<Position, PositionRepository> {
    @Autowired
    public PositionController(PositionRepository repository) {
        super(repository);
    }
}
