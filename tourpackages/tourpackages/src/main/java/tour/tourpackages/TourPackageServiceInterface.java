package tour.tourpackages;


import java.util.List;

public interface TourPackageServiceInterface {
    
    TourPackage addPackage(TourPackage travelPackage);

    void deletePackage(Long id);

    List<TourPackage> getAllPackages();

    TourPackage getPackageById(Long id);

    List<TourPackage> filterPackages(String destination, Double minPrice, Double maxPrice, Integer minDays, Integer maxDays);

    List<TourPackage> searchPackages(String keyword);

 TourPackage updatePackage(Long id, TourPackage updatedPackage);
}
