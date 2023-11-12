package ru.sharov.tasklist.backendspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharov.tasklist.backendspringboot.entity.Category;
import ru.sharov.tasklist.backendspringboot.repository.CategoryRepository;
import ru.sharov.tasklist.backendspringboot.search.CategorySearchValues;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/all")
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {
        var NOT_ACCEPTABLE = checkCategory(category);
        if (NOT_ACCEPTABLE != null) return NOT_ACCEPTABLE;
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        var NOT_ACCEPTABLE = checkCategory(category);
        if (NOT_ACCEPTABLE != null) return NOT_ACCEPTABLE;
        return ResponseEntity.ok(categoryRepository.saveAndFlush(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            return new ResponseEntity("category with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryRepository.findById(id).get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            return new ResponseEntity("category with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
        return new ResponseEntity("Category was deleted", HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {
        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getText()));
    }

    private static ResponseEntity checkCategory(Category category) {
        if (isNotBlank(category.getTitle())) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (isBlank(category.getTitle())) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }


}
