package tour.tourpackages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TourpackagesControllerTests {

    @Mock
    private TourPackageService service;

    @InjectMocks
    private TourPackageController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Utility method to set id using reflection
    private void setId(TourPackage pkg, Long id) {
        try {
            Field idField = TourPackage.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(pkg, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddPackage() {
        TourPackage pkg = new TourPackage();
        pkg.setDestination("Goa");
        pkg.setPrice(10000.0);

        when(service.addPackage(pkg)).thenReturn(pkg);

        TourPackage result = controller.addPackage(pkg);

        assertNotNull(result);
        assertEquals("Goa", result.getDestination());
        verify(service, times(1)).addPackage(pkg);
    }

    @Test
    void testDeletePackage() {
        Long id = 1L;
        doNothing().when(service).deletePackage(id);

        ResponseEntity<String> response = controller.deletePackage(id);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Deleted successfully", response.getBody());
        verify(service, times(1)).deletePackage(id);
    }

    @Test
    void testGetAllPackages() {
        TourPackage pkg1 = new TourPackage();
        setId(pkg1, 1L);
        pkg1.setDestination("Goa");
        pkg1.setDuration(5);
        pkg1.setPrice(10000.0);
        pkg1.setDescription("desc");
        pkg1.setItinerary("itinerary");

        TourPackage pkg2 = new TourPackage();
        setId(pkg2, 2L);
        pkg2.setDestination("Mysore");
        pkg2.setDuration(3);
        pkg2.setPrice(5000.0);
        pkg2.setDescription("desc2");
        pkg2.setItinerary("itinerary2");

        List<TourPackage> packages = Arrays.asList(pkg1, pkg2);
        when(service.getAllPackages()).thenReturn(packages);

        List<TourPackage> result = controller.getAllPackages();

        assertEquals(2, result.size());
        assertEquals("Goa", result.get(0).getDestination());
        verify(service, times(1)).getAllPackages();
    }

 

    @Test
    void testUpdatePackage() {
        Long id = 1L;
        TourPackage updated = new TourPackage();
        setId(updated, id);
        updated.setDestination("Goa");
        updated.setDuration(7);
        updated.setPrice(12000.0);
        updated.setDescription("updated");
        updated.setItinerary("updated itinerary");

        when(service.updatePackage(eq(id), any(TourPackage.class))).thenReturn(updated);

        TourPackage result = controller.updatePackage(id, updated);

        assertNotNull(result);
        assertEquals(7, result.getDuration());
        assertEquals(12000.0, result.getPrice());
        verify(service, times(1)).updatePackage(eq(id), any(TourPackage.class));
    }


}