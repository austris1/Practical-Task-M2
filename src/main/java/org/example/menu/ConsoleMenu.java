package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;
import org.example.util.util;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner =  new Scanner(System.in);
    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start(){
        boolean running = true;

        while (running) {
            printMenu();

            int choice = validNumber();

            switch (choice) {
                case 1 -> addAnimal();
                case 2 -> printAnimals(shelter.getAllAnimals());
                case 3 -> findSpecies();
                case 4 -> printAnimals(shelter.findAvailableAnimals());
                case 5 -> adoptAnimal();
                case 6 -> printAnimals(shelter.sortByName());
                case 7 -> printAnimals(shelter.sortByAge());
                case 8 -> printStatistics();
                case 9 -> printAdoptionHistory();
                case 0 -> running = false;
                default -> System.out.println("Unknown option.");
            }
        }
    }

    private void findSpecies(){
        System.out.print("Species: ");
        String species = validStringInput();
        printAnimals(shelter.findBySpecies(species));
    }

    private void adoptAnimal(){
        System.out.print("Animal ID: ");
        String id = scanner.nextLine();
        System.out.print("Adopter name: ");
        String adopterName = validStringInput();
        shelter.markAsAdopted(id,adopterName);
    }

    private void addAnimal(){
        System.out.print("Species: ");
        String species = validStringInput();
        System.out.print("Name: ");
        String name = validStringInput();
        System.out.print("Age: ");
        int age = validNumber();

        if (species.equals("Dog")) {
            shelter.addAnimal(new Dog(new AnimalId(), name, age));
        } else if (species.equals("Cat")) {
            shelter.addAnimal(new Cat(new AnimalId(), name, age));
        } else if (species.equals("Bird")) {
            shelter.addAnimal(new Bird(new AnimalId(), name, age));
        } else if (species.equals("Rabbit")) {
            shelter.addAnimal(new Rabbit(new AnimalId(), name, age));
        } else {
            System.out.println("Unknown species.");
        }
    }

    private String validStringInput(){
        String stringInput = scanner.nextLine();
        if (stringInput.isEmpty()){
            System.out.println("Input cannot be empty");
            return validStringInput();
        }
        return stringInput;
    }

    private int validNumber(){
        String input = scanner.nextLine();
        if (input.isEmpty()){
            System.out.println("Input cannot be empty");
            return validNumber();
        }

        int number;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input must be a number");
            return validNumber();
        }

        if (number < 0){
            System.out.println("Number cannot be negative");
            return validNumber();
        }
        return number;
    }

    private void printAnimals(List<Animal> animals){
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    private void printAdoptionHistory(){
        List<AdoptionRecord> history = shelter.getAdoptionHistory();
        if (history.isEmpty()) {
            System.out.println("No adoptions yet.");
        }
        for (AdoptionRecord record : history) {
            System.out.println(record.animal().getName() + " | " + record.adoptionDate() + " | adopted by " + record.adopterName());
        }
    }

    private void printStatistics(){
        List<Animal> animals = shelter.getAllAnimals();

        System.out.println("Average age: " + util.averageAge(animals));
        System.out.println("Oldest animal: " + util.findOldest(animals));

        Map<String, Integer> counts = util.speciesCounts(animals);
        for (String species : counts.keySet()) {
            System.out.println(species + ": " + counts.get(species));
        }

    }

    private void printMenu(){
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                6. Sort animals by name
                7. Sort animals by age
                8. Statistics
                9. Adoption history
                0. Exit
                """);
    }
}
