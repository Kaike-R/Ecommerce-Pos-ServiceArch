package br.com.kaikedev.auth.Controller;


import br.com.kaikedev.auth.Entity.UserEntity;
import br.com.kaikedev.auth.Service.AuthorizationService;
import br.com.kaikedev.auth.Service.UserService;
import br.com.kaikedev.auth.ValueObject.AuthenticationRequest;
import br.com.kaikedev.auth.ValueObject.LoginToken;
import br.com.kaikedev.auth.ValueObject.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vi/auth")
public class AuthenticationController {


    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {

        LoginToken token = userService.AuthenticateUserByUsername(authenticationRequest);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest authenticationRequest) {


        return ResponseEntity.ok().build();
    }


}
