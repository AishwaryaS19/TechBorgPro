package com.emb.ecom.service;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emb.ecom.model.Category;
import com.emb.ecom.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository;
	
	private static final Logger log = LogManager.getLogger(CategoryServiceImpl.class);
	
	{
		BasicConfigurator.configure();
	}
	
	@Override
	public List<Category> getAllCategory(){
		log.info("Fetching list of categories"); 
		return categoryRepository.findAll();
	}
	
	@Override
	public void addCategory(Category category){
		log.info("Saving new category to the database", category.getName());		
		categoryRepository.save(category);
	}
	
	@Override
	public void removeCategoryById(int id){
		log.info("Deleting category from id {}", id);
		categoryRepository.deleteById(id);
	}
	
	@Override
	public Optional<Category> getCategoryById(int id){
		log.info("Fetching category from id {}", id);
		return categoryRepository.findById(id);
	}
}