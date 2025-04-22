package br.com.kaikedev.productservice.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {


    @GetMapping
    public ResponseEntity<?> getProduct() {

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct() {

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateProduct() {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct() {

        return ResponseEntity.ok().build();
    }


}
