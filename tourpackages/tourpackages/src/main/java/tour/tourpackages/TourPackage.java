package tour.tourpackages;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tour_packages")
public class TourPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name; // <-- Added field for package name

    @NotBlank(message = "Destination cannot be empty")
    private String destination;

    @Positive(message = "Duration must be greater than zero")
    private int duration; // in days

    @Positive(message = "Price must be greater than zero")
    private double price;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String itinerary;

    
    //@Column(length = 1000)
    //private String imageUrl; // <-- Added field for image URL
  @ElementCollection
private List<String> imageUrls = new ArrayList<>();

    // Constructors
    public TourPackage() {
    }

    public TourPackage(String name, String destination, int duration, double price, String description, String itinerary, String imageUrl, List<String> imageUrls) {
        this.name = name;
        this.destination = destination;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.itinerary = itinerary;
        this.imageUrls = imageUrls;

        //this.imageUrl = imageUrl;
    }
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() { // <-- Getter for name
        return name;
    }

    public void setName(String name) { // <-- Setter for name
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }
   // public String getImageUrl() {
     //   return imageUrl;
   // }

    //public void setImageUrl(String imageUrl) {
      //  this.imageUrl = imageUrl;
    //}
 public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }


    @Override
    public String toString() {
        return "TravelPackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", itinerary='" + itinerary + '\'' +
                ", imageUrls='" + imageUrls + '\'' +
                '}';
    }
}