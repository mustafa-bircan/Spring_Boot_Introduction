package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import com.workintech.fswebs17d1.exceptions.AnimalNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/animal")
public class AnimalController {

    private Map<Integer, Animal> animals = new HashMap<>();

    @Value("${course.name}")
    private String courseName;

    @Value("${project.developer.fullname}")
    private String developerName;


    @PostConstruct
    public void init() {
        animals.put(1, new Animal(1, "Lion"));
        animals.put(2, new Animal(2, "Elephant"));
        animals.put(3, new Animal(3, "Tiger"));
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return new ArrayList<>(animals.values());
    }

    @GetMapping("/{id}")
    public Animal getAnimal(@PathVariable Integer id) {
        return animals.get(id);
    }

    @PostMapping
    public void addAnimal(@RequestBody Animal animal) {
        animals.put(animal.getId(), animal);
    }


    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable Integer id, @RequestBody Animal animal) {
        if (!animals.containsKey(id)) {
            throw new AnimalNotFoundException("Animal with ID " + id + " not found.");
        }

        if (!animal.getId().equals(id)) {
            throw new IllegalArgumentException("ID in path and body must match.");
        }

        animals.put(id, animal);
        return animal;
    }


    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Integer id) {
        animals.remove(id);
    }
}
