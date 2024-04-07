package com.agap.management.auth.backup;

//@Service
//@RequiredArgsConstructor
public class AuthService {

    /*private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SendEmailService sendEmailService;

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Error: Role USER is not found."));
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .firstName(request.getFirstname()).lastName(request.getLastname())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .roles(roles).enabled(false)
                .build();

        User savedUser = userRepository.save(user);

        sendVerificationEmail(savedUser);

        return RegisterResponseDTO.builder().message("Verify your account by going to your email").build();
    }




    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Usuario no verificado");
        }



        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return buildAuthenticationResponse(user, jwtToken, refreshToken);
    }

    public AuthenticationResponseDTO refreshToken(String refreshToken) throws IOException {

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token is missing, empty or malformed");
        }
        refreshToken = refreshToken.substring(7);

        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if ( !jwtService.isTokenValid(refreshToken, user) ) {
            throw new RuntimeException("Invalid refresh token");
        }

        var accessToken = jwtService.generateToken(user);   // tal vez deba cambiar
        revokeAllUserTokens(user);                          // de orden estas dos
        saveUserToken(user, accessToken);

        return buildAuthenticationResponse(user, accessToken, refreshToken);
    }

    public void verifyUser(String token) {
        try {
            String username = jwtService.extractUsername(token);

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

            Optional<Token> verificationTokenOptional = tokenRepository.findByToken(token);
            if (!verificationTokenOptional.isPresent() || verificationTokenOptional.get().isExpired() || verificationTokenOptional.get().isRevoked()) {
                throw new RuntimeException("Invalid or expired verification link.");
            }

            Token verificationToken = verificationTokenOptional.get();

            if (user.isEnabled()) {
                throw new IllegalStateException("Account is already verified.");
            }

            user.setEnabled(true);
            userRepository.save(user);
            invalidateToken(verificationToken);

        } catch (ExpiredJwtException e) {
            User user = userRepository.findByEmail(e.getClaims().getSubject())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            sendVerificationEmail(user);
            throw new RuntimeException("Verification link has expired. A new verification email has been sent.");
        } catch (JwtException e) {
            throw new RuntimeException("Verification failed: " + e.getMessage());
        }
    }

    private void invalidateToken(Token verificationToken) {
        verificationToken.setRevoked(true);
        tokenRepository.save(verificationToken);
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }



    private AuthenticationResponseDTO buildAuthenticationResponse(User user, String accessToken, String refreshToken) {
        AuthenticationResponseDTO.UserResponseDTO userResponseDTO = new AuthenticationResponseDTO.UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getFirstName() + " " + user.getLastName());
        userResponseDTO.setActive(user.isEnabled());
        userResponseDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return AuthenticationResponseDTO.builder()
                .user(userResponseDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void sendVerificationEmail(User user) {
        String jwtToken = jwtService.generateToken(user);
        //String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        String verificationLink = "http://localhost:8080/api/v1/auth/verify/" + jwtToken;
        sendEmailService.sendEmail(user.getEmail(), "Verifica tu cuenta", "Haz click en el enlace para verificar tu cuenta: " + verificationLink);
    }*/
}
