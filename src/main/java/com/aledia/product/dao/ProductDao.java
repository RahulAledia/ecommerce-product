package com.aledia.product.dao;

import com.aledia.product.entity.Products;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Products,Integer> {
    List<Products> findAll();
    Optional<Products> findById(Integer productId);
    void deleteById(Integer productId);
}
