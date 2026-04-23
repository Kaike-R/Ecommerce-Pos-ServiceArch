package br.com.kaikedev.productservice.Controller;


import br.com.kaikedev.productservice.Entity.Dto.ProductDto;
import br.com.kaikedev.productservice.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<?> productResponses = productService.getAllProducts();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {

        ProductDto productDto = productService.getProductById(id);

        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/image/{productId}")
    public ResponseEntity<?> findImageByProductId(@PathVariable Integer productId) {

        List<String> images = productService.findImageByProductId(productId);

        return ResponseEntity.ok(images);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {

        Boolean x = productService.createProduct(productDto);

        return ResponseEntity.ok(x) ;
    }

    @PutMapping
    public ResponseEntity<?> updateProduct() {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateStocks")
    public ResponseEntity<?> updateStocks(@RequestBody ProductDto productDto) {

        return ResponseEntity.ok(productService.uptadeStock(productDto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct() {

        return ResponseEntity.ok().build();
    }
}
