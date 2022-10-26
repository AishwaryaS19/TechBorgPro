package com.emb.ecom.service;

import java.util.List;
import java.util.Optional;
import com.emb.ecom.model.Product;

public interface ProductService {

	List<Product> getAllProduct();
	void addProduct(Product product);
	void removeProductById(long id);
	Optional<Product> getProductById(long id);
	List<Product> getAllProductsByCategoryId(int id);
}
