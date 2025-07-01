import java.io.*;
import java.util.*;

public class PetAdoptionSystem {

    static ArrayList<String> availablePets = new ArrayList<>(); // availablePets stores pets up for adoption
    static String[] adoptedPets = new String[100]; // fixed size, stores names of adopted pets
    static int adoptedCount = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        int choice;
        do {  // keep showing menu til user selects exit
            System.out.println("\n--- Pet Adoption Management System ---");
            System.out.println("1. Add a new pet for adoption");
            System.out.println("2. View available pets");
            System.out.println("3. Adopt a pet");
            System.out.println("4. View adopted pets");
            System.out.println("5. Exit and Save");
            System.out.print("Enter your choice: ");
            choice = getValidInt();

            switch (choice) {
                case 1 -> addPet();
                case 2 -> viewAvailablePets();
                case 3 -> adoptPet();
                case 4 -> viewAdoptedPets();
                case 5 -> {
                    saveData();
                    System.out.println("Data saved. Exiting...");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);
    }

    
    public static int getValidInt() { // input validation (recursive method)
        try {
            return Integer.parseInt(scanner.nextLine()); // reads line, tries to convert to int
        } catch (NumberFormatException e) { // if input not a number, catch error and ask again
            System.out.print("Invalid input. Enter a number: ");
            return getValidInt();
        }
    }

    public static void addPet() {
        System.out.print("Enter pet name to add: ");
        String petName = scanner.nextLine().trim(); // trim white space and checks that input isnt empty
        if (!petName.isEmpty()) {
            availablePets.add(petName); // adds name to availablePets
            System.out.println(petName + " added to adoption list."); 
        } else {
            System.out.println("Pet name cannot be empty.");
        }
    }

    public static void viewAvailablePets() {
        System.out.println("--- Available Pets ---");
        if (availablePets.isEmpty()) {
            System.out.println("No pets currently available.");
        } else {
            for (int i = 0; i < availablePets.size(); i++) { // shows all current pets in availablePets list
                System.out.println((i + 1) + ". " + availablePets.get(i));
            }
        }
    }

    public static void adoptPet() {
        viewAvailablePets(); // show current pets
        if (availablePets.isEmpty()) return;

        System.out.print("Enter the number of the pet to adopt: ");
        int index = getValidInt() - 1;
        try {
            String adopted = availablePets.remove(index); // removes selected pet from list
            if (adoptedCount < adoptedPets.length) {
                adoptedPets[adoptedCount++] = adopted; // adds pet to adoptedPets and increases adoptedCount
                System.out.println(adopted + " has been adopted.");
            } else {
                System.out.println("Adopted list is full. Cannot add more.");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index. Please try again.");
        }
    }

    public static void viewAdoptedPets() {
        System.out.println("--- Adopted Pets ---");
        if (adoptedCount == 0) {
            System.out.println("No pets have been adopted yet.");
        } else {
            for (int i = 0; i < adoptedCount; i++) { // shows all pets in adoptedPets up to adoptedCount
                System.out.println((i + 1) + ". " + adoptedPets[i]);
            }
        }
    }

    public static void saveData() { // save data to files
        try (PrintWriter availableWriter = new PrintWriter("availablePets.txt");
             PrintWriter adoptedWriter = new PrintWriter("adoptedPets.txt")) {

            for (String pet : availablePets) {
                availableWriter.println(pet);
            }

            for (int i = 0; i < adoptedCount; i++) { // iterates over ArrayList to save each pet to file
                adoptedWriter.println(adoptedPets[i]);
            }

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    
    public static void loadData() { // load data to files
        try (BufferedReader availableReader = new BufferedReader(new FileReader("availablePets.txt"));
             BufferedReader adoptedReader = new BufferedReader(new FileReader("adoptedPets.txt"))) {

            String line;
            while ((line = availableReader.readLine()) != null) {
                availablePets.add(line);
            }

            while ((line = adoptedReader.readLine()) != null && adoptedCount < adoptedPets.length) {
                adoptedPets[adoptedCount++] = line;
            }

        } catch (IOException e) {
        }
    }
}
