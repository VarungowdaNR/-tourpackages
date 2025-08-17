package tour.tourpackages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {

    List<TourPackage> findByDestinationContainingIgnoreCase(String destination);

    List<TourPackage> findByPriceBetween(double minPrice, double maxPrice);

    List<TourPackage> findByDurationBetween(int minDays, int maxDays);

    List<TourPackage> findByDestinationContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword1, String keyword2);
}
