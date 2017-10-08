package com.yummy.bakery.service;

import com.yummy.bakery.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by alalwani on 23/06/17.
 */
public interface ProductService {

    //Product getById(long id);

    List<Product> getProducts();

    Page<Product> findAllByPage(Pageable pageable);

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    void updateProduct(Product product);

    Product getProductById(long id);
}
