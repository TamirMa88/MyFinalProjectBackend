package tamir.ma.tamir;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tamir.ma.tamir.entity.Role;
import tamir.ma.tamir.entity.User;
import tamir.ma.tamir.repository.RoleRepository;
import tamir.ma.tamir.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import static org.hibernate.id.SequenceMismatchStrategy.LOG;



@Component
@AllArgsConstructor
public class CmdInit implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(CmdInit.class);

    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        try {
            roleRepository.findByName("ROLE_USER")
                    .orElseThrow();
            roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow();
        } catch(Exception e) {
            // add tje role
            roleRepository.save(Role.builder().name("ROLE_USER").build());
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        }

        try {
            userRepository.findByEmail("admin@gmail.com")
                    .orElseThrow();
        } catch(Exception e) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow();
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow();
            // add tje user
            userRepository.save(User.builder()
                     .roles(Set.of(userRole,adminRole))
                     .email("admin@gmail.com")
                     .orders(new HashSet<>())
                    .password(passwordEncoder.encode("123456"))
                    .build());
        }
    }
}
