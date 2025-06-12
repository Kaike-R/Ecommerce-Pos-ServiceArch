package br.com.kaikedev.auth.Service;

import br.com.kaikedev.auth.Entity.UserEntity;
import br.com.kaikedev.auth.Repository.UserRepository;
import br.com.kaikedev.auth.ValueObject.AuthenticationRequest;
import br.com.kaikedev.auth.ValueObject.LoginToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private TokenService tokenService;
    private AuthorizationService authenticationService;
    private UserRepository userRepository;


    private AuthenticationManager authenticationManager;

    public UserService(TokenService tokenService, AuthorizationService authenticationService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public LoginToken AuthenticateUserByUsername(AuthenticationRequest authenticationRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequest.user(), authenticationRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((UserEntity) auth.getPrincipal());
        return new LoginToken(token);
    }

}
