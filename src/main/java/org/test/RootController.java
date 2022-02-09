package org.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {
    @Autowired
    private Repository repository;

    RootController() {
    }

    @GetMapping("/entities")
    List<Entity> all() {
        return repository.findAll();
    }

    @GetMapping("/entities/{name}")
    Entity findOne(@PathVariable String name) {
        return repository.findById(name);
    }

    @PostMapping("/entities")
    Entity create(@RequestBody Entity newEntity) {
        return repository.create(newEntity);
    }

    @PutMapping("/entities/{name}")
    Entity update(@RequestBody Entity newEntity, @PathVariable String name) {
        if( ! newEntity.getName().equals(name) )
            throw new IllegalArgumentException("names don't match");
        
        return repository.update(newEntity);
    }

    @DeleteMapping("/entities/{name}")
    void deleteEmployee(@PathVariable String name) {
        repository.deleteById(name);
    }
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(EmptyResultDataAccessException exception) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }
}
