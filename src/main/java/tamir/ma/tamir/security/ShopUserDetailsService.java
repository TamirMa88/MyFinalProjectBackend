package tamir.ma.tamir.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tamir.ma.tamir.entity.User;
import tamir.ma.tamir.repository.UserRepository;



@Component
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

   private final UserRepository userRepository;


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
       return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email" + email + " not found"));
    }
}
