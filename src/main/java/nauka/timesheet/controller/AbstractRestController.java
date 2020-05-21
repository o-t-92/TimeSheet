package nauka.timesheet.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractRestController<T, R extends JpaRepository<T, ?>> {

    protected R repository;

    public AbstractRestController(R repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<T> list() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<T> getOne(@PathVariable("id") T obj) {
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public T add(@RequestBody T obj) {
        return repository.save(obj);
    }

    @PutMapping("{id}")
    public ResponseEntity<T> update(@PathVariable("id") T dbObj, @RequestBody T obj) {
        if (dbObj == null) {
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(obj, dbObj, "id");
        return ResponseEntity.ok(repository.save(dbObj));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") T dbObj) {
        repository.delete(dbObj);
    }
}
