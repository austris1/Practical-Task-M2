package org.example.shelter;

import org.example.model.AdoptionRecord;
import org.example.model.AdoptionStatus;
import org.example.model.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shelter<T extends Animal> {
    private final List<T> animals = new ArrayList<>();
    private final List<AdoptionRecord> adoptionHistory = new ArrayList<>();

    public void addAnimal(T animal) {
        animals.add(animal);
    }

    public List<T> getAllAnimals() {
        return animals;
    }

    public List<T> findBySpecies(String species) {
        List<T> result = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getSpecies().equals(species)) {
                result.add(animal);
            }
        }
        return result;
    }

    public List<T> findAvailableAnimals() {
        List<T> result = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE) {
                result.add(animal);
            }
        }
        return result;
    }

    public void markAsAdopted(String id, String adopterName) {
        for (T animal : animals) {
            if (animal.getId().toString().equals(id) && animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE) {
                animal.markAsAdopted();
                adoptionHistory.add(new AdoptionRecord(animal, LocalDate.now(), adopterName));
                return;
            }
        }
        System.out.println("No available animal with ID " + id);
    }

    public List<AdoptionRecord> getAdoptionHistory() {
        return adoptionHistory;
    }

    public List<T> sortByName() {
        List<T> result = new ArrayList<>(animals);
        result.sort(Comparator.comparing(Animal::getName));
        return result;
    }

    public List<T> sortByAge() {
        List<T> result = new ArrayList<>(animals);
        result.sort(Comparator.comparing(Animal::getAge));
        return result;
    }

}
