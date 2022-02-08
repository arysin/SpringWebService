package org.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    Entity findOne(@PathVariable String name) throws EntityNotFoundException {

        return repository.findById(name).orElseThrow(() -> new EntityNotFoundException(name));
    }

    @PostMapping("/entities")
    Entity create(@RequestBody Entity newEntity) {
        return repository.save(newEntity);
    }

    @PutMapping("/entities/{name}")
    Entity update(@RequestBody Entity newEntity, @PathVariable String name) throws EntityNotFoundException {

        return repository.findById(name)
                .map(entity -> {
                    entity.setName(newEntity.getName());
                    entity.setLabel(newEntity.getLabel());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException(name));
    }

    @DeleteMapping("/entities/{name}")
    void deleteEmployee(@PathVariable String name) {
        repository.deleteById(name);
    }
}
