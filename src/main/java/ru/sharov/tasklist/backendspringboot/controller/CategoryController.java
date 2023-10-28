package ru.sharov.tasklist.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sharov.tasklist.backendspringboot.entity.Category;
import ru.sharov.tasklist.backendspringboot.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/test")
    public List<Category> get() {
        return Optional.of(categoryRepository.findAll()).orElseThrow(null);
    }
}
