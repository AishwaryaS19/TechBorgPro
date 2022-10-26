package com.emb.ecom.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.emb.ecom.model.Product;
import com.emb.ecom.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);
	
	{
		BasicConfigurator.configure();
	}
	
	public List<Product> getAllProduct(){
		log.info("Fetching list of products");  
		return productRepository.findAll();
	}
	
	public void addProduct(Product product){
		log.info("Saving new product to the database", product.getName());		
		productRepository.save(product);
	}
	
	public void removeProductById(long id){
		log.info("Deleting product from id {}", id);
		productRepository.deleteById(id);
	}
	
	public Optional<Product> getProductById(long id){
		log.info("Fetching products by id");  
		return productRepository.findById(id);
	}
		
	public List<Product> getAllProductsByCategoryId(int id){
		log.info("Fetching all products by category id"); 
		return productRepository.findAllByCategoryId(id);
	}
}
