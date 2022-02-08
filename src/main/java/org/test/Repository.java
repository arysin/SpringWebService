package org.test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class Repository {

    private List<Entity> items = Stream.of("apple", "orange")
            .map(n -> new Entity(n, "Label for: "+n))
            .collect(Collectors.toList());
    
    public List<Entity> findAll() {
        return items;
    }

    public void deleteById(String name) {
        items.removeIf(e -> e.getName().equals(name));
    }

    public Entity save(Entity newEntity) {
        items.add(newEntity);
        return newEntity;
    }

    public Optional<Entity> findById(String name) {
        return items.stream().filter(e -> e.getName().equals(name)).findFirst();
    }

}
