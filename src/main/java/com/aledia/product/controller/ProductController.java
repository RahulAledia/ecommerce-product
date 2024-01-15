package com.aledia.product.controller;

import com.aledia.product.entity.Products;
import jdk.jfr.Label;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Products>> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<Products> getProductById(@PathVariable Integer productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody List<Products> products){
        return ResponseEntity.ok(productService.save(products));
    }

    @GetMapping("/delete/{productId}")
    public ResponseEntity<String> delete(@PathVariable Integer productId){
        return ResponseEntity.ok(productService.delete(productId));
    }


}
