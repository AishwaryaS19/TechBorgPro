package com.emb.ecom.service;

import java.util.List;
import java.util.Optional;
import com.emb.ecom.model.Category;

public interface CategoryService {
	
	List<Category> getAllCategory();
	void addCategory(Category category);
	void removeCategoryById(int id);
	Optional<Category> getCategoryById(int id);
}
