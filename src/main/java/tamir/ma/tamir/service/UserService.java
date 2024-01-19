package tamir.ma.tamir.service;

import org.springframework.security.authentication.BadCredentialsException;
import tamir.ma.tamir.dto.AuthDTO;
import tamir.ma.tamir.dto.Token;
import tamir.ma.tamir.entity.User;
import tamir.ma.tamir.error.auth.EmailAlreadyExistsException;

public interface UserService {
    User register(AuthDTO signUpDto) throws EmailAlreadyExistsException;
    Token signIn(AuthDTO signInDto) throws BadCredentialsException;

    User getUserByEmail(String email);
}
