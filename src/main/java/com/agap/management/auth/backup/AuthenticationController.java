package com.agap.management.auth.backup;


//@RestController
//@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = {"http://localhost:4200"}, originPatterns = {"*"})
//@RequiredArgsConstructor
public class AuthenticationController {

    /*private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Mi Refresh Token es: " + refreshToken);

        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        try {
            authService.verifyUser(token);
            return ResponseEntity.ok("Account successfully verified.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
}
