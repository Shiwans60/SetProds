package com.productmanagement.Service;

import com.productmanagement.Model.Product;
import com.productmanagement.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {

        return productRepository.findAll();


    }

    public Product getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {

        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {

        return productRepository.update(id, product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}