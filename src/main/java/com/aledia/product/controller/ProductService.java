package com.aledia.product.controller;

import com.aledia.product.dao.ProductDao;
import com.aledia.product.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${services.auth-service-url}")
    public String authServiceUrl;
    private final ProductDao productDao;
    private WebClient webClient;

    private boolean validateToken(String token){
        this.webClient = WebClient.create(authServiceUrl);
        String validateTokenUrl = "/authenticate";
        // Use WebClient to make the request to auth service
        boolean isTokenValid = webClient.post()
                .uri(validateTokenUrl)
                .bodyValue(token)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();  // Blocking operation, consider using reactive programming

        return isTokenValid;
    }
    public List<Products> getAll() {
        return productDao.findAll();
    }

    public String save(List<Products> products){
        productDao.saveAll(products);
        return "saved!";
    }

    public Products getProductById(Integer productId) {
        Optional<Products> optionalProduct = productDao.findById(productId);
        Products product = optionalProduct.orElse(null);
        return product;
    }

    public String delete(Integer productId) {
        productDao.deleteById(productId);
        return "deleted!";
    }
}
