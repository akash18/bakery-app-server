package com.yummy.bakery.controller;

import com.yummy.bakery.entity.Product;
import com.yummy.bakery.service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;

	private static final String imagesDir = "productImages/";

	@CrossOrigin
	@PostMapping("/upload")
	public ResponseEntity<String> uploadProductImage(@RequestParam("file") MultipartFile file) {
		Path filePath = Paths.get(imagesDir + file.getOriginalFilename());
		try {
			if(!Files.exists(filePath.getParent())){
				Files.createDirectory(filePath.getParent());
			}else {
				Files.deleteIfExists(filePath);
			}
			Files.copy(file.getInputStream(), filePath);
		}
		catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).error("ImageUpload", e);
			return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(filePath.toString(), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> products = new ArrayList<>();
		try {
			products.addAll(productService.getProducts());
			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}catch (Exception ex){
			return new ResponseEntity<>(products, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping("/product")
	public ResponseEntity<Product> deleteProduct(@RequestBody Product product){
		try {
			productService.deleteProduct(product);
		}catch (Exception ex){
			return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping("/product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		try {
			productService.updateProduct(product);
		}catch (Exception ex){
			return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping("/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product){
		System.out.println(product.getProductName());
		try {
			product = productService.saveProduct(product);
		}catch (Exception ex){
			return new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/product/images/**")
	public ResponseEntity<byte[]> getProductImage(HttpServletRequest request) {
		String path =
				(String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern =
				(String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		AntPathMatcher apm = new AntPathMatcher();
		String imagePath = apm.extractPathWithinPattern(bestMatchPattern, path);

		Path filePath = Paths.get(this.getClass().getProtectionDomain().
				getCodeSource().getLocation().getPath() + "../../" + imagePath);

		byte[] data = null;
		try {
			data = Files.readAllBytes(filePath);
		}catch (IOException e){
			LoggerFactory.getLogger(this.getClass()).error("ImageDownload", e);
			return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
