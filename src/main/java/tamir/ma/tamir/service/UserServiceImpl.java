package tamir.ma.tamir.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tamir.ma.tamir.dto.AuthDTO;
import tamir.ma.tamir.dto.Token;
import tamir.ma.tamir.entity.Role;
import tamir.ma.tamir.entity.User;
import tamir.ma.tamir.error.ShopException;
import tamir.ma.tamir.error.auth.EmailAlreadyExistsException;
import tamir.ma.tamir.repository.RoleRepository;
import tamir.ma.tamir.repository.UserRepository;
import tamir.ma.tamir.security.JwtUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserDetailsService userDetailsService;
    private final JwtUtilities jwtUtilities;

    private final PasswordEncoder passwordEncoder;


    private final Logger iLogger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User register(AuthDTO signUpDto) throws EmailAlreadyExistsException {
        if(userRepository.existsByEmail(signUpDto.getEmail())) {
            iLogger.info("register {}", signUpDto.getEmail() + " already exists");
            throw new EmailAlreadyExistsException(signUpDto.getEmail());
        }

        iLogger.info("register {}", signUpDto.getPassword() + " Encoding");
        User user = new User();
        user.setEmail(signUpDto.getEmail());
        String encoded = passwordEncoder.encode(signUpDto.getPassword());
        iLogger.info("register {}", encoded + " Encoded");
        user.setPassword(encoded);

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }

    @Override
    public Token signIn(AuthDTO signInDto) throws BadCredentialsException {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetails user = userDetailsService.loadUserByUsername(signInDto.getEmail());
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : user.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        String token = jwtUtilities.generateToken(signInDto.getEmail(),roles);
        return new Token(token);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ShopException("User not found"));
    }
}
