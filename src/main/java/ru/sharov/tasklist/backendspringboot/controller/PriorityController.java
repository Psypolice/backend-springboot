package ru.sharov.tasklist.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharov.tasklist.backendspringboot.entity.Priority;
import ru.sharov.tasklist.backendspringboot.repository.PriorityRepository;
import ru.sharov.tasklist.backendspringboot.search.PrioritySearchValues;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequestMapping("/prority")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityRepository priorityRepository;

    @GetMapping("/all")
    public List<Priority> get() {
        return priorityRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        var NOT_ACCEPTABLE = checkPriority(priority);
        if (NOT_ACCEPTABLE != null) return NOT_ACCEPTABLE;
        return ResponseEntity.ok(priorityRepository.save(priority));
    }


    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority) {
        var NOT_ACCEPTABLE = checkPriority(priority);
        if (NOT_ACCEPTABLE != null) return NOT_ACCEPTABLE;
        return ResponseEntity.ok(priorityRepository.saveAndFlush(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {
        if (priorityRepository.findById(id).isEmpty()) {
            return new ResponseEntity("priority with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityRepository.findById(id).get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (priorityRepository.findById(id).isEmpty()) {
            return new ResponseEntity("priority with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        priorityRepository.findById(id).ifPresent(priorityRepository::delete);
        return new ResponseEntity("Priority was deleted", HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getText()));
    }

    private static ResponseEntity checkPriority(Priority priority) {
        if (isNotBlank(priority.getId().toString())) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (isBlank(priority.getTitle())) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (isBlank(priority.getColor())) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }


}
