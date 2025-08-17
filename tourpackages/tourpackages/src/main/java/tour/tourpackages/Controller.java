package tour.tourpackages;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;

//import java.util.List;
//import java.util.Scanner;

public class Controller{
    
}

// @Component
// public class Controller implements CommandLineRunner {

//     @Autowired
//     private TourPackageService service;

//     @Override
//     public void run(String... args) throws Exception {
//         Scanner scanner = new Scanner(System.in);
//         int choice;

//         do {
//             System.out.println("\n=== Tour Package Console ===");
//             System.out.println("1. Add Package");
//             System.out.println("2. Delete Package");
//             System.out.println("3. View All Packages");
//             System.out.println("4. Filter by Price Range");
//             System.out.println("5. Filter by Destination");
//             System.out.println("6. Filter by Duration");
//             System.out.println("7. Search by Keyword");
//             System.out.println("8. Exit");
//             System.out.print("Choose: ");
            
//             // Read choice here
//             while (!scanner.hasNextInt()) {
//                 System.out.print("Please enter a valid number: ");
//                 scanner.next(); // discard invalid input
//             }
//             choice = scanner.nextInt();
//             scanner.nextLine(); // consume newline

//             switch (choice) {
//                 case 1:
//                     TourPackage newPackage = new TourPackage();

//                     // Destination validation (not empty)
//                     String destination;
//                     do {
//                         System.out.print("Enter Destination: ");
//                         destination = scanner.nextLine().trim();
//                         if (destination.isEmpty()) {
//                             System.out.println("Destination cannot be empty. Please enter again.");
//                         }
//                     } while (destination.isEmpty());
//                     newPackage.setDestination(destination);

//                     // Duration validation (int and non-negative)
//                     int duration;
//                     do {
//                         System.out.print("Enter Duration (in days): ");
//                         while (!scanner.hasNextInt()) {
//                             System.out.print("Invalid input. Enter a valid integer for duration: ");
//                             scanner.next();
//                         }
//                         duration = scanner.nextInt();
//                         scanner.nextLine();
//                         if (duration < 0) {
//                             System.out.println("Duration cannot be negative. Please enter again.");
//                         }
//                     } while (duration < 0);
//                     newPackage.setDuration(duration);

//                     // Price validation (double and non-negative)
//                     double price;
//                     do {
//                         System.out.print("Enter Price: ");
//                         while (!scanner.hasNextDouble()) {
//                             System.out.print("Invalid input. Enter a valid number for price: ");
//                             scanner.next();
//                         }
//                         price = scanner.nextDouble();
//                         scanner.nextLine();
//                         if (price < 0) {
//                             System.out.println("Price cannot be negative. Please enter again.");
//                         }
//                     } while (price < 0);
//                     newPackage.setPrice(price);

//                     System.out.print("Enter Description: ");
//                     newPackage.setDescription(scanner.nextLine());

//                     System.out.print("Enter Itinerary: ");
//                     newPackage.setItinerary(scanner.nextLine());

//                     service.addPackage(newPackage);
//                     System.out.println("Package Added!");
//                     break;

//                 case 2:
//                     long id;
//                     do {
//                         System.out.print("Enter Package ID to Delete: ");
//                         while (!scanner.hasNextLong()) {
//                             System.out.print("Invalid input. Enter a valid package ID: ");
//                             scanner.next();
//                         }
//                         id = scanner.nextLong();
//                         scanner.nextLine();
//                         if (id < 0) {
//                             System.out.println("Package ID cannot be negative. Please enter again.");
//                         }
//                     } while (id < 0);
//                     service.deletePackage(id);
//                     System.out.println("Package Deleted (if ID existed).");
//                     break;

//                 case 3:
//                     List<TourPackage> packages = service.getAllPackages();
//                     if (packages.isEmpty()) {
//                         System.out.println("No packages found.");
//                     } else {
//                         packages.forEach(System.out::println);
//                     }
//                     break;

//                 case 4:
//                     double minPrice, maxPrice;
//                     do {
//                         System.out.print("Enter Minimum Price: ");
//                         while (!scanner.hasNextDouble()) {
//                             System.out.print("Invalid input. Enter a valid minimum price: ");
//                             scanner.next();
//                         }
//                         minPrice = scanner.nextDouble();
//                         scanner.nextLine();
//                         if (minPrice < 0) {
//                             System.out.println("Minimum price cannot be negative. Please enter again.");
//                         }
//                     } while (minPrice < 0);

//                     do {
//                         System.out.print("Enter Maximum Price: ");
//                         while (!scanner.hasNextDouble()) {
//                             System.out.print("Invalid input. Enter a valid maximum price: ");
//                             scanner.next();
//                         }
//                         maxPrice = scanner.nextDouble();
//                         scanner.nextLine();
//                         if (maxPrice < 0) {
//                             System.out.println("Maximum price cannot be negative. Please enter again.");
//                         } else if (maxPrice < minPrice) {
//                             System.out.println("Maximum price cannot be less than minimum price. Please enter again.");
//                             maxPrice = -1; // to continue the loop
//                         }
//                     } while (maxPrice < 0);

