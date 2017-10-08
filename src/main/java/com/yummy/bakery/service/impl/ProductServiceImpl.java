package com.yummy.bakery.service.impl;

import com.yummy.bakery.entity.Image;
import com.yummy.bakery.entity.Product;
import com.yummy.bakery.repository.ImageRepository;
import com.yummy.bakery.repository.ProductRepository;
import com.yummy.bakery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by alalwani on 23/06/17.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    EntityManager em;

    @Override
    public Product saveProduct(Product product) {

        Image image = new Image();
        image.setImagePath(product.getProductImage().getImagePath());
        image.setId(product.getProductImage().getId());
        product.setProductImage(image);
        image.setProduct(product);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll(sortByProductNameAsc());
        return products;

    }

    private Sort sortByProductNameAsc() {
        return new Sort(Sort.Direction.ASC, "productName");
    }

    @Override
    public Page<Product> findAllByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void deleteProduct(Product product) {
         productRepository.delete(product.getId());
    }

    @Override
    public void updateProduct(Product product) {
        Product dbProduct = getProductById(product.getId());
        Product.updateAllFields(product, dbProduct);
        productRepository.saveAndFlush(dbProduct);
    }

    public Product getProductById(long id) {
        return productRepository.getOne(id);
    }

}
