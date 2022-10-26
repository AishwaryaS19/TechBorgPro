package com.emb.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.emb.ecom.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
