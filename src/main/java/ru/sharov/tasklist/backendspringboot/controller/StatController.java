package ru.sharov.tasklist.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sharov.tasklist.backendspringboot.entity.Stat;
import ru.sharov.tasklist.backendspringboot.repository.StatRepository;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StatController {

    private final StatRepository statRepository;

    private final long DEFAULT_ID = 1;

    @GetMapping("/stat")
    public ResponseEntity<Stat> findById() {
        return ResponseEntity.ok(statRepository.findById(DEFAULT_ID).get());
    }


}
