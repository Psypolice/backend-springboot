package ru.sharov.tasklist.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sharov.tasklist.backendspringboot.entity.Priority;
import ru.sharov.tasklist.backendspringboot.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prority")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityRepository priorityRepository;

    @GetMapping("/test")
    public List<Priority> get() {
        return Optional.of(priorityRepository.findAll()).orElseThrow(null);
    }
}
