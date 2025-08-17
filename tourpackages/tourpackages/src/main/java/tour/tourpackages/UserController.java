package tour.tourpackages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
    RequestMethod.DELETE, RequestMethod.OPTIONS
})
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists.");
        }

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

   @PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
    String email = loginRequest.get("email");
    String password = loginRequest.get("password");
    String requestedRole = loginRequest.get("role");

    if (email == null || password == null || requestedRole == null) {
        return ResponseEntity.badRequest().body(Map.of("message", "Email, password and role must be provided."));
    }

    Optional<User> optionalUser = userRepository.findByEmail(email);

    if (optionalUser.isEmpty()) {
        return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password."));
    }

    User existingUser = optionalUser.get();

    if (!passwordEncoder.matches(password, existingUser.getPassword())) {
        return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password."));
    }

    // Check if the requested role matches the stored role
    if (!existingUser.getRole().equalsIgnoreCase(requestedRole)) {
        return ResponseEntity.status(403).body(Map.of("message", "Access denied for the requested role."));
    }

    return ResponseEntity.ok(Map.of(
        "message", "Login successful",
        "email", existingUser.getEmail(),
        "role", existingUser.getRole()
    ));
}

}
