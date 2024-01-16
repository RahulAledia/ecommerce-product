package com.aledia.product.controller;

import com.aledia.product.dao.ProductDao;
import com.aledia.product.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${services.auth-service-url}")
    public String authServiceUrl;
    private final ProductDao productDao;
    private WebClient webClient;

    public Mono<Boolean> validateToken(HttpHeaders headers) {
        WebClient webClient = WebClient.create(authServiceUrl);
        String validateTokenUrl = "";

        // Use WebClient to make the request to the auth service
        return webClient.method(HttpMethod.GET)
                .uri(validateTokenUrl)  // Adjust the URI as needed in your authorization service
                .headers(httpHeaders -> httpHeaders.addAll(headers))
               // .body(BodyInserters.fromValue(body))
                .retrieve()
                .toEntity(String.class)
                .map(responseEntity -> {
                    // Handle the response from the authorization service
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        return true;
                    } else {
                        return false;
                    }
                });
    }

    public List<Products> getAll(HttpHeaders headers) {
        Mono<Boolean> booleanMono = validateToken(headers);
        boolean result = booleanMono.block();
        if(result)
        return productDao.findAll();
        else return null;
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
