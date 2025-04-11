package br.com.kaikedev.auth.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/teste")
public class HelloController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
