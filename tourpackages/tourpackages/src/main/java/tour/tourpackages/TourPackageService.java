package tour.tourpackages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourPackageService implements TourPackageServiceInterface {

    @Autowired
    private TourPackageRepository repo;

    @Override
    public TourPackage addPackage(TourPackage pkg) {
        return repo.save(pkg);
    }

   @Override
public void deletePackage(Long id) {
    if (!repo.existsById(id)) {
        throw new ResourceNotFoundException("Tour package with id " + id + " not found.");
    }
    repo.deleteById(id);
}

    @Override
    public List<TourPackage> getAllPackages() {
        return repo.findAll();
    }

    @Override
    public TourPackage getPackageById(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tour package with id " + id + " not found."));
    }
@Override
public List<TourPackage> filterPackages(String destination, Double minPrice, Double maxPrice, Integer minDays, Integer maxDays) {
    List<TourPackage> filtered = repo.findAll().stream().filter(pkg -> {
        boolean match = true;
        if (destination != null && !pkg.getDestination().toLowerCase().contains(destination.toLowerCase())) {
            match = false;
        }
        if (minPrice != null && pkg.getPrice() < minPrice) {
            match = false;
        }
        if (maxPrice != null && pkg.getPrice() > maxPrice) {
            match = false;
        }
        if (minDays != null && pkg.getDuration() < minDays) {
            match = false;
        }
        if (maxDays != null && pkg.getDuration() > maxDays) {
            match = false;
        }
        return match;
    }).collect(Collectors.toList());
    
    if (filtered.isEmpty()) {
        throw new ResourceNotFoundException("No tour packages found matching the filter criteria.");
    }
    return filtered;
}
      @Override
    public List<TourPackage> searchPackages(String keyword) {
        List<TourPackage> results = repo.findByDestinationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No tour packages found matching keyword: " + keyword);
        }
        return results;
    }
@Override
public TourPackage updatePackage(Long id, TourPackage updatedPackage) {
    TourPackage existing = getPackageById(id);
    if (existing == null) return null;

    existing.setName(updatedPackage.getName());
    existing.setDestination(updatedPackage.getDestination());
    existing.setDuration(updatedPackage.getDuration());
    existing.setPrice(updatedPackage.getPrice());
    existing.setDescription(updatedPackage.getDescription());
    existing.setItinerary(updatedPackage.getItinerary());
    //existing.setImageUrl(updatedPackage.getImageUrl()); // <-- update imageUrl
    existing.setImageUrls(updatedPackage.getImageUrls());


    return repo.save(existing);
}
}
