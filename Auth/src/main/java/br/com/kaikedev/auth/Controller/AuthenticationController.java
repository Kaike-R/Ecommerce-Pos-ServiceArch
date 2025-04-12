package br.com.kaikedev.auth.Controller;


import br.com.kaikedev.auth.ValueObject.AuthenticationRequest;
import br.com.kaikedev.auth.ValueObject.LoginToken;
import br.com.kaikedev.auth.ValueObject.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vi/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(new LoginToken("placeholder"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest authenticationRequest) {


        return ResponseEntity.ok().build();
    }


}