//                     List<TourPackage> priceFiltered = service.filterPackages(null, minPrice, maxPrice, null, null);
//                     priceFiltered.forEach(System.out::println);
//                     break;

//                 case 5:
//                     String filterDestination;
//                     do {
//                         System.out.print("Enter Destination: ");
//                         filterDestination = scanner.nextLine().trim();
//                         if (filterDestination.isEmpty()) {
//                             System.out.println("Destination cannot be empty. Please enter again.");
//                         }
//                     } while (filterDestination.isEmpty());
//                     List<TourPackage> destinationFiltered = service.filterPackages(filterDestination, null, null, null, null);
//                     if (destinationFiltered.isEmpty()) {
//                         System.out.println("No packages found for this destination.");
//                     } else {
//                         destinationFiltered.forEach(System.out::println);
//                     }
//                     break;
                
//                 case 6:
//                     int minDays, maxDays;
//                     do {
//                         System.out.print("Enter Minimum Duration (days): ");
//                         while (!scanner.hasNextInt()) {
//                             System.out.print("Invalid input. Enter a valid minimum duration: ");
//                             scanner.next();
//                         }
//                         minDays = scanner.nextInt();
//                         scanner.nextLine();
//                         if (minDays < 0) {
//                             System.out.println("Minimum duration cannot be negative. Please enter again.");
//                         }
//                     } while (minDays < 0);

//                     do {
//                         System.out.print("Enter Maximum Duration (days): ");
//                         while (!scanner.hasNextInt()) {
//                             System.out.print("Invalid input. Enter a valid maximum duration: ");
//                             scanner.next();
//                         }
//                         maxDays = scanner.nextInt();
//                         scanner.nextLine();
//                         if (maxDays < 0) {
//                             System.out.println("Maximum duration cannot be negative. Please enter again.");
//                         } else if (maxDays < minDays) {
//                             System.out.println("Maximum duration cannot be less than minimum duration. Please enter again.");
//                             maxDays = -1; // to continue loop
//                         }
//                     } while (maxDays < 0);

//                     List<TourPackage> durationFiltered = service.filterPackages(null, null, null, minDays, maxDays);
//                     durationFiltered.forEach(System.out::println);
//                     break;

//                 case 7:
//                     System.out.print("Enter keyword (destination/description): ");
//                     String keyword = scanner.nextLine();
//                     List<TourPackage> keywordFiltered = service.searchPackages(keyword);
//                     keywordFiltered.forEach(System.out::println);
//                     break;
              
//                 case 8:
//                 System.out.print("Enter Package ID to Update: ");
//                 while (!scanner.hasNextLong()) {
//                 System.out.print("Invalid input. Enter a valid package ID: ");
//                 scanner.next();
//                 }
//                 long updateId = scanner.nextLong();
//                 scanner.nextLine();

//                 TourPackage existingPackage = service.getPackageById(updateId);
//                 if (existingPackage == null) {
//                 System.out.println("Package not found.");
//                 break;
//                 }

//                 TourPackage updatedPackage = new TourPackage();

//                 System.out.print("Enter new Destination (current: " + existingPackage.getDestination() + "): ");
//                 String newDestination = scanner.nextLine().trim();
//                 updatedPackage.setDestination(newDestination.isEmpty() ? existingPackage.getDestination() : newDestination);

//                 System.out.print("Enter new Duration (current: " + existingPackage.getDuration() + "): ");
//                 String durationInput = scanner.nextLine().trim();
//                 int newDuration = durationInput.isEmpty() ? existingPackage.getDuration() : Integer.parseInt(durationInput);
//                 updatedPackage.setDuration(newDuration);

//                 System.out.print("Enter new Price (current: " + existingPackage.getPrice() + "): ");
//                 String priceInput = scanner.nextLine().trim();
//                 double newPrice = priceInput.isEmpty() ? existingPackage.getPrice() : Double.parseDouble(priceInput);
//                 updatedPackage.setPrice(newPrice);

//                 System.out.print("Enter new Description (current: " + existingPackage.getDescription() + "): ");
//                 String newDescription = scanner.nextLine().trim();
//                 updatedPackage.setDescription(newDescription.isEmpty() ? existingPackage.getDescription() : newDescription);

//                 System.out.print("Enter new Itinerary (current: " + existingPackage.getItinerary() + "): ");
//                 String newItinerary = scanner.nextLine().trim();
//                 updatedPackage.setItinerary(newItinerary.isEmpty() ? existingPackage.getItinerary() : newItinerary);

//                 TourPackage result = service.updatePackage(updateId, updatedPackage);
//                 if (result != null) {
//                  System.out.println("Package Updated Successfully.");
//                 } else {
//                 System.out.println("Failed to update package.");
//                 }
//              break;



//                 case 9:
//                     System.out.println("Exiting...");
//                     scanner.close(); 
//                     return;

//                 default:
//                     System.out.println("Invalid choice.");
//             }


//         } while (true);
//     }
// }
