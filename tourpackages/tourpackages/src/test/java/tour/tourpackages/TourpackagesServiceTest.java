package tour.tourpackages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TourpackagesServiceTest {

    @Mock
    TourPackageRepository repo;

    @InjectMocks
    TourPackageService service;

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

        when(repo.save(pkg)).thenReturn(pkg);

        TourPackage result = service.addPackage(pkg);

        assertNotNull(result);
        assertEquals("Goa", result.getDestination());
        verify(repo, times(1)).save(pkg);
    }

    @Test
    void testGetAllPackages() {
        TourPackage pkg1 = new TourPackage();
        setId(pkg1, 1L);
        pkg1.setDestination("Goa");

        TourPackage pkg2 = new TourPackage();
        setId(pkg2, 2L);
        pkg2.setDestination("Mysore");

        List<TourPackage> packages = Arrays.asList(pkg1, pkg2);
        when(repo.findAll()).thenReturn(packages);

        List<TourPackage> result = service.getAllPackages();

        assertEquals(2, result.size());
        assertEquals("Goa", result.get(0).getDestination());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetPackageById_Found() {
        TourPackage pkg = new TourPackage();
        setId(pkg, 1L);
        pkg.setDestination("Goa");

        when(repo.findById(1L)).thenReturn(Optional.of(pkg));

        TourPackage result = service.getPackageById(1L);

        assertNotNull(result);
        assertEquals("Goa", result.getDestination());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testGetPackageById_NotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getPackageById(99L));
        verify(repo, times(1)).findById(99L);
    }

    @Test
    void testDeletePackage_Success() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);

        assertDoesNotThrow(() -> service.deletePackage(1L));
        verify(repo, times(1)).existsById(1L);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePackage_NotFound() {
        when(repo.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletePackage(99L));
        verify(repo, times(1)).existsById(99L);
        verify(repo, never()).deleteById(anyLong());
    }

    @Test
    void testUpdatePackage_Found() {
        TourPackage existing = new TourPackage();
        setId(existing, 1L);
        existing.setDestination("Goa");
        existing.setDuration(5);

        TourPackage updated = new TourPackage();
        setId(updated, 1L);
        updated.setDestination("Goa");
        updated.setDuration(7);

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(updated);

        TourPackage result = service.updatePackage(1L, updated);

        assertNotNull(result);
        assertEquals(7, result.getDuration());
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(existing);
    }

    @Test
    void testUpdatePackage_NotFound() {
        TourPackage updated = new TourPackage();
        setId(updated, 99L);

        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updatePackage(99L, updated));
        verify(repo, times(1)).findById(99L);
        verify(repo, never()).save(any());
    }
}